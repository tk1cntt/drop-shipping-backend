package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.Region;
import com.dropshipping.domain.Country;
import com.dropshipping.domain.User;
import com.dropshipping.repository.RegionRepository;
import com.dropshipping.service.RegionService;
import com.dropshipping.service.dto.RegionDTO;
import com.dropshipping.service.mapper.RegionMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;
import com.dropshipping.service.dto.RegionCriteria;
import com.dropshipping.service.RegionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.dropshipping.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RegionResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class RegionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RegionRepository regionRepository;

    @Mock
    private RegionRepository regionRepositoryMock;

    @Autowired
    private RegionMapper regionMapper;

    @Mock
    private RegionService regionServiceMock;

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionQueryService regionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRegionMockMvc;

    private Region region;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionResource regionResource = new RegionResource(regionService, regionQueryService);
        this.restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createEntity(EntityManager em) {
        Region region = new Region()
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return region;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createUpdatedEntity(EntityManager em) {
        Region region = new Region()
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return region;
    }

    @BeforeEach
    public void initTest() {
        region = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegion.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testRegion.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testRegion.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region with an existing ID
        region.setId(1L);
        RegionDTO regionDTO = regionMapper.toDto(region);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRegionsWithEagerRelationshipsIsEnabled() throws Exception {
        RegionResource regionResource = new RegionResource(regionServiceMock, regionQueryService);
        when(regionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegionMockMvc.perform(get("/api/regions?eagerload=true"))
        .andExpect(status().isOk());

        verify(regionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRegionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RegionResource regionResource = new RegionResource(regionServiceMock, regionQueryService);
            when(regionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegionMockMvc.perform(get("/api/regions?eagerload=true"))
        .andExpect(status().isOk());

            verify(regionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name equals to DEFAULT_NAME
        defaultRegionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionList where name equals to UPDATED_NAME
        defaultRegionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionList where name equals to UPDATED_NAME
        defaultRegionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name is not null
        defaultRegionShouldBeFound("name.specified=true");

        // Get all the regionList where name is null
        defaultRegionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where enabled equals to DEFAULT_ENABLED
        defaultRegionShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the regionList where enabled equals to UPDATED_ENABLED
        defaultRegionShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllRegionsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultRegionShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the regionList where enabled equals to UPDATED_ENABLED
        defaultRegionShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllRegionsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where enabled is not null
        defaultRegionShouldBeFound("enabled.specified=true");

        // Get all the regionList where enabled is null
        defaultRegionShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createAt equals to DEFAULT_CREATE_AT
        defaultRegionShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the regionList where createAt equals to UPDATED_CREATE_AT
        defaultRegionShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultRegionShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the regionList where createAt equals to UPDATED_CREATE_AT
        defaultRegionShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createAt is not null
        defaultRegionShouldBeFound("createAt.specified=true");

        // Get all the regionList where createAt is null
        defaultRegionShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createAt greater than or equals to DEFAULT_CREATE_AT
        defaultRegionShouldBeFound("createAt.greaterOrEqualThan=" + DEFAULT_CREATE_AT);

        // Get all the regionList where createAt greater than or equals to UPDATED_CREATE_AT
        defaultRegionShouldNotBeFound("createAt.greaterOrEqualThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where createAt less than or equals to DEFAULT_CREATE_AT
        defaultRegionShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the regionList where createAt less than or equals to UPDATED_CREATE_AT
        defaultRegionShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllRegionsByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where updateAt equals to DEFAULT_UPDATE_AT
        defaultRegionShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the regionList where updateAt equals to UPDATED_UPDATE_AT
        defaultRegionShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultRegionShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the regionList where updateAt equals to UPDATED_UPDATE_AT
        defaultRegionShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where updateAt is not null
        defaultRegionShouldBeFound("updateAt.specified=true");

        // Get all the regionList where updateAt is null
        defaultRegionShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where updateAt greater than or equals to DEFAULT_UPDATE_AT
        defaultRegionShouldBeFound("updateAt.greaterOrEqualThan=" + DEFAULT_UPDATE_AT);

        // Get all the regionList where updateAt greater than or equals to UPDATED_UPDATE_AT
        defaultRegionShouldNotBeFound("updateAt.greaterOrEqualThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllRegionsByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where updateAt less than or equals to DEFAULT_UPDATE_AT
        defaultRegionShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the regionList where updateAt less than or equals to UPDATED_UPDATE_AT
        defaultRegionShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllRegionsByCountriesIsEqualToSomething() throws Exception {
        // Initialize the database
        Country countries = CountryResourceIT.createEntity(em);
        em.persist(countries);
        em.flush();
        region.addCountries(countries);
        regionRepository.saveAndFlush(region);
        Long countriesId = countries.getId();

        // Get all the regionList where countries equals to countriesId
        defaultRegionShouldBeFound("countriesId.equals=" + countriesId);

        // Get all the regionList where countries equals to countriesId + 1
        defaultRegionShouldNotBeFound("countriesId.equals=" + (countriesId + 1));
    }


    @Test
    @Transactional
    public void getAllRegionsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        region.addUser(user);
        regionRepository.saveAndFlush(region);
        Long userId = user.getId();

        // Get all the regionList where user equals to userId
        defaultRegionShouldBeFound("userId.equals=" + userId);

        // Get all the regionList where user equals to userId + 1
        defaultRegionShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegionShouldBeFound(String filter) throws Exception {
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restRegionMockMvc.perform(get("/api/regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegionShouldNotBeFound(String filter) throws Exception {
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegionMockMvc.perform(get("/api/regions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region
        Region updatedRegion = regionRepository.findById(region.getId()).get();
        // Disconnect from session so that the updates on updatedRegion are not directly saved in db
        em.detach(updatedRegion);
        updatedRegion
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        RegionDTO regionDTO = regionMapper.toDto(updatedRegion);

        restRegionMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegion.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testRegion.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testRegion.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Create the Region
        RegionDTO regionDTO = regionMapper.toDto(region);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Delete the region
        restRegionMockMvc.perform(delete("/api/regions/{id}", region.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Region.class);
        Region region1 = new Region();
        region1.setId(1L);
        Region region2 = new Region();
        region2.setId(region1.getId());
        assertThat(region1).isEqualTo(region2);
        region2.setId(2L);
        assertThat(region1).isNotEqualTo(region2);
        region1.setId(null);
        assertThat(region1).isNotEqualTo(region2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionDTO.class);
        RegionDTO regionDTO1 = new RegionDTO();
        regionDTO1.setId(1L);
        RegionDTO regionDTO2 = new RegionDTO();
        assertThat(regionDTO1).isNotEqualTo(regionDTO2);
        regionDTO2.setId(regionDTO1.getId());
        assertThat(regionDTO1).isEqualTo(regionDTO2);
        regionDTO2.setId(2L);
        assertThat(regionDTO1).isNotEqualTo(regionDTO2);
        regionDTO1.setId(null);
        assertThat(regionDTO1).isNotEqualTo(regionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(regionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(regionMapper.fromId(null)).isNull();
    }
}
