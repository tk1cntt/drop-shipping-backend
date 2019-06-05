package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.Ward;
import com.dropshipping.domain.District;
import com.dropshipping.repository.WardRepository;
import com.dropshipping.service.WardService;
import com.dropshipping.service.dto.WardDTO;
import com.dropshipping.service.mapper.WardMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;
import com.dropshipping.service.dto.WardCriteria;
import com.dropshipping.service.WardQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.List;

import static com.dropshipping.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link WardResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class WardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private WardMapper wardMapper;

    @Autowired
    private WardService wardService;

    @Autowired
    private WardQueryService wardQueryService;

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

    private MockMvc restWardMockMvc;

    private Ward ward;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WardResource wardResource = new WardResource(wardService, wardQueryService);
        this.restWardMockMvc = MockMvcBuilders.standaloneSetup(wardResource)
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
    public static Ward createEntity(EntityManager em) {
        Ward ward = new Ward()
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return ward;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ward createUpdatedEntity(EntityManager em) {
        Ward ward = new Ward()
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return ward;
    }

    @BeforeEach
    public void initTest() {
        ward = createEntity(em);
    }

    @Test
    @Transactional
    public void createWard() throws Exception {
        int databaseSizeBeforeCreate = wardRepository.findAll().size();

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);
        restWardMockMvc.perform(post("/api/wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isCreated());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeCreate + 1);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWard.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testWard.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testWard.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createWardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wardRepository.findAll().size();

        // Create the Ward with an existing ID
        ward.setId(1L);
        WardDTO wardDTO = wardMapper.toDto(ward);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWardMockMvc.perform(post("/api/wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWards() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList
        restWardMockMvc.perform(get("/api/wards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ward.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get the ward
        restWardMockMvc.perform(get("/api/wards/{id}", ward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ward.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getAllWardsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where name equals to DEFAULT_NAME
        defaultWardShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the wardList where name equals to UPDATED_NAME
        defaultWardShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWardsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWardShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the wardList where name equals to UPDATED_NAME
        defaultWardShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWardsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where name is not null
        defaultWardShouldBeFound("name.specified=true");

        // Get all the wardList where name is null
        defaultWardShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllWardsByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where enabled equals to DEFAULT_ENABLED
        defaultWardShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the wardList where enabled equals to UPDATED_ENABLED
        defaultWardShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllWardsByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultWardShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the wardList where enabled equals to UPDATED_ENABLED
        defaultWardShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllWardsByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where enabled is not null
        defaultWardShouldBeFound("enabled.specified=true");

        // Get all the wardList where enabled is null
        defaultWardShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllWardsByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where createAt equals to DEFAULT_CREATE_AT
        defaultWardShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the wardList where createAt equals to UPDATED_CREATE_AT
        defaultWardShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultWardShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the wardList where createAt equals to UPDATED_CREATE_AT
        defaultWardShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where createAt is not null
        defaultWardShouldBeFound("createAt.specified=true");

        // Get all the wardList where createAt is null
        defaultWardShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllWardsByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where createAt greater than or equals to DEFAULT_CREATE_AT
        defaultWardShouldBeFound("createAt.greaterOrEqualThan=" + DEFAULT_CREATE_AT);

        // Get all the wardList where createAt greater than or equals to UPDATED_CREATE_AT
        defaultWardShouldNotBeFound("createAt.greaterOrEqualThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where createAt less than or equals to DEFAULT_CREATE_AT
        defaultWardShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the wardList where createAt less than or equals to UPDATED_CREATE_AT
        defaultWardShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllWardsByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where updateAt equals to DEFAULT_UPDATE_AT
        defaultWardShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the wardList where updateAt equals to UPDATED_UPDATE_AT
        defaultWardShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultWardShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the wardList where updateAt equals to UPDATED_UPDATE_AT
        defaultWardShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where updateAt is not null
        defaultWardShouldBeFound("updateAt.specified=true");

        // Get all the wardList where updateAt is null
        defaultWardShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllWardsByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where updateAt greater than or equals to DEFAULT_UPDATE_AT
        defaultWardShouldBeFound("updateAt.greaterOrEqualThan=" + DEFAULT_UPDATE_AT);

        // Get all the wardList where updateAt greater than or equals to UPDATED_UPDATE_AT
        defaultWardShouldNotBeFound("updateAt.greaterOrEqualThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllWardsByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        // Get all the wardList where updateAt less than or equals to DEFAULT_UPDATE_AT
        defaultWardShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the wardList where updateAt less than or equals to UPDATED_UPDATE_AT
        defaultWardShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllWardsByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        ward.setDistrict(district);
        wardRepository.saveAndFlush(ward);
        Long districtId = district.getId();

        // Get all the wardList where district equals to districtId
        defaultWardShouldBeFound("districtId.equals=" + districtId);

        // Get all the wardList where district equals to districtId + 1
        defaultWardShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWardShouldBeFound(String filter) throws Exception {
        restWardMockMvc.perform(get("/api/wards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ward.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restWardMockMvc.perform(get("/api/wards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWardShouldNotBeFound(String filter) throws Exception {
        restWardMockMvc.perform(get("/api/wards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWardMockMvc.perform(get("/api/wards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWard() throws Exception {
        // Get the ward
        restWardMockMvc.perform(get("/api/wards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeUpdate = wardRepository.findAll().size();

        // Update the ward
        Ward updatedWard = wardRepository.findById(ward.getId()).get();
        // Disconnect from session so that the updates on updatedWard are not directly saved in db
        em.detach(updatedWard);
        updatedWard
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        WardDTO wardDTO = wardMapper.toDto(updatedWard);

        restWardMockMvc.perform(put("/api/wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isOk());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
        Ward testWard = wardList.get(wardList.size() - 1);
        assertThat(testWard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWard.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testWard.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testWard.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingWard() throws Exception {
        int databaseSizeBeforeUpdate = wardRepository.findAll().size();

        // Create the Ward
        WardDTO wardDTO = wardMapper.toDto(ward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardMockMvc.perform(put("/api/wards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ward in the database
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWard() throws Exception {
        // Initialize the database
        wardRepository.saveAndFlush(ward);

        int databaseSizeBeforeDelete = wardRepository.findAll().size();

        // Delete the ward
        restWardMockMvc.perform(delete("/api/wards/{id}", ward.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Ward> wardList = wardRepository.findAll();
        assertThat(wardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ward.class);
        Ward ward1 = new Ward();
        ward1.setId(1L);
        Ward ward2 = new Ward();
        ward2.setId(ward1.getId());
        assertThat(ward1).isEqualTo(ward2);
        ward2.setId(2L);
        assertThat(ward1).isNotEqualTo(ward2);
        ward1.setId(null);
        assertThat(ward1).isNotEqualTo(ward2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WardDTO.class);
        WardDTO wardDTO1 = new WardDTO();
        wardDTO1.setId(1L);
        WardDTO wardDTO2 = new WardDTO();
        assertThat(wardDTO1).isNotEqualTo(wardDTO2);
        wardDTO2.setId(wardDTO1.getId());
        assertThat(wardDTO1).isEqualTo(wardDTO2);
        wardDTO2.setId(2L);
        assertThat(wardDTO1).isNotEqualTo(wardDTO2);
        wardDTO1.setId(null);
        assertThat(wardDTO1).isNotEqualTo(wardDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wardMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wardMapper.fromId(null)).isNull();
    }
}
