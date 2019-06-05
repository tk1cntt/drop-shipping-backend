package com.dropshipping.service;

import com.dropshipping.domain.UserShippingAddress;
import com.dropshipping.repository.UserShippingAddressRepository;
import com.dropshipping.service.dto.UserShippingAddressDTO;
import com.dropshipping.service.mapper.UserShippingAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserShippingAddress}.
 */
@Service
@Transactional
public class UserShippingAddressService {

    private final Logger log = LoggerFactory.getLogger(UserShippingAddressService.class);

    private final UserShippingAddressRepository userShippingAddressRepository;

    private final UserShippingAddressMapper userShippingAddressMapper;

    public UserShippingAddressService(UserShippingAddressRepository userShippingAddressRepository, UserShippingAddressMapper userShippingAddressMapper) {
        this.userShippingAddressRepository = userShippingAddressRepository;
        this.userShippingAddressMapper = userShippingAddressMapper;
    }

    /**
     * Save a userShippingAddress.
     *
     * @param userShippingAddressDTO the entity to save.
     * @return the persisted entity.
     */
    public UserShippingAddressDTO save(UserShippingAddressDTO userShippingAddressDTO) {
        log.debug("Request to save UserShippingAddress : {}", userShippingAddressDTO);
        UserShippingAddress userShippingAddress = userShippingAddressMapper.toEntity(userShippingAddressDTO);
        userShippingAddress = userShippingAddressRepository.save(userShippingAddress);
        return userShippingAddressMapper.toDto(userShippingAddress);
    }

    /**
     * Get all the userShippingAddresses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserShippingAddressDTO> findAll() {
        log.debug("Request to get all UserShippingAddresses");
        return userShippingAddressRepository.findAll().stream()
            .map(userShippingAddressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userShippingAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserShippingAddressDTO> findOne(Long id) {
        log.debug("Request to get UserShippingAddress : {}", id);
        return userShippingAddressRepository.findById(id)
            .map(userShippingAddressMapper::toDto);
    }

    /**
     * Delete the userShippingAddress by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserShippingAddress : {}", id);
        userShippingAddressRepository.deleteById(id);
    }
}
