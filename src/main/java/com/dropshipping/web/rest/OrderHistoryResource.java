package com.dropshipping.web.rest;

import com.dropshipping.service.OrderHistoryService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderHistoryDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.OrderHistory}.
 */
@RestController
@RequestMapping("/api")
public class OrderHistoryResource {

    private final Logger log = LoggerFactory.getLogger(OrderHistoryResource.class);

    private static final String ENTITY_NAME = "orderHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderHistoryService orderHistoryService;

    public OrderHistoryResource(OrderHistoryService orderHistoryService) {
        this.orderHistoryService = orderHistoryService;
    }

    /**
     * {@code POST  /order-histories} : Create a new orderHistory.
     *
     * @param orderHistoryDTO the orderHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderHistoryDTO, or with status {@code 400 (Bad Request)} if the orderHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-histories")
    public ResponseEntity<OrderHistoryDTO> createOrderHistory(@RequestBody OrderHistoryDTO orderHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save OrderHistory : {}", orderHistoryDTO);
        if (orderHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderHistoryDTO result = orderHistoryService.save(orderHistoryDTO);
        return ResponseEntity.created(new URI("/api/order-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-histories} : Updates an existing orderHistory.
     *
     * @param orderHistoryDTO the orderHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the orderHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-histories")
    public ResponseEntity<OrderHistoryDTO> updateOrderHistory(@RequestBody OrderHistoryDTO orderHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update OrderHistory : {}", orderHistoryDTO);
        if (orderHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderHistoryDTO result = orderHistoryService.save(orderHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-histories} : get all the orderHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderHistories in body.
     */
    @GetMapping("/order-histories")
    public List<OrderHistoryDTO> getAllOrderHistories() {
        log.debug("REST request to get all OrderHistories");
        return orderHistoryService.findAll();
    }

    /**
     * {@code GET  /order-histories/:id} : get the "id" orderHistory.
     *
     * @param id the id of the orderHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-histories/{id}")
    public ResponseEntity<OrderHistoryDTO> getOrderHistory(@PathVariable Long id) {
        log.debug("REST request to get OrderHistory : {}", id);
        Optional<OrderHistoryDTO> orderHistoryDTO = orderHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderHistoryDTO);
    }

    /**
     * {@code DELETE  /order-histories/:id} : delete the "id" orderHistory.
     *
     * @param id the id of the orderHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-histories/{id}")
    public ResponseEntity<Void> deleteOrderHistory(@PathVariable Long id) {
        log.debug("REST request to delete OrderHistory : {}", id);
        orderHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
