package com.dropshipping.service;

import com.dropshipping.domain.DeliveryPackage;
import com.dropshipping.repository.DeliveryPackageRepository;
import com.dropshipping.service.dto.DeliveryPackageDTO;
import com.dropshipping.service.mapper.DeliveryPackageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DeliveryPackage}.
 */
@Service
@Transactional
public class DeliveryPackageService {

    private final Logger log = LoggerFactory.getLogger(DeliveryPackageService.class);

    private final DeliveryPackageRepository deliveryPackageRepository;

    private final DeliveryPackageMapper deliveryPackageMapper;

    public DeliveryPackageService(DeliveryPackageRepository deliveryPackageRepository, DeliveryPackageMapper deliveryPackageMapper) {
        this.deliveryPackageRepository = deliveryPackageRepository;
        this.deliveryPackageMapper = deliveryPackageMapper;
    }

    /**
     * Save a deliveryPackage.
     *
     * @param deliveryPackageDTO the entity to save.
     * @return the persisted entity.
     */
    public DeliveryPackageDTO save(DeliveryPackageDTO deliveryPackageDTO) {
        log.debug("Request to save DeliveryPackage : {}", deliveryPackageDTO);
        DeliveryPackage deliveryPackage = deliveryPackageMapper.toEntity(deliveryPackageDTO);
        deliveryPackage = deliveryPackageRepository.save(deliveryPackage);
        return deliveryPackageMapper.toDto(deliveryPackage);
    }

    /**
     * Get all the deliveryPackages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryPackageDTO> findAll() {
        log.debug("Request to get all DeliveryPackages");
        return deliveryPackageRepository.findAll().stream()
            .map(deliveryPackageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one deliveryPackage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeliveryPackageDTO> findOne(Long id) {
        log.debug("Request to get DeliveryPackage : {}", id);
        return deliveryPackageRepository.findById(id)
            .map(deliveryPackageMapper::toDto);
    }

    /**
     * Delete the deliveryPackage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DeliveryPackage : {}", id);
        deliveryPackageRepository.deleteById(id);
    }
}
