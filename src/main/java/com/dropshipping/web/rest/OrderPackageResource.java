package com.dropshipping.web.rest;

import com.dropshipping.service.OrderPackageService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.OrderPackageDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.OrderPackage}.
 */
@RestController
@RequestMapping("/api")
public class OrderPackageResource {

    private final Logger log = LoggerFactory.getLogger(OrderPackageResource.class);

    private static final String ENTITY_NAME = "orderPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPackageService orderPackageService;

    public OrderPackageResource(OrderPackageService orderPackageService) {
        this.orderPackageService = orderPackageService;
    }

    /**
     * {@code POST  /order-packages} : Create a new orderPackage.
     *
     * @param orderPackageDTO the orderPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPackageDTO, or with status {@code 400 (Bad Request)} if the orderPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-packages")
    public ResponseEntity<OrderPackageDTO> createOrderPackage(@RequestBody OrderPackageDTO orderPackageDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPackage : {}", orderPackageDTO);
        if (orderPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPackageDTO result = orderPackageService.save(orderPackageDTO);
        return ResponseEntity.created(new URI("/api/order-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-packages} : Updates an existing orderPackage.
     *
     * @param orderPackageDTO the orderPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPackageDTO,
     * or with status {@code 400 (Bad Request)} if the orderPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-packages")
    public ResponseEntity<OrderPackageDTO> updateOrderPackage(@RequestBody OrderPackageDTO orderPackageDTO) throws URISyntaxException {
        log.debug("REST request to update OrderPackage : {}", orderPackageDTO);
        if (orderPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPackageDTO result = orderPackageService.save(orderPackageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-packages} : get all the orderPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPackages in body.
     */
    @GetMapping("/order-packages")
    public List<OrderPackageDTO> getAllOrderPackages() {
        log.debug("REST request to get all OrderPackages");
        return orderPackageService.findAll();
    }

    /**
     * {@code GET  /order-packages/:id} : get the "id" orderPackage.
     *
     * @param id the id of the orderPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-packages/{id}")
    public ResponseEntity<OrderPackageDTO> getOrderPackage(@PathVariable Long id) {
        log.debug("REST request to get OrderPackage : {}", id);
        Optional<OrderPackageDTO> orderPackageDTO = orderPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPackageDTO);
    }

    /**
     * {@code DELETE  /order-packages/:id} : delete the "id" orderPackage.
     *
     * @param id the id of the orderPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-packages/{id}")
    public ResponseEntity<Void> deleteOrderPackage(@PathVariable Long id) {
        log.debug("REST request to delete OrderPackage : {}", id);
        orderPackageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
