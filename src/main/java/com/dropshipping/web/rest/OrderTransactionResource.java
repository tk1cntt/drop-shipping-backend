package com.dropshipping.web.rest;

import com.dropshipping.service.OrderTransactionService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderTransactionDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.OrderTransaction}.
 */
@RestController
@RequestMapping("/api")
public class OrderTransactionResource {

    private final Logger log = LoggerFactory.getLogger(OrderTransactionResource.class);

    private static final String ENTITY_NAME = "orderTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTransactionService orderTransactionService;

    public OrderTransactionResource(OrderTransactionService orderTransactionService) {
        this.orderTransactionService = orderTransactionService;
    }

    /**
     * {@code POST  /order-transactions} : Create a new orderTransaction.
     *
     * @param orderTransactionDTO the orderTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTransactionDTO, or with status {@code 400 (Bad Request)} if the orderTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-transactions")
    public ResponseEntity<OrderTransactionDTO> createOrderTransaction(@RequestBody OrderTransactionDTO orderTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save OrderTransaction : {}", orderTransactionDTO);
        if (orderTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTransactionDTO result = orderTransactionService.save(orderTransactionDTO);
        return ResponseEntity.created(new URI("/api/order-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-transactions} : Updates an existing orderTransaction.
     *
     * @param orderTransactionDTO the orderTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the orderTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-transactions")
    public ResponseEntity<OrderTransactionDTO> updateOrderTransaction(@RequestBody OrderTransactionDTO orderTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update OrderTransaction : {}", orderTransactionDTO);
        if (orderTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTransactionDTO result = orderTransactionService.save(orderTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-transactions} : get all the orderTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTransactions in body.
     */
    @GetMapping("/order-transactions")
    public List<OrderTransactionDTO> getAllOrderTransactions() {
        log.debug("REST request to get all OrderTransactions");
        return orderTransactionService.findAll();
    }

    /**
     * {@code GET  /order-transactions/:id} : get the "id" orderTransaction.
     *
     * @param id the id of the orderTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-transactions/{id}")
    public ResponseEntity<OrderTransactionDTO> getOrderTransaction(@PathVariable Long id) {
        log.debug("REST request to get OrderTransaction : {}", id);
        Optional<OrderTransactionDTO> orderTransactionDTO = orderTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTransactionDTO);
    }

    /**
     * {@code DELETE  /order-transactions/:id} : delete the "id" orderTransaction.
     *
     * @param id the id of the orderTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-transactions/{id}")
    public ResponseEntity<Void> deleteOrderTransaction(@PathVariable Long id) {
        log.debug("REST request to delete OrderTransaction : {}", id);
        orderTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
