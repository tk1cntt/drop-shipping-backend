package com.dropshipping.web.rest;

import com.dropshipping.service.OrderCartService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderCartDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.OrderCart}.
 */
@RestController
@RequestMapping("/api")
public class OrderCartResource {

    private final Logger log = LoggerFactory.getLogger(OrderCartResource.class);

    private static final String ENTITY_NAME = "orderCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderCartService orderCartService;

    public OrderCartResource(OrderCartService orderCartService) {
        this.orderCartService = orderCartService;
    }

    /**
     * {@code POST  /order-carts} : Create a new orderCart.
     *
     * @param orderCartDTO the orderCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderCartDTO, or with status {@code 400 (Bad Request)} if the orderCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-carts")
    public ResponseEntity<OrderCartDTO> createOrderCart(@RequestBody OrderCartDTO orderCartDTO) throws URISyntaxException {
        log.debug("REST request to save OrderCart : {}", orderCartDTO);
        if (orderCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderCartDTO result = orderCartService.save(orderCartDTO);
        return ResponseEntity.created(new URI("/api/order-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-carts} : Updates an existing orderCart.
     *
     * @param orderCartDTO the orderCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderCartDTO,
     * or with status {@code 400 (Bad Request)} if the orderCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-carts")
    public ResponseEntity<OrderCartDTO> updateOrderCart(@RequestBody OrderCartDTO orderCartDTO) throws URISyntaxException {
        log.debug("REST request to update OrderCart : {}", orderCartDTO);
        if (orderCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderCartDTO result = orderCartService.save(orderCartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-carts} : get all the orderCarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderCarts in body.
     */
    @GetMapping("/order-carts")
    public List<OrderCartDTO> getAllOrderCarts() {
        log.debug("REST request to get all OrderCarts");
        return orderCartService.findAll();
    }

    /**
     * {@code GET  /order-carts/:id} : get the "id" orderCart.
     *
     * @param id the id of the orderCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-carts/{id}")
    public ResponseEntity<OrderCartDTO> getOrderCart(@PathVariable Long id) {
        log.debug("REST request to get OrderCart : {}", id);
        Optional<OrderCartDTO> orderCartDTO = orderCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderCartDTO);
    }

    /**
     * {@code DELETE  /order-carts/:id} : delete the "id" orderCart.
     *
     * @param id the id of the orderCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-carts/{id}")
    public ResponseEntity<Void> deleteOrderCart(@PathVariable Long id) {
        log.debug("REST request to delete OrderCart : {}", id);
        orderCartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
