package com.dropshipping.service;

import com.dropshipping.domain.OrderHistory;
import com.dropshipping.repository.OrderHistoryRepository;
import com.dropshipping.service.dto.OrderHistoryDTO;
import com.dropshipping.service.mapper.OrderHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderHistory}.
 */
@Service
@Transactional
public class OrderHistoryService {

    private final Logger log = LoggerFactory.getLogger(OrderHistoryService.class);

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderHistoryMapper orderHistoryMapper;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository, OrderHistoryMapper orderHistoryMapper) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    /**
     * Save a orderHistory.
     *
     * @param orderHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderHistoryDTO save(OrderHistoryDTO orderHistoryDTO) {
        log.debug("Request to save OrderHistory : {}", orderHistoryDTO);
        OrderHistory orderHistory = orderHistoryMapper.toEntity(orderHistoryDTO);
        orderHistory = orderHistoryRepository.save(orderHistory);
        return orderHistoryMapper.toDto(orderHistory);
    }

    /**
     * Get all the orderHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderHistoryDTO> findAll() {
        log.debug("Request to get all OrderHistories");
        return orderHistoryRepository.findAll().stream()
            .map(orderHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderHistoryDTO> findOne(Long id) {
        log.debug("Request to get OrderHistory : {}", id);
        return orderHistoryRepository.findById(id)
            .map(orderHistoryMapper::toDto);
    }

    /**
     * Delete the orderHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderHistory : {}", id);
        orderHistoryRepository.deleteById(id);
    }
}
