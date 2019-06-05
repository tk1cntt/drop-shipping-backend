package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.Country;
import com.dropshipping.domain.City;
import com.dropshipping.domain.Region;
import com.dropshipping.repository.CountryRepository;
import com.dropshipping.service.CountryService;
import com.dropshipping.service.dto.CountryDTO;
import com.dropshipping.service.mapper.CountryMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;
import com.dropshipping.service.dto.CountryCriteria;
import com.dropshipping.service.CountryQueryService;

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
 * Integration tests for the {@Link CountryResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class CountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryQueryService countryQueryService;

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

    private MockMvc restCountryMockMvc;

    private Country country;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryResource countryResource = new CountryResource(countryService, countryQueryService);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
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
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .name(DEFAULT_NAME)
            .enabled(DEFAULT_ENABLED)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return country;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountry.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCountry.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testCountry.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name equals to DEFAULT_NAME
        defaultCountryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countryList where name equals to UPDATED_NAME
        defaultCountryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where name is not null
        defaultCountryShouldBeFound("name.specified=true");

        // Get all the countryList where name is null
        defaultCountryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where enabled equals to DEFAULT_ENABLED
        defaultCountryShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the countryList where enabled equals to UPDATED_ENABLED
        defaultCountryShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCountriesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultCountryShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the countryList where enabled equals to UPDATED_ENABLED
        defaultCountryShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCountriesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where enabled is not null
        defaultCountryShouldBeFound("enabled.specified=true");

        // Get all the countryList where enabled is null
        defaultCountryShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createAt equals to DEFAULT_CREATE_AT
        defaultCountryShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the countryList where createAt equals to UPDATED_CREATE_AT
        defaultCountryShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultCountryShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the countryList where createAt equals to UPDATED_CREATE_AT
        defaultCountryShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createAt is not null
        defaultCountryShouldBeFound("createAt.specified=true");

        // Get all the countryList where createAt is null
        defaultCountryShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createAt greater than or equals to DEFAULT_CREATE_AT
        defaultCountryShouldBeFound("createAt.greaterOrEqualThan=" + DEFAULT_CREATE_AT);

        // Get all the countryList where createAt greater than or equals to UPDATED_CREATE_AT
        defaultCountryShouldNotBeFound("createAt.greaterOrEqualThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where createAt less than or equals to DEFAULT_CREATE_AT
        defaultCountryShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the countryList where createAt less than or equals to UPDATED_CREATE_AT
        defaultCountryShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllCountriesByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where updateAt equals to DEFAULT_UPDATE_AT
        defaultCountryShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the countryList where updateAt equals to UPDATED_UPDATE_AT
        defaultCountryShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultCountryShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the countryList where updateAt equals to UPDATED_UPDATE_AT
        defaultCountryShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where updateAt is not null
        defaultCountryShouldBeFound("updateAt.specified=true");

        // Get all the countryList where updateAt is null
        defaultCountryShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where updateAt greater than or equals to DEFAULT_UPDATE_AT
        defaultCountryShouldBeFound("updateAt.greaterOrEqualThan=" + DEFAULT_UPDATE_AT);

        // Get all the countryList where updateAt greater than or equals to UPDATED_UPDATE_AT
        defaultCountryShouldNotBeFound("updateAt.greaterOrEqualThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCountriesByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where updateAt less than or equals to DEFAULT_UPDATE_AT
        defaultCountryShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the countryList where updateAt less than or equals to UPDATED_UPDATE_AT
        defaultCountryShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllCountriesByCitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        City cities = CityResourceIT.createEntity(em);
        em.persist(cities);
        em.flush();
        country.addCities(cities);
        countryRepository.saveAndFlush(country);
        Long citiesId = cities.getId();

        // Get all the countryList where cities equals to citiesId
        defaultCountryShouldBeFound("citiesId.equals=" + citiesId);

        // Get all the countryList where cities equals to citiesId + 1
        defaultCountryShouldNotBeFound("citiesId.equals=" + (citiesId + 1));
    }


    @Test
    @Transactional
    public void getAllCountriesByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        Region region = RegionResourceIT.createEntity(em);
        em.persist(region);
        em.flush();
        country.setRegion(region);
        countryRepository.saveAndFlush(country);
        Long regionId = region.getId();

        // Get all the countryList where region equals to regionId
        defaultCountryShouldBeFound("regionId.equals=" + regionId);

        // Get all the countryList where region equals to regionId + 1
        defaultCountryShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountryMockMvc.perform(get("/api/countries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .name(UPDATED_NAME)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountry.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCountry.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testCountry.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setId(1L);
        Country country2 = new Country();
        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);
        country2.setId(2L);
        assertThat(country1).isNotEqualTo(country2);
        country1.setId(null);
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDTO.class);
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setId(1L);
        CountryDTO countryDTO2 = new CountryDTO();
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO2.setId(countryDTO1.getId());
        assertThat(countryDTO1).isEqualTo(countryDTO2);
        countryDTO2.setId(2L);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO1.setId(null);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(countryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(countryMapper.fromId(null)).isNull();
    }
}
