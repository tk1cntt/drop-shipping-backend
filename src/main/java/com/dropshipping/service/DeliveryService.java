package com.dropshipping.service;

import com.dropshipping.domain.Delivery;
import com.dropshipping.repository.DeliveryRepository;
import com.dropshipping.service.dto.DeliveryDTO;
import com.dropshipping.service.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Delivery}.
 */
@Service
@Transactional
public class DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryDTO save(DeliveryDTO deliveryDTO) {
        log.debug("Request to save Delivery : {}", deliveryDTO);
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        delivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(delivery);
    }

    /**
     * Get all the deliveries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> findAll() {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll().stream()
            .map(deliveryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one delivery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeliveryDTO> findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        return deliveryRepository.findById(id)
            .map(deliveryMapper::toDto);
    }

    /**
     * Delete the delivery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.deleteById(id);
    }
}
