package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.UserProfile;
import com.dropshipping.domain.User;
import com.dropshipping.domain.City;
import com.dropshipping.domain.District;
import com.dropshipping.domain.UserShippingAddress;
import com.dropshipping.repository.UserProfileRepository;
import com.dropshipping.service.UserProfileService;
import com.dropshipping.service.dto.UserProfileDTO;
import com.dropshipping.service.mapper.UserProfileMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;
import com.dropshipping.service.dto.UserProfileCriteria;
import com.dropshipping.service.UserProfileQueryService;

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

import com.dropshipping.domain.enumeration.Gender;
/**
 * Integration tests for the {@Link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class UserProfileResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileQueryService userProfileQueryService;

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

    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserProfileResource userProfileResource = new UserProfileResource(userProfileService, userProfileQueryService);
        this.restUserProfileMockMvc = MockMvcBuilders.standaloneSetup(userProfileResource)
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
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .fullName(DEFAULT_FULL_NAME)
            .gender(DEFAULT_GENDER)
            .email(DEFAULT_EMAIL)
            .mobile(DEFAULT_MOBILE)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .fullName(UPDATED_FULL_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUserProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserProfile.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testUserProfile.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where fullName equals to DEFAULT_FULL_NAME
        defaultUserProfileShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the userProfileList where fullName equals to UPDATED_FULL_NAME
        defaultUserProfileShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultUserProfileShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the userProfileList where fullName equals to UPDATED_FULL_NAME
        defaultUserProfileShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where fullName is not null
        defaultUserProfileShouldBeFound("fullName.specified=true");

        // Get all the userProfileList where fullName is null
        defaultUserProfileShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where gender equals to DEFAULT_GENDER
        defaultUserProfileShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the userProfileList where gender equals to UPDATED_GENDER
        defaultUserProfileShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUserProfileShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the userProfileList where gender equals to UPDATED_GENDER
        defaultUserProfileShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where gender is not null
        defaultUserProfileShouldBeFound("gender.specified=true");

        // Get all the userProfileList where gender is null
        defaultUserProfileShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email equals to DEFAULT_EMAIL
        defaultUserProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the userProfileList where email equals to UPDATED_EMAIL
        defaultUserProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUserProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the userProfileList where email equals to UPDATED_EMAIL
        defaultUserProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email is not null
        defaultUserProfileShouldBeFound("email.specified=true");

        // Get all the userProfileList where email is null
        defaultUserProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile equals to DEFAULT_MOBILE
        defaultUserProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the userProfileList where mobile equals to UPDATED_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultUserProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the userProfileList where mobile equals to UPDATED_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile is not null
        defaultUserProfileShouldBeFound("mobile.specified=true");

        // Get all the userProfileList where mobile is null
        defaultUserProfileShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createAt equals to DEFAULT_CREATE_AT
        defaultUserProfileShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the userProfileList where createAt equals to UPDATED_CREATE_AT
        defaultUserProfileShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultUserProfileShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the userProfileList where createAt equals to UPDATED_CREATE_AT
        defaultUserProfileShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createAt is not null
        defaultUserProfileShouldBeFound("createAt.specified=true");

        // Get all the userProfileList where createAt is null
        defaultUserProfileShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createAt greater than or equals to DEFAULT_CREATE_AT
        defaultUserProfileShouldBeFound("createAt.greaterOrEqualThan=" + DEFAULT_CREATE_AT);

        // Get all the userProfileList where createAt greater than or equals to UPDATED_CREATE_AT
        defaultUserProfileShouldNotBeFound("createAt.greaterOrEqualThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createAt less than or equals to DEFAULT_CREATE_AT
        defaultUserProfileShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the userProfileList where createAt less than or equals to UPDATED_CREATE_AT
        defaultUserProfileShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updateAt equals to DEFAULT_UPDATE_AT
        defaultUserProfileShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the userProfileList where updateAt equals to UPDATED_UPDATE_AT
        defaultUserProfileShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultUserProfileShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the userProfileList where updateAt equals to UPDATED_UPDATE_AT
        defaultUserProfileShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updateAt is not null
        defaultUserProfileShouldBeFound("updateAt.specified=true");

        // Get all the userProfileList where updateAt is null
        defaultUserProfileShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updateAt greater than or equals to DEFAULT_UPDATE_AT
        defaultUserProfileShouldBeFound("updateAt.greaterOrEqualThan=" + DEFAULT_UPDATE_AT);

        // Get all the userProfileList where updateAt greater than or equals to UPDATED_UPDATE_AT
        defaultUserProfileShouldNotBeFound("updateAt.greaterOrEqualThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updateAt less than or equals to DEFAULT_UPDATE_AT
        defaultUserProfileShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the userProfileList where updateAt less than or equals to UPDATED_UPDATE_AT
        defaultUserProfileShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByCreateByIsEqualToSomething() throws Exception {
        // Initialize the database
        User createBy = UserResourceIT.createEntity(em);
        em.persist(createBy);
        em.flush();
        userProfile.setCreateBy(createBy);
        userProfileRepository.saveAndFlush(userProfile);
        Long createById = createBy.getId();

        // Get all the userProfileList where createBy equals to createById
        defaultUserProfileShouldBeFound("createById.equals=" + createById);

        // Get all the userProfileList where createBy equals to createById + 1
        defaultUserProfileShouldNotBeFound("createById.equals=" + (createById + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByUpdateByIsEqualToSomething() throws Exception {
        // Initialize the database
        User updateBy = UserResourceIT.createEntity(em);
        em.persist(updateBy);
        em.flush();
        userProfile.setUpdateBy(updateBy);
        userProfileRepository.saveAndFlush(userProfile);
        Long updateById = updateBy.getId();

        // Get all the userProfileList where updateBy equals to updateById
        defaultUserProfileShouldBeFound("updateById.equals=" + updateById);

        // Get all the userProfileList where updateBy equals to updateById + 1
        defaultUserProfileShouldNotBeFound("updateById.equals=" + (updateById + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        City city = CityResourceIT.createEntity(em);
        em.persist(city);
        em.flush();
        userProfile.setCity(city);
        userProfileRepository.saveAndFlush(userProfile);
        Long cityId = city.getId();

        // Get all the userProfileList where city equals to cityId
        defaultUserProfileShouldBeFound("cityId.equals=" + cityId);

        // Get all the userProfileList where city equals to cityId + 1
        defaultUserProfileShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        District district = DistrictResourceIT.createEntity(em);
        em.persist(district);
        em.flush();
        userProfile.setDistrict(district);
        userProfileRepository.saveAndFlush(userProfile);
        Long districtId = district.getId();

        // Get all the userProfileList where district equals to districtId
        defaultUserProfileShouldBeFound("districtId.equals=" + districtId);

        // Get all the userProfileList where district equals to districtId + 1
        defaultUserProfileShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        UserShippingAddress address = UserShippingAddressResourceIT.createEntity(em);
        em.persist(address);
        em.flush();
        userProfile.addAddress(address);
        userProfileRepository.saveAndFlush(userProfile);
        Long addressId = address.getId();

        // Get all the userProfileList where address equals to addressId
        defaultUserProfileShouldBeFound("addressId.equals=" + addressId);

        // Get all the userProfileList where address equals to addressId + 1
        defaultUserProfileShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserProfileShouldBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserProfileShouldNotBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .fullName(UPDATED_FULL_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUserProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserProfile.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testUserProfile.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfile.class);
        UserProfile userProfile1 = new UserProfile();
        userProfile1.setId(1L);
        UserProfile userProfile2 = new UserProfile();
        userProfile2.setId(userProfile1.getId());
        assertThat(userProfile1).isEqualTo(userProfile2);
        userProfile2.setId(2L);
        assertThat(userProfile1).isNotEqualTo(userProfile2);
        userProfile1.setId(null);
        assertThat(userProfile1).isNotEqualTo(userProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileDTO.class);
        UserProfileDTO userProfileDTO1 = new UserProfileDTO();
        userProfileDTO1.setId(1L);
        UserProfileDTO userProfileDTO2 = new UserProfileDTO();
        assertThat(userProfileDTO1).isNotEqualTo(userProfileDTO2);
        userProfileDTO2.setId(userProfileDTO1.getId());
        assertThat(userProfileDTO1).isEqualTo(userProfileDTO2);
        userProfileDTO2.setId(2L);
        assertThat(userProfileDTO1).isNotEqualTo(userProfileDTO2);
        userProfileDTO1.setId(null);
        assertThat(userProfileDTO1).isNotEqualTo(userProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userProfileMapper.fromId(null)).isNull();
    }
}
