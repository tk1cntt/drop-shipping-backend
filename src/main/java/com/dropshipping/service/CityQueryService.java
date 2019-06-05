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

import com.dropshipping.domain.City;
import com.dropshipping.domain.*; // for static metamodels
import com.dropshipping.repository.CityRepository;
import com.dropshipping.service.dto.CityCriteria;
import com.dropshipping.service.dto.CityDTO;
import com.dropshipping.service.mapper.CityMapper;

/**
 * Service for executing complex queries for {@link City} entities in the database.
 * The main input is a {@link CityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CityDTO} or a {@link Page} of {@link CityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CityQueryService extends QueryService<City> {

    private final Logger log = LoggerFactory.getLogger(CityQueryService.class);

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    public CityQueryService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    /**
     * Return a {@link List} of {@link CityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CityDTO> findByCriteria(CityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<City> specification = createSpecification(criteria);
        return cityMapper.toDto(cityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CityDTO> findByCriteria(CityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<City> specification = createSpecification(criteria);
        return cityRepository.findAll(specification, page)
            .map(cityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<City> specification = createSpecification(criteria);
        return cityRepository.count(specification);
    }

    /**
     * Function to convert CityCriteria to a {@link Specification}.
     */
    private Specification<City> createSpecification(CityCriteria criteria) {
        Specification<City> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), City_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), City_.name));
            }
            if (criteria.getIndex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndex(), City_.index));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), City_.enabled));
            }
            if (criteria.getCreateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateAt(), City_.createAt));
            }
            if (criteria.getUpdateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateAt(), City_.updateAt));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(City_.country, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getDistrictsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistrictsId(),
                    root -> root.join(City_.districts, JoinType.LEFT).get(District_.id)));
            }
        }
        return specification;
    }
}
