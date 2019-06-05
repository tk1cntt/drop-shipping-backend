package com.dropshipping.service;

import com.dropshipping.domain.OrderTransaction;
import com.dropshipping.repository.OrderTransactionRepository;
import com.dropshipping.service.dto.OrderTransactionDTO;
import com.dropshipping.service.mapper.OrderTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderTransaction}.
 */
@Service
@Transactional
public class OrderTransactionService {

    private final Logger log = LoggerFactory.getLogger(OrderTransactionService.class);

    private final OrderTransactionRepository orderTransactionRepository;

    private final OrderTransactionMapper orderTransactionMapper;

    public OrderTransactionService(OrderTransactionRepository orderTransactionRepository, OrderTransactionMapper orderTransactionMapper) {
        this.orderTransactionRepository = orderTransactionRepository;
        this.orderTransactionMapper = orderTransactionMapper;
    }

    /**
     * Save a orderTransaction.
     *
     * @param orderTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderTransactionDTO save(OrderTransactionDTO orderTransactionDTO) {
        log.debug("Request to save OrderTransaction : {}", orderTransactionDTO);
        OrderTransaction orderTransaction = orderTransactionMapper.toEntity(orderTransactionDTO);
        orderTransaction = orderTransactionRepository.save(orderTransaction);
        return orderTransactionMapper.toDto(orderTransaction);
    }

    /**
     * Get all the orderTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderTransactionDTO> findAll() {
        log.debug("Request to get all OrderTransactions");
        return orderTransactionRepository.findAll().stream()
            .map(orderTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderTransactionDTO> findOne(Long id) {
        log.debug("Request to get OrderTransaction : {}", id);
        return orderTransactionRepository.findById(id)
            .map(orderTransactionMapper::toDto);
    }

    /**
     * Delete the orderTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderTransaction : {}", id);
        orderTransactionRepository.deleteById(id);
    }
}
