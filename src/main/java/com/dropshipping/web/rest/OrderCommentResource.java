package com.dropshipping.web.rest;

import com.dropshipping.service.OrderCommentService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderCommentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dropshipping.domain.OrderComment}.
 */
@RestController
@RequestMapping("/api")
public class OrderCommentResource {

    private final Logger log = LoggerFactory.getLogger(OrderCommentResource.class);

    private static final String ENTITY_NAME = "orderComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderCommentService orderCommentService;

    public OrderCommentResource(OrderCommentService orderCommentService) {
        this.orderCommentService = orderCommentService;
    }

    /**
     * {@code POST  /order-comments} : Create a new orderComment.
     *
     * @param orderCommentDTO the orderCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderCommentDTO, or with status {@code 400 (Bad Request)} if the orderComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-comments")
    public ResponseEntity<OrderCommentDTO> createOrderComment(@RequestBody OrderCommentDTO orderCommentDTO) throws URISyntaxException {
        log.debug("REST request to save OrderComment : {}", orderCommentDTO);
        if (orderCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderCommentDTO result = orderCommentService.save(orderCommentDTO);
        return ResponseEntity.created(new URI("/api/order-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-comments} : Updates an existing orderComment.
     *
     * @param orderCommentDTO the orderCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderCommentDTO,
     * or with status {@code 400 (Bad Request)} if the orderCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-comments")
    public ResponseEntity<OrderCommentDTO> updateOrderComment(@RequestBody OrderCommentDTO orderCommentDTO) throws URISyntaxException {
        log.debug("REST request to update OrderComment : {}", orderCommentDTO);
        if (orderCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderCommentDTO result = orderCommentService.save(orderCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-comments} : get all the orderComments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderComments in body.
     */
    @GetMapping("/order-comments")
    public List<OrderCommentDTO> getAllOrderComments() {
        log.debug("REST request to get all OrderComments");
        return orderCommentService.findAll();
    }

    /**
     * {@code GET  /order-comments/:id} : get the "id" orderComment.
     *
     * @param id the id of the orderCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-comments/{id}")
    public ResponseEntity<OrderCommentDTO> getOrderComment(@PathVariable Long id) {
        log.debug("REST request to get OrderComment : {}", id);
        Optional<OrderCommentDTO> orderCommentDTO = orderCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderCommentDTO);
    }

    /**
     * {@code DELETE  /order-comments/:id} : delete the "id" orderComment.
     *
     * @param id the id of the orderCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-comments/{id}")
    public ResponseEntity<Void> deleteOrderComment(@PathVariable Long id) {
        log.debug("REST request to delete OrderComment : {}", id);
        orderCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
