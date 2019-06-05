package com.dropshipping.service;

import com.dropshipping.domain.OrderPackageHistory;
import com.dropshipping.repository.OrderPackageHistoryRepository;
import com.dropshipping.service.dto.OrderPackageHistoryDTO;
import com.dropshipping.service.mapper.OrderPackageHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderPackageHistory}.
 */
@Service
@Transactional
public class OrderPackageHistoryService {

    private final Logger log = LoggerFactory.getLogger(OrderPackageHistoryService.class);

    private final OrderPackageHistoryRepository orderPackageHistoryRepository;

    private final OrderPackageHistoryMapper orderPackageHistoryMapper;

    public OrderPackageHistoryService(OrderPackageHistoryRepository orderPackageHistoryRepository, OrderPackageHistoryMapper orderPackageHistoryMapper) {
        this.orderPackageHistoryRepository = orderPackageHistoryRepository;
        this.orderPackageHistoryMapper = orderPackageHistoryMapper;
    }

    /**
     * Save a orderPackageHistory.
     *
     * @param orderPackageHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderPackageHistoryDTO save(OrderPackageHistoryDTO orderPackageHistoryDTO) {
        log.debug("Request to save OrderPackageHistory : {}", orderPackageHistoryDTO);
        OrderPackageHistory orderPackageHistory = orderPackageHistoryMapper.toEntity(orderPackageHistoryDTO);
        orderPackageHistory = orderPackageHistoryRepository.save(orderPackageHistory);
        return orderPackageHistoryMapper.toDto(orderPackageHistory);
    }

    /**
     * Get all the orderPackageHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderPackageHistoryDTO> findAll() {
        log.debug("Request to get all OrderPackageHistories");
        return orderPackageHistoryRepository.findAll().stream()
            .map(orderPackageHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderPackageHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderPackageHistoryDTO> findOne(Long id) {
        log.debug("Request to get OrderPackageHistory : {}", id);
        return orderPackageHistoryRepository.findById(id)
            .map(orderPackageHistoryMapper::toDto);
    }

    /**
     * Delete the orderPackageHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderPackageHistory : {}", id);
        orderPackageHistoryRepository.deleteById(id);
    }
}
