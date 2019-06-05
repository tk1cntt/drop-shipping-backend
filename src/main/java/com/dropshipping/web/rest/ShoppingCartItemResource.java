package com.dropshipping.web.rest;

import com.dropshipping.service.ShoppingCartItemService;
import com.dropshipping.web.rest.errors.BadRequestAlertException;
import com.dropshipping.service.dto.ShoppingCartItemDTO;

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
 * REST controller for managing {@link com.dropshipping.domain.ShoppingCartItem}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartItemResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemResource.class);

    private static final String ENTITY_NAME = "shoppingCartItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingCartItemService shoppingCartItemService;

    public ShoppingCartItemResource(ShoppingCartItemService shoppingCartItemService) {
        this.shoppingCartItemService = shoppingCartItemService;
    }

    /**
     * {@code POST  /shopping-cart-items} : Create a new shoppingCartItem.
     *
     * @param shoppingCartItemDTO the shoppingCartItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingCartItemDTO, or with status {@code 400 (Bad Request)} if the shoppingCartItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemDTO> createShoppingCartItem(@RequestBody ShoppingCartItemDTO shoppingCartItemDTO) throws URISyntaxException {
        log.debug("REST request to save ShoppingCartItem : {}", shoppingCartItemDTO);
        if (shoppingCartItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCartItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartItemDTO result = shoppingCartItemService.save(shoppingCartItemDTO);
        return ResponseEntity.created(new URI("/api/shopping-cart-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-cart-items} : Updates an existing shoppingCartItem.
     *
     * @param shoppingCartItemDTO the shoppingCartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingCartItemDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingCartItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingCartItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-cart-items")
    public ResponseEntity<ShoppingCartItemDTO> updateShoppingCartItem(@RequestBody ShoppingCartItemDTO shoppingCartItemDTO) throws URISyntaxException {
        log.debug("REST request to update ShoppingCartItem : {}", shoppingCartItemDTO);
        if (shoppingCartItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShoppingCartItemDTO result = shoppingCartItemService.save(shoppingCartItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shoppingCartItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shopping-cart-items} : get all the shoppingCartItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingCartItems in body.
     */
    @GetMapping("/shopping-cart-items")
    public List<ShoppingCartItemDTO> getAllShoppingCartItems() {
        log.debug("REST request to get all ShoppingCartItems");
        return shoppingCartItemService.findAll();
    }

    /**
     * {@code GET  /shopping-cart-items/:id} : get the "id" shoppingCartItem.
     *
     * @param id the id of the shoppingCartItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingCartItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-cart-items/{id}")
    public ResponseEntity<ShoppingCartItemDTO> getShoppingCartItem(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCartItem : {}", id);
        Optional<ShoppingCartItemDTO> shoppingCartItemDTO = shoppingCartItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartItemDTO);
    }

    /**
     * {@code DELETE  /shopping-cart-items/:id} : delete the "id" shoppingCartItem.
     *
     * @param id the id of the shoppingCartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-cart-items/{id}")
    public ResponseEntity<Void> deleteShoppingCartItem(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCartItem : {}", id);
        shoppingCartItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
