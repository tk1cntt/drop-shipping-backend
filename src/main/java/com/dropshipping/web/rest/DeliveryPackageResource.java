package com.dropshipping.web.rest;

import com.dropshipping.service.DeliveryPackageService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.DeliveryPackageDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.DeliveryPackage}.
 */
@RestController
@RequestMapping("/api")
public class DeliveryPackageResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryPackageResource.class);

    private static final String ENTITY_NAME = "deliveryPackage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryPackageService deliveryPackageService;

    public DeliveryPackageResource(DeliveryPackageService deliveryPackageService) {
        this.deliveryPackageService = deliveryPackageService;
    }

    /**
     * {@code POST  /delivery-packages} : Create a new deliveryPackage.
     *
     * @param deliveryPackageDTO the deliveryPackageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deliveryPackageDTO, or with status {@code 400 (Bad Request)} if the deliveryPackage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delivery-packages")
    public ResponseEntity<DeliveryPackageDTO> createDeliveryPackage(@RequestBody DeliveryPackageDTO deliveryPackageDTO) throws URISyntaxException {
        log.debug("REST request to save DeliveryPackage : {}", deliveryPackageDTO);
        if (deliveryPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new deliveryPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryPackageDTO result = deliveryPackageService.save(deliveryPackageDTO);
        return ResponseEntity.created(new URI("/api/delivery-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delivery-packages} : Updates an existing deliveryPackage.
     *
     * @param deliveryPackageDTO the deliveryPackageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deliveryPackageDTO,
     * or with status {@code 400 (Bad Request)} if the deliveryPackageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deliveryPackageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delivery-packages")
    public ResponseEntity<DeliveryPackageDTO> updateDeliveryPackage(@RequestBody DeliveryPackageDTO deliveryPackageDTO) throws URISyntaxException {
        log.debug("REST request to update DeliveryPackage : {}", deliveryPackageDTO);
        if (deliveryPackageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeliveryPackageDTO result = deliveryPackageService.save(deliveryPackageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deliveryPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delivery-packages} : get all the deliveryPackages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveryPackages in body.
     */
    @GetMapping("/delivery-packages")
    public List<DeliveryPackageDTO> getAllDeliveryPackages() {
        log.debug("REST request to get all DeliveryPackages");
        return deliveryPackageService.findAll();
    }

    /**
     * {@code GET  /delivery-packages/:id} : get the "id" deliveryPackage.
     *
     * @param id the id of the deliveryPackageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deliveryPackageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delivery-packages/{id}")
    public ResponseEntity<DeliveryPackageDTO> getDeliveryPackage(@PathVariable Long id) {
        log.debug("REST request to get DeliveryPackage : {}", id);
        Optional<DeliveryPackageDTO> deliveryPackageDTO = deliveryPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deliveryPackageDTO);
    }

    /**
     * {@code DELETE  /delivery-packages/:id} : delete the "id" deliveryPackage.
     *
     * @param id the id of the deliveryPackageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delivery-packages/{id}")
    public ResponseEntity<Void> deleteDeliveryPackage(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryPackage : {}", id);
        deliveryPackageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
