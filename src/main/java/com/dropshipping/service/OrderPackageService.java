package com.dropshipping.service;

import com.dropshipping.domain.OrderPackage;
import com.dropshipping.repository.OrderPackageRepository;
import com.dropshipping.service.dto.OrderPackageDTO;
import com.dropshipping.service.mapper.OrderPackageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderPackage}.
 */
@Service
@Transactional
public class OrderPackageService {

    private final Logger log = LoggerFactory.getLogger(OrderPackageService.class);

    private final OrderPackageRepository orderPackageRepository;

    private final OrderPackageMapper orderPackageMapper;

    public OrderPackageService(OrderPackageRepository orderPackageRepository, OrderPackageMapper orderPackageMapper) {
        this.orderPackageRepository = orderPackageRepository;
        this.orderPackageMapper = orderPackageMapper;
    }

    /**
     * Save a orderPackage.
     *
     * @param orderPackageDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderPackageDTO save(OrderPackageDTO orderPackageDTO) {
        log.debug("Request to save OrderPackage : {}", orderPackageDTO);
        OrderPackage orderPackage = orderPackageMapper.toEntity(orderPackageDTO);
        orderPackage = orderPackageRepository.save(orderPackage);
        return orderPackageMapper.toDto(orderPackage);
    }

    /**
     * Get all the orderPackages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderPackageDTO> findAll() {
        log.debug("Request to get all OrderPackages");
        return orderPackageRepository.findAll().stream()
            .map(orderPackageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderPackage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderPackageDTO> findOne(Long id) {
        log.debug("Request to get OrderPackage : {}", id);
        return orderPackageRepository.findById(id)
            .map(orderPackageMapper::toDto);
    }

    /**
     * Delete the orderPackage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderPackage : {}", id);
        orderPackageRepository.deleteById(id);
    }
}
