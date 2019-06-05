package com.dropshipping.web.rest;

import com.dropshipping.service.OrderPackageHistoryService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderPackageHistoryDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.OrderPackageHistory}.
 */
@RestController
@RequestMapping("/api")
public class OrderPackageHistoryResource {

    private final Logger log = LoggerFactory.getLogger(OrderPackageHistoryResource.class);

    private static final String ENTITY_NAME = "orderPackageHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPackageHistoryService orderPackageHistoryService;

    public OrderPackageHistoryResource(OrderPackageHistoryService orderPackageHistoryService) {
        this.orderPackageHistoryService = orderPackageHistoryService;
    }

    /**
     * {@code POST  /order-package-histories} : Create a new orderPackageHistory.
     *
     * @param orderPackageHistoryDTO the orderPackageHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPackageHistoryDTO, or with status {@code 400 (Bad Request)} if the orderPackageHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-package-histories")
    public ResponseEntity<OrderPackageHistoryDTO> createOrderPackageHistory(@RequestBody OrderPackageHistoryDTO orderPackageHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPackageHistory : {}", orderPackageHistoryDTO);
        if (orderPackageHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPackageHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPackageHistoryDTO result = orderPackageHistoryService.save(orderPackageHistoryDTO);
        return ResponseEntity.created(new URI("/api/order-package-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-package-histories} : Updates an existing orderPackageHistory.
     *
     * @param orderPackageHistoryDTO the orderPackageHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPackageHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the orderPackageHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPackageHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-package-histories")
    public ResponseEntity<OrderPackageHistoryDTO> updateOrderPackageHistory(@RequestBody OrderPackageHistoryDTO orderPackageHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update OrderPackageHistory : {}", orderPackageHistoryDTO);
        if (orderPackageHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPackageHistoryDTO result = orderPackageHistoryService.save(orderPackageHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPackageHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-package-histories} : get all the orderPackageHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPackageHistories in body.
     */
    @GetMapping("/order-package-histories")
    public List<OrderPackageHistoryDTO> getAllOrderPackageHistories() {
        log.debug("REST request to get all OrderPackageHistories");
        return orderPackageHistoryService.findAll();
    }

    /**
     * {@code GET  /order-package-histories/:id} : get the "id" orderPackageHistory.
     *
     * @param id the id of the orderPackageHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPackageHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-package-histories/{id}")
    public ResponseEntity<OrderPackageHistoryDTO> getOrderPackageHistory(@PathVariable Long id) {
        log.debug("REST request to get OrderPackageHistory : {}", id);
        Optional<OrderPackageHistoryDTO> orderPackageHistoryDTO = orderPackageHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPackageHistoryDTO);
    }

    /**
     * {@code DELETE  /order-package-histories/:id} : delete the "id" orderPackageHistory.
     *
     * @param id the id of the orderPackageHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-package-histories/{id}")
    public ResponseEntity<Void> deleteOrderPackageHistory(@PathVariable Long id) {
        log.debug("REST request to delete OrderPackageHistory : {}", id);
        orderPackageHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
