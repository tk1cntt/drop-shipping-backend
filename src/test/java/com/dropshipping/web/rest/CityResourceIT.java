package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.City;
import com.dropshipping.domain.Country;
import com.dropshipping.domain.District;
import com.dropshipping.repository.CityRepository;
import com.dropshipping.service.CityService;
import com.dropshipping.service.dto.CityDTO;
import com.dropshipping.service.mapper.CityMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;
import com.dropshipping.service.dto.CityCriteria;
import com.dropshipping.service.CityQueryService;

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
 * Integration tests for the {@Link CityResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class CityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_INDEX = 1;
    private static final Integer UPDATED_INDEX = 2;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityQueryService cityQueryService;

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

    private MockMvc restCityMockMvc;

    private City city;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityResource cityResource = new CityResource(cityService, cityQueryService);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
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
    public static City createEntity(EntityManager em) {
        City city = new City()
            .name(DEFAULT_NAME)
            .index(DEFAULT_INDEX)
            .enabled(DEFAULT_ENABLED)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return city;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity(EntityManager em) {
        City city = new City()
            .name(UPDATED_NAME)
            .index(UPDATED_INDEX)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return city;
    }

    @BeforeEach
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCity.getIndex()).isEqualTo(DEFAULT_INDEX);
        assertThat(testCity.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testCity.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testCity.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City with an existing ID
        city.setId(1L);
        CityDTO cityDTO = cityMapper.toDto(city);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.index").value(DEFAULT_INDEX))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where name equals to DEFAULT_NAME
        defaultCityShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cityList where name equals to UPDATED_NAME
        defaultCityShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCityShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cityList where name equals to UPDATED_NAME
        defaultCityShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where name is not null
        defaultCityShouldBeFound("name.specified=true");

        // Get all the cityList where name is null
        defaultCityShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where index equals to DEFAULT_INDEX
        defaultCityShouldBeFound("index.equals=" + DEFAULT_INDEX);

        // Get all the cityList where index equals to UPDATED_INDEX
        defaultCityShouldNotBeFound("index.equals=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllCitiesByIndexIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where index in DEFAULT_INDEX or UPDATED_INDEX
        defaultCityShouldBeFound("index.in=" + DEFAULT_INDEX + "," + UPDATED_INDEX);

        // Get all the cityList where index equals to UPDATED_INDEX
        defaultCityShouldNotBeFound("index.in=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllCitiesByIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where index is not null
        defaultCityShouldBeFound("index.specified=true");

        // Get all the cityList where index is null
        defaultCityShouldNotBeFound("index.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByIndexIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where index greater than or equals to DEFAULT_INDEX
        defaultCityShouldBeFound("index.greaterOrEqualThan=" + DEFAULT_INDEX);

        // Get all the cityList where index greater than or equals to UPDATED_INDEX
        defaultCityShouldNotBeFound("index.greaterOrEqualThan=" + UPDATED_INDEX);
    }

    @Test
    @Transactional
    public void getAllCitiesByIndexIsLessThanSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where index less than or equals to DEFAULT_INDEX
        defaultCityShouldNotBeFound("index.lessThan=" + DEFAULT_INDEX);

        // Get all the cityList where index less than or equals to UPDATED_INDEX
        defaultCityShouldBeFound("index.lessThan=" + UPDATED_INDEX);
    }


    @Test
    @Transactional
    public void getAllCitiesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where enabled equals to DEFAULT_ENABLED
        defaultCityShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the cityList where enabled equals to UPDATED_ENABLED
        defaultCityShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCitiesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultCityShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the cityList where enabled equals to UPDATED_ENABLED
        defaultCityShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void getAllCitiesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where enabled is not null
        defaultCityShouldBeFound("enabled.specified=true");

        // Get all the cityList where enabled is null
        defaultCityShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where createAt equals to DEFAULT_CREATE_AT
        defaultCityShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the cityList where createAt equals to UPDATED_CREATE_AT
        defaultCityShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultCityShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the cityList where createAt equals to UPDATED_CREATE_AT
        defaultCityShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where createAt is not null
        defaultCityShouldBeFound("createAt.specified=true");

        // Get all the cityList where createAt is null
        defaultCityShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where createAt greater than or equals to DEFAULT_CREATE_AT
        defaultCityShouldBeFound("createAt.greaterOrEqualThan=" + DEFAULT_CREATE_AT);

        // Get all the cityList where createAt greater than or equals to UPDATED_CREATE_AT
        defaultCityShouldNotBeFound("createAt.greaterOrEqualThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where createAt less than or equals to DEFAULT_CREATE_AT
        defaultCityShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the cityList where createAt less than or equals to UPDATED_CREATE_AT
        defaultCityShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllCitiesByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where updateAt equals to DEFAULT_UPDATE_AT
        defaultCityShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the cityList where updateAt equals to UPDATED_UPDATE_AT
        defaultCityShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultCityShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the cityList where updateAt equals to UPDATED_UPDATE_AT
        defaultCityShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where updateAt is not null
        defaultCityShouldBeFound("updateAt.specified=true");

        // Get all the cityList where updateAt is null
        defaultCityShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where updateAt greater than or equals to DEFAULT_UPDATE_AT
        defaultCityShouldBeFound("updateAt.greaterOrEqualThan=" + DEFAULT_UPDATE_AT);

        // Get all the cityList where updateAt greater than or equals to UPDATED_UPDATE_AT
        defaultCityShouldNotBeFound("updateAt.greaterOrEqualThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllCitiesByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where updateAt less than or equals to DEFAULT_UPDATE_AT
        defaultCityShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the cityList where updateAt less than or equals to UPDATED_UPDATE_AT
        defaultCityShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllCitiesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        Country country = CountryResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        city.setCountry(country);
        cityRepository.saveAndFlush(city);
        Long countryId = country.getId();

        // Get all the cityList where country equals to countryId
        defaultCityShouldBeFound("countryId.equals=" + countryId);

        // Get all the cityList where country equals to countryId + 1
        defaultCityShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }


    @Test
    @Transactional
    public void getAllCitiesByDistrictsIsEqualToSomething() throws Exception {
        // Initialize the database
        District districts = DistrictResourceIT.createEntity(em);
        em.persist(districts);
        em.flush();
        city.addDistricts(districts);
        cityRepository.saveAndFlush(city);
        Long districtsId = districts.getId();

        // Get all the cityList where districts equals to districtsId
        defaultCityShouldBeFound("districtsId.equals=" + districtsId);

        // Get all the cityList where districts equals to districtsId + 1
        defaultCityShouldNotBeFound("districtsId.equals=" + (districtsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCityShouldBeFound(String filter) throws Exception {
        restCityMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].index").value(hasItem(DEFAULT_INDEX)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restCityMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCityShouldNotBeFound(String filter) throws Exception {
        restCityMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCityMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getId()).get();
        // Disconnect from session so that the updates on updatedCity are not directly saved in db
        em.detach(updatedCity);
        updatedCity
            .name(UPDATED_NAME)
            .index(UPDATED_INDEX)
            .enabled(UPDATED_ENABLED)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        CityDTO cityDTO = cityMapper.toDto(updatedCity);

        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCity.getIndex()).isEqualTo(UPDATED_INDEX);
        assertThat(testCity.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testCity.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testCity.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = new City();
        city1.setId(1L);
        City city2 = new City();
        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);
        city2.setId(2L);
        assertThat(city1).isNotEqualTo(city2);
        city1.setId(null);
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityDTO.class);
        CityDTO cityDTO1 = new CityDTO();
        cityDTO1.setId(1L);
        CityDTO cityDTO2 = new CityDTO();
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
        cityDTO2.setId(cityDTO1.getId());
        assertThat(cityDTO1).isEqualTo(cityDTO2);
        cityDTO2.setId(2L);
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
        cityDTO1.setId(null);
        assertThat(cityDTO1).isNotEqualTo(cityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cityMapper.fromId(null)).isNull();
    }
}
