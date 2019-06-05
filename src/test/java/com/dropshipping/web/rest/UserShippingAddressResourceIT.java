package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.UserShippingAddress;
import com.dropshipping.repository.UserShippingAddressRepository;
import com.dropshipping.service.UserShippingAddressService;
import com.dropshipping.service.dto.UserShippingAddressDTO;
import com.dropshipping.service.mapper.UserShippingAddressMapper;
import com.dropshipping.web.rest.errors.ExceptionTranslator;

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
 * Integration tests for the {@Link UserShippingAddressResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class UserShippingAddressResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SHIPPING_ADDRESS = false;
    private static final Boolean UPDATED_IS_SHIPPING_ADDRESS = true;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserShippingAddressRepository userShippingAddressRepository;

    @Autowired
    private UserShippingAddressMapper userShippingAddressMapper;

    @Autowired
    private UserShippingAddressService userShippingAddressService;

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

    private MockMvc restUserShippingAddressMockMvc;

    private UserShippingAddress userShippingAddress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserShippingAddressResource userShippingAddressResource = new UserShippingAddressResource(userShippingAddressService);
        this.restUserShippingAddressMockMvc = MockMvcBuilders.standaloneSetup(userShippingAddressResource)
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
    public static UserShippingAddress createEntity(EntityManager em) {
        UserShippingAddress userShippingAddress = new UserShippingAddress()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .mobile(DEFAULT_MOBILE)
            .note(DEFAULT_NOTE)
            .isShippingAddress(DEFAULT_IS_SHIPPING_ADDRESS)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return userShippingAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserShippingAddress createUpdatedEntity(EntityManager em) {
        UserShippingAddress userShippingAddress = new UserShippingAddress()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .mobile(UPDATED_MOBILE)
            .note(UPDATED_NOTE)
            .isShippingAddress(UPDATED_IS_SHIPPING_ADDRESS)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return userShippingAddress;
    }

    @BeforeEach
    public void initTest() {
        userShippingAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = userShippingAddressRepository.findAll().size();

        // Create the UserShippingAddress
        UserShippingAddressDTO userShippingAddressDTO = userShippingAddressMapper.toDto(userShippingAddress);
        restUserShippingAddressMockMvc.perform(post("/api/user-shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShippingAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the UserShippingAddress in the database
        List<UserShippingAddress> userShippingAddressList = userShippingAddressRepository.findAll();
        assertThat(userShippingAddressList).hasSize(databaseSizeBeforeCreate + 1);
        UserShippingAddress testUserShippingAddress = userShippingAddressList.get(userShippingAddressList.size() - 1);
        assertThat(testUserShippingAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserShippingAddress.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserShippingAddress.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserShippingAddress.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testUserShippingAddress.isIsShippingAddress()).isEqualTo(DEFAULT_IS_SHIPPING_ADDRESS);
        assertThat(testUserShippingAddress.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testUserShippingAddress.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createUserShippingAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userShippingAddressRepository.findAll().size();

        // Create the UserShippingAddress with an existing ID
        userShippingAddress.setId(1L);
        UserShippingAddressDTO userShippingAddressDTO = userShippingAddressMapper.toDto(userShippingAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserShippingAddressMockMvc.perform(post("/api/user-shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShippingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserShippingAddress in the database
        List<UserShippingAddress> userShippingAddressList = userShippingAddressRepository.findAll();
        assertThat(userShippingAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserShippingAddresses() throws Exception {
        // Initialize the database
        userShippingAddressRepository.saveAndFlush(userShippingAddress);

        // Get all the userShippingAddressList
        restUserShippingAddressMockMvc.perform(get("/api/user-shipping-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userShippingAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].isShippingAddress").value(hasItem(DEFAULT_IS_SHIPPING_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getUserShippingAddress() throws Exception {
        // Initialize the database
        userShippingAddressRepository.saveAndFlush(userShippingAddress);

        // Get the userShippingAddress
        restUserShippingAddressMockMvc.perform(get("/api/user-shipping-addresses/{id}", userShippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userShippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.isShippingAddress").value(DEFAULT_IS_SHIPPING_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserShippingAddress() throws Exception {
        // Get the userShippingAddress
        restUserShippingAddressMockMvc.perform(get("/api/user-shipping-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserShippingAddress() throws Exception {
        // Initialize the database
        userShippingAddressRepository.saveAndFlush(userShippingAddress);

        int databaseSizeBeforeUpdate = userShippingAddressRepository.findAll().size();

        // Update the userShippingAddress
        UserShippingAddress updatedUserShippingAddress = userShippingAddressRepository.findById(userShippingAddress.getId()).get();
        // Disconnect from session so that the updates on updatedUserShippingAddress are not directly saved in db
        em.detach(updatedUserShippingAddress);
        updatedUserShippingAddress
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .mobile(UPDATED_MOBILE)
            .note(UPDATED_NOTE)
            .isShippingAddress(UPDATED_IS_SHIPPING_ADDRESS)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        UserShippingAddressDTO userShippingAddressDTO = userShippingAddressMapper.toDto(updatedUserShippingAddress);

        restUserShippingAddressMockMvc.perform(put("/api/user-shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShippingAddressDTO)))
            .andExpect(status().isOk());

        // Validate the UserShippingAddress in the database
        List<UserShippingAddress> userShippingAddressList = userShippingAddressRepository.findAll();
        assertThat(userShippingAddressList).hasSize(databaseSizeBeforeUpdate);
        UserShippingAddress testUserShippingAddress = userShippingAddressList.get(userShippingAddressList.size() - 1);
        assertThat(testUserShippingAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserShippingAddress.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserShippingAddress.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserShippingAddress.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testUserShippingAddress.isIsShippingAddress()).isEqualTo(UPDATED_IS_SHIPPING_ADDRESS);
        assertThat(testUserShippingAddress.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testUserShippingAddress.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserShippingAddress() throws Exception {
        int databaseSizeBeforeUpdate = userShippingAddressRepository.findAll().size();

        // Create the UserShippingAddress
        UserShippingAddressDTO userShippingAddressDTO = userShippingAddressMapper.toDto(userShippingAddress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserShippingAddressMockMvc.perform(put("/api/user-shipping-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userShippingAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserShippingAddress in the database
        List<UserShippingAddress> userShippingAddressList = userShippingAddressRepository.findAll();
        assertThat(userShippingAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserShippingAddress() throws Exception {
        // Initialize the database
        userShippingAddressRepository.saveAndFlush(userShippingAddress);

        int databaseSizeBeforeDelete = userShippingAddressRepository.findAll().size();

        // Delete the userShippingAddress
        restUserShippingAddressMockMvc.perform(delete("/api/user-shipping-addresses/{id}", userShippingAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<UserShippingAddress> userShippingAddressList = userShippingAddressRepository.findAll();
        assertThat(userShippingAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserShippingAddress.class);
        UserShippingAddress userShippingAddress1 = new UserShippingAddress();
        userShippingAddress1.setId(1L);
        UserShippingAddress userShippingAddress2 = new UserShippingAddress();
        userShippingAddress2.setId(userShippingAddress1.getId());
        assertThat(userShippingAddress1).isEqualTo(userShippingAddress2);
        userShippingAddress2.setId(2L);
        assertThat(userShippingAddress1).isNotEqualTo(userShippingAddress2);
        userShippingAddress1.setId(null);
        assertThat(userShippingAddress1).isNotEqualTo(userShippingAddress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserShippingAddressDTO.class);
        UserShippingAddressDTO userShippingAddressDTO1 = new UserShippingAddressDTO();
        userShippingAddressDTO1.setId(1L);
        UserShippingAddressDTO userShippingAddressDTO2 = new UserShippingAddressDTO();
        assertThat(userShippingAddressDTO1).isNotEqualTo(userShippingAddressDTO2);
        userShippingAddressDTO2.setId(userShippingAddressDTO1.getId());
        assertThat(userShippingAddressDTO1).isEqualTo(userShippingAddressDTO2);
        userShippingAddressDTO2.setId(2L);
        assertThat(userShippingAddressDTO1).isNotEqualTo(userShippingAddressDTO2);
        userShippingAddressDTO1.setId(null);
        assertThat(userShippingAddressDTO1).isNotEqualTo(userShippingAddressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userShippingAddressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userShippingAddressMapper.fromId(null)).isNull();
    }
}
