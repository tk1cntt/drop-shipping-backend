package com.dropshipping.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dropshipping.domain.UserProfile;
import com.dropshipping.domain.*; // for static metamodels
import com.dropshipping.repository.UserProfileRepository;
import com.dropshipping.service.dto.UserProfileCriteria;
import com.dropshipping.service.dto.UserProfileDTO;
import com.dropshipping.service.mapper.UserProfileMapper;

/**
 * Service for executing complex queries for {@link UserProfile} entities in the database.
 * The main input is a {@link UserProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserProfileDTO} or a {@link Page} of {@link UserProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserProfileQueryService extends QueryService<UserProfile> {

    private final Logger log = LoggerFactory.getLogger(UserProfileQueryService.class);

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    public UserProfileQueryService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * Return a {@link List} of {@link UserProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserProfileDTO> findByCriteria(UserProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileMapper.toDto(userProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserProfileDTO> findByCriteria(UserProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileRepository.findAll(specification, page)
            .map(userProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileRepository.count(specification);
    }

    /**
     * Function to convert UserProfileCriteria to a {@link Specification}.
     */
    private Specification<UserProfile> createSpecification(UserProfileCriteria criteria) {
        Specification<UserProfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserProfile_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), UserProfile_.fullName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), UserProfile_.gender));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), UserProfile_.email));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), UserProfile_.mobile));
            }
            if (criteria.getCreateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateAt(), UserProfile_.createAt));
            }
            if (criteria.getUpdateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateAt(), UserProfile_.updateAt));
            }
            if (criteria.getCreateById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreateById(),
                    root -> root.join(UserProfile_.createBy, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUpdateById() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdateById(),
                    root -> root.join(UserProfile_.updateBy, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(UserProfile_.city, JoinType.LEFT).get(City_.id)));
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictId(),
                    root -> root.join(UserProfile_.district, JoinType.LEFT).get(District_.id)));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(UserProfile_.addresses, JoinType.LEFT).get(UserShippingAddress_.id)));
            }
        }
        return specification;
    }
}
