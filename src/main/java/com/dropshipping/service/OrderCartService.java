package com.dropshipping.service;

import com.dropshipping.domain.OrderCart;
import com.dropshipping.repository.OrderCartRepository;
import com.dropshipping.service.dto.OrderCartDTO;
import com.dropshipping.service.mapper.OrderCartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderCart}.
 */
@Service
@Transactional
public class OrderCartService {

    private final Logger log = LoggerFactory.getLogger(OrderCartService.class);

    private final OrderCartRepository orderCartRepository;

    private final OrderCartMapper orderCartMapper;

    public OrderCartService(OrderCartRepository orderCartRepository, OrderCartMapper orderCartMapper) {
        this.orderCartRepository = orderCartRepository;
        this.orderCartMapper = orderCartMapper;
    }

    /**
     * Save a orderCart.
     *
     * @param orderCartDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderCartDTO save(OrderCartDTO orderCartDTO) {
        log.debug("Request to save OrderCart : {}", orderCartDTO);
        OrderCart orderCart = orderCartMapper.toEntity(orderCartDTO);
        orderCart = orderCartRepository.save(orderCart);
        return orderCartMapper.toDto(orderCart);
    }

    /**
     * Get all the orderCarts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderCartDTO> findAll() {
        log.debug("Request to get all OrderCarts");
        return orderCartRepository.findAll().stream()
            .map(orderCartMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderCart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderCartDTO> findOne(Long id) {
        log.debug("Request to get OrderCart : {}", id);
        return orderCartRepository.findById(id)
            .map(orderCartMapper::toDto);
    }

    /**
     * Delete the orderCart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderCart : {}", id);
        orderCartRepository.deleteById(id);
    }
}
