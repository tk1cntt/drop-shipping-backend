package com.dropshipping.web.rest;

import com.dropshipping.service.UserShippingAddressService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.UserShippingAddressDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.UserShippingAddress}.
 */
@RestController
@RequestMapping("/api")
public class UserShippingAddressResource {

    private final Logger log = LoggerFactory.getLogger(UserShippingAddressResource.class);

    private static final String ENTITY_NAME = "userShippingAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserShippingAddressService userShippingAddressService;

    public UserShippingAddressResource(UserShippingAddressService userShippingAddressService) {
        this.userShippingAddressService = userShippingAddressService;
    }

    /**
     * {@code POST  /user-shipping-addresses} : Create a new userShippingAddress.
     *
     * @param userShippingAddressDTO the userShippingAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userShippingAddressDTO, or with status {@code 400 (Bad Request)} if the userShippingAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-shipping-addresses")
    public ResponseEntity<UserShippingAddressDTO> createUserShippingAddress(@RequestBody UserShippingAddressDTO userShippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to save UserShippingAddress : {}", userShippingAddressDTO);
        if (userShippingAddressDTO.getId() != null) {
            throw new BadRequestAlertException("A new userShippingAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserShippingAddressDTO result = userShippingAddressService.save(userShippingAddressDTO);
        return ResponseEntity.created(new URI("/api/user-shipping-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-shipping-addresses} : Updates an existing userShippingAddress.
     *
     * @param userShippingAddressDTO the userShippingAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userShippingAddressDTO,
     * or with status {@code 400 (Bad Request)} if the userShippingAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userShippingAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-shipping-addresses")
    public ResponseEntity<UserShippingAddressDTO> updateUserShippingAddress(@RequestBody UserShippingAddressDTO userShippingAddressDTO) throws URISyntaxException {
        log.debug("REST request to update UserShippingAddress : {}", userShippingAddressDTO);
        if (userShippingAddressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserShippingAddressDTO result = userShippingAddressService.save(userShippingAddressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userShippingAddressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-shipping-addresses} : get all the userShippingAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userShippingAddresses in body.
     */
    @GetMapping("/user-shipping-addresses")
    public List<UserShippingAddressDTO> getAllUserShippingAddresses() {
        log.debug("REST request to get all UserShippingAddresses");
        return userShippingAddressService.findAll();
    }

    /**
     * {@code GET  /user-shipping-addresses/:id} : get the "id" userShippingAddress.
     *
     * @param id the id of the userShippingAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userShippingAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-shipping-addresses/{id}")
    public ResponseEntity<UserShippingAddressDTO> getUserShippingAddress(@PathVariable Long id) {
        log.debug("REST request to get UserShippingAddress : {}", id);
        Optional<UserShippingAddressDTO> userShippingAddressDTO = userShippingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userShippingAddressDTO);
    }

    /**
     * {@code DELETE  /user-shipping-addresses/:id} : delete the "id" userShippingAddress.
     *
     * @param id the id of the userShippingAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-shipping-addresses/{id}")
    public ResponseEntity<Void> deleteUserShippingAddress(@PathVariable Long id) {
        log.debug("REST request to delete UserShippingAddress : {}", id);
        userShippingAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
