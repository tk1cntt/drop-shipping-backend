package com.dropshipping.service;

import com.dropshipping.domain.OrderComment;
import com.dropshipping.repository.OrderCommentRepository;
import com.dropshipping.service.dto.OrderCommentDTO;
import com.dropshipping.service.mapper.OrderCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderComment}.
 */
@Service
@Transactional
public class OrderCommentService {

    private final Logger log = LoggerFactory.getLogger(OrderCommentService.class);

    private final OrderCommentRepository orderCommentRepository;

    private final OrderCommentMapper orderCommentMapper;

    public OrderCommentService(OrderCommentRepository orderCommentRepository, OrderCommentMapper orderCommentMapper) {
        this.orderCommentRepository = orderCommentRepository;
        this.orderCommentMapper = orderCommentMapper;
    }

    /**
     * Save a orderComment.
     *
     * @param orderCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderCommentDTO save(OrderCommentDTO orderCommentDTO) {
        log.debug("Request to save OrderComment : {}", orderCommentDTO);
        OrderComment orderComment = orderCommentMapper.toEntity(orderCommentDTO);
        orderComment = orderCommentRepository.save(orderComment);
        return orderCommentMapper.toDto(orderComment);
    }

    /**
     * Get all the orderComments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderCommentDTO> findAll() {
        log.debug("Request to get all OrderComments");
        return orderCommentRepository.findAll().stream()
            .map(orderCommentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderCommentDTO> findOne(Long id) {
        log.debug("Request to get OrderComment : {}", id);
        return orderCommentRepository.findById(id)
            .map(orderCommentMapper::toDto);
    }

    /**
     * Delete the orderComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderComment : {}", id);
        orderCommentRepository.deleteById(id);
    }
}
