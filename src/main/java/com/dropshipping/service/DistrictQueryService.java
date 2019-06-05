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

import com.dropshipping.domain.District;
import com.dropshipping.domain.*; // for static metamodels
import com.dropshipping.repository.DistrictRepository;
import com.dropshipping.service.dto.DistrictCriteria;
import com.dropshipping.service.dto.DistrictDTO;
import com.dropshipping.service.mapper.DistrictMapper;

/**
 * Service for executing complex queries for {@link District} entities in the database.
 * The main input is a {@link DistrictCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DistrictDTO} or a {@link Page} of {@link DistrictDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistrictQueryService extends QueryService<District> {

    private final Logger log = LoggerFactory.getLogger(DistrictQueryService.class);

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    public DistrictQueryService(DistrictRepository districtRepository, DistrictMapper districtMapper) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    /**
     * Return a {@link List} of {@link DistrictDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DistrictDTO> findByCriteria(DistrictCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<District> specification = createSpecification(criteria);
        return districtMapper.toDto(districtRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DistrictDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DistrictDTO> findByCriteria(DistrictCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<District> specification = createSpecification(criteria);
        return districtRepository.findAll(specification, page)
            .map(districtMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistrictCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<District> specification = createSpecification(criteria);
        return districtRepository.count(specification);
    }

    /**
     * Function to convert DistrictCriteria to a {@link Specification}.
     */
    private Specification<District> createSpecification(DistrictCriteria criteria) {
        Specification<District> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), District_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), District_.name));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), District_.enabled));
            }
            if (criteria.getCreateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateAt(), District_.createAt));
            }
            if (criteria.getUpdateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateAt(), District_.updateAt));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(District_.city, JoinType.LEFT).get(City_.id)));
            }
            if (criteria.getWardsId() != null) {
                specification = specification.and(buildSpecification(criteria.getWardsId(),
                    root -> root.join(District_.wards, JoinType.LEFT).get(Ward_.id)));
            }
        }
        return specification;
    }
}
