package com.dropshipping.service;

import com.dropshipping.domain.ShoppingCart;
import com.dropshipping.domain.ShoppingCartItem;
import com.dropshipping.repository.ShoppingCartItemRepository;
import com.dropshipping.repository.ShoppingCartRepository;
import com.dropshipping.service.dto.ShoppingCartDTO;
import com.dropshipping.service.dto.ShoppingCartItemDTO;
import com.dropshipping.service.mapper.ShoppingCartItemMapper;
import com.dropshipping.service.mapper.ShoppingCartMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShoppingCart}.
 */
@Service
@Transactional
public class ShoppingCartService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    private final ShoppingCartItemMapper shoppingCartItemMapper;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper, ShoppingCartItemRepository shoppingCartItemRepository, ShoppingCartItemMapper shoppingCartItemMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
    }

    /**
     * Save a shoppingCart.
     *
     * @param shoppingCartDTO the entity to save.
     * @return the persisted entity.
     */
    public ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO) {
        log.debug("Request to save ShoppingCart : {}", shoppingCartDTO);
        ShoppingCart currentShop = shoppingCartRepository.findFirstByShopId(shoppingCartDTO.getShopId());
        if (ObjectUtils.isEmpty(currentShop)) {
            ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDTO);
            shoppingCart = shoppingCartRepository.save(shoppingCart);
            List<ShoppingCartItem> items = shoppingCartItemMapper.toEntity(shoppingCartDTO.getItems());
            for (ShoppingCartItem shoppingCartItem: items) {
                shoppingCartItem.setShoppingCart(shoppingCart);
                shoppingCartItemRepository.save(shoppingCartItem);
            }
            currentShop = shoppingCart;
        } else {
            List<ShoppingCartItem> items = shoppingCartItemMapper.toEntity(shoppingCartDTO.getItems());
            Set<ShoppingCartItem> currentItems = currentShop.getItems();
            for (ShoppingCartItem shoppingCartItem: items) {
                for (ShoppingCartItem currentItem: currentItems) {
                    if (currentItem.getItemId().equals(shoppingCartItem.getItemId()) 
                        && currentItem.getPropertiesId().equals(shoppingCartItem.getPropertiesId())
                        && currentItem.getPropertiesName().equals(shoppingCartItem.getPropertiesName())
                        && currentItem.getPropertiesType().equals(shoppingCartItem.getPropertiesType())) {
                        currentItem.setQuantity(currentItem.getQuantity() + shoppingCartItem.getQuantity());
                        currentItem.setTotalAmountNDT(currentItem.getTotalAmountNDT() + shoppingCartItem.getTotalAmountNDT());
                    } else {
                        shoppingCartItem.setShoppingCart(currentShop);
                        shoppingCartItemRepository.save(shoppingCartItem);
                    }                    
                }
            }
            currentShop = shoppingCartRepository.save(currentShop);
        }
        return shoppingCartMapper.toDto(currentShop);
    }

    /**
     * Get all the shoppingCarts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ShoppingCartDTO> findAll() {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartRepository.findAll().stream()
            .map(shoppingCartMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shoppingCart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShoppingCartDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCart : {}", id);
        return shoppingCartRepository.findById(id)
            .map(shoppingCartMapper::toDto);
    }

    /**
     * Delete the shoppingCart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCart : {}", id);
        shoppingCartRepository.deleteById(id);
    }
}
