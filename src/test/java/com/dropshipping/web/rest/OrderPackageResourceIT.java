package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderPackage;
import com.dropshipping.repository.OrderPackageRepository;
import com.dropshipping.service.OrderPackageService;
import com.dropshipping.service.dto.OrderPackageDTO;
import com.dropshipping.service.mapper.OrderPackageMapper;
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
 * Integration tests for the {@Link OrderPackageResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderPackageResourceIT {

    private static final String DEFAULT_LADING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LADING_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_HEIGHT_PACKAGE = 1F;
    private static final Float UPDATED_HEIGHT_PACKAGE = 2F;

    private static final Float DEFAULT_WIDTH_PACKAGE = 1F;
    private static final Float UPDATED_WIDTH_PACKAGE = 2F;

    private static final Float DEFAULT_LENGTH_PACKAGE = 1F;
    private static final Float UPDATED_LENGTH_PACKAGE = 2F;

    private static final Float DEFAULT_NET_WEIGHT = 1F;
    private static final Float UPDATED_NET_WEIGHT = 2F;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_STYLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderPackageRepository orderPackageRepository;

    @Autowired
    private OrderPackageMapper orderPackageMapper;

    @Autowired
    private OrderPackageService orderPackageService;

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

    private MockMvc restOrderPackageMockMvc;

    private OrderPackage orderPackage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderPackageResource orderPackageResource = new OrderPackageResource(orderPackageService);
        this.restOrderPackageMockMvc = MockMvcBuilders.standaloneSetup(orderPackageResource)
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
    public static OrderPackage createEntity(EntityManager em) {
        OrderPackage orderPackage = new OrderPackage()
            .ladingCode(DEFAULT_LADING_CODE)
            .heightPackage(DEFAULT_HEIGHT_PACKAGE)
            .widthPackage(DEFAULT_WIDTH_PACKAGE)
            .lengthPackage(DEFAULT_LENGTH_PACKAGE)
            .netWeight(DEFAULT_NET_WEIGHT)
            .status(DEFAULT_STATUS)
            .statusName(DEFAULT_STATUS_NAME)
            .statusStyle(DEFAULT_STATUS_STYLE)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return orderPackage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderPackage createUpdatedEntity(EntityManager em) {
        OrderPackage orderPackage = new OrderPackage()
            .ladingCode(UPDATED_LADING_CODE)
            .heightPackage(UPDATED_HEIGHT_PACKAGE)
            .widthPackage(UPDATED_WIDTH_PACKAGE)
            .lengthPackage(UPDATED_LENGTH_PACKAGE)
            .netWeight(UPDATED_NET_WEIGHT)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return orderPackage;
    }

    @BeforeEach
    public void initTest() {
        orderPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPackage() throws Exception {
        int databaseSizeBeforeCreate = orderPackageRepository.findAll().size();

        // Create the OrderPackage
        OrderPackageDTO orderPackageDTO = orderPackageMapper.toDto(orderPackage);
        restOrderPackageMockMvc.perform(post("/api/order-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderPackage in the database
        List<OrderPackage> orderPackageList = orderPackageRepository.findAll();
        assertThat(orderPackageList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPackage testOrderPackage = orderPackageList.get(orderPackageList.size() - 1);
        assertThat(testOrderPackage.getLadingCode()).isEqualTo(DEFAULT_LADING_CODE);
        assertThat(testOrderPackage.getHeightPackage()).isEqualTo(DEFAULT_HEIGHT_PACKAGE);
        assertThat(testOrderPackage.getWidthPackage()).isEqualTo(DEFAULT_WIDTH_PACKAGE);
        assertThat(testOrderPackage.getLengthPackage()).isEqualTo(DEFAULT_LENGTH_PACKAGE);
        assertThat(testOrderPackage.getNetWeight()).isEqualTo(DEFAULT_NET_WEIGHT);
        assertThat(testOrderPackage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderPackage.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testOrderPackage.getStatusStyle()).isEqualTo(DEFAULT_STATUS_STYLE);
        assertThat(testOrderPackage.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testOrderPackage.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createOrderPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPackageRepository.findAll().size();

        // Create the OrderPackage with an existing ID
        orderPackage.setId(1L);
        OrderPackageDTO orderPackageDTO = orderPackageMapper.toDto(orderPackage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPackageMockMvc.perform(post("/api/order-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackage in the database
        List<OrderPackage> orderPackageList = orderPackageRepository.findAll();
        assertThat(orderPackageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderPackages() throws Exception {
        // Initialize the database
        orderPackageRepository.saveAndFlush(orderPackage);

        // Get all the orderPackageList
        restOrderPackageMockMvc.perform(get("/api/order-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].ladingCode").value(hasItem(DEFAULT_LADING_CODE.toString())))
            .andExpect(jsonPath("$.[*].heightPackage").value(hasItem(DEFAULT_HEIGHT_PACKAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].widthPackage").value(hasItem(DEFAULT_WIDTH_PACKAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].lengthPackage").value(hasItem(DEFAULT_LENGTH_PACKAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].statusStyle").value(hasItem(DEFAULT_STATUS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderPackage() throws Exception {
        // Initialize the database
        orderPackageRepository.saveAndFlush(orderPackage);

        // Get the orderPackage
        restOrderPackageMockMvc.perform(get("/api/order-packages/{id}", orderPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderPackage.getId().intValue()))
            .andExpect(jsonPath("$.ladingCode").value(DEFAULT_LADING_CODE.toString()))
            .andExpect(jsonPath("$.heightPackage").value(DEFAULT_HEIGHT_PACKAGE.doubleValue()))
            .andExpect(jsonPath("$.widthPackage").value(DEFAULT_WIDTH_PACKAGE.doubleValue()))
            .andExpect(jsonPath("$.lengthPackage").value(DEFAULT_LENGTH_PACKAGE.doubleValue()))
            .andExpect(jsonPath("$.netWeight").value(DEFAULT_NET_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.statusStyle").value(DEFAULT_STATUS_STYLE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPackage() throws Exception {
        // Get the orderPackage
        restOrderPackageMockMvc.perform(get("/api/order-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPackage() throws Exception {
        // Initialize the database
        orderPackageRepository.saveAndFlush(orderPackage);

        int databaseSizeBeforeUpdate = orderPackageRepository.findAll().size();

        // Update the orderPackage
        OrderPackage updatedOrderPackage = orderPackageRepository.findById(orderPackage.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPackage are not directly saved in db
        em.detach(updatedOrderPackage);
        updatedOrderPackage
            .ladingCode(UPDATED_LADING_CODE)
            .heightPackage(UPDATED_HEIGHT_PACKAGE)
            .widthPackage(UPDATED_WIDTH_PACKAGE)
            .lengthPackage(UPDATED_LENGTH_PACKAGE)
            .netWeight(UPDATED_NET_WEIGHT)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        OrderPackageDTO orderPackageDTO = orderPackageMapper.toDto(updatedOrderPackage);

        restOrderPackageMockMvc.perform(put("/api/order-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageDTO)))
            .andExpect(status().isOk());

        // Validate the OrderPackage in the database
        List<OrderPackage> orderPackageList = orderPackageRepository.findAll();
        assertThat(orderPackageList).hasSize(databaseSizeBeforeUpdate);
        OrderPackage testOrderPackage = orderPackageList.get(orderPackageList.size() - 1);
        assertThat(testOrderPackage.getLadingCode()).isEqualTo(UPDATED_LADING_CODE);
        assertThat(testOrderPackage.getHeightPackage()).isEqualTo(UPDATED_HEIGHT_PACKAGE);
        assertThat(testOrderPackage.getWidthPackage()).isEqualTo(UPDATED_WIDTH_PACKAGE);
        assertThat(testOrderPackage.getLengthPackage()).isEqualTo(UPDATED_LENGTH_PACKAGE);
        assertThat(testOrderPackage.getNetWeight()).isEqualTo(UPDATED_NET_WEIGHT);
        assertThat(testOrderPackage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderPackage.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testOrderPackage.getStatusStyle()).isEqualTo(UPDATED_STATUS_STYLE);
        assertThat(testOrderPackage.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testOrderPackage.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPackage() throws Exception {
        int databaseSizeBeforeUpdate = orderPackageRepository.findAll().size();

        // Create the OrderPackage
        OrderPackageDTO orderPackageDTO = orderPackageMapper.toDto(orderPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPackageMockMvc.perform(put("/api/order-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackage in the database
        List<OrderPackage> orderPackageList = orderPackageRepository.findAll();
        assertThat(orderPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderPackage() throws Exception {
        // Initialize the database
        orderPackageRepository.saveAndFlush(orderPackage);

        int databaseSizeBeforeDelete = orderPackageRepository.findAll().size();

        // Delete the orderPackage
        restOrderPackageMockMvc.perform(delete("/api/order-packages/{id}", orderPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderPackage> orderPackageList = orderPackageRepository.findAll();
        assertThat(orderPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackage.class);
        OrderPackage orderPackage1 = new OrderPackage();
        orderPackage1.setId(1L);
        OrderPackage orderPackage2 = new OrderPackage();
        orderPackage2.setId(orderPackage1.getId());
        assertThat(orderPackage1).isEqualTo(orderPackage2);
        orderPackage2.setId(2L);
        assertThat(orderPackage1).isNotEqualTo(orderPackage2);
        orderPackage1.setId(null);
        assertThat(orderPackage1).isNotEqualTo(orderPackage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackageDTO.class);
        OrderPackageDTO orderPackageDTO1 = new OrderPackageDTO();
        orderPackageDTO1.setId(1L);
        OrderPackageDTO orderPackageDTO2 = new OrderPackageDTO();
        assertThat(orderPackageDTO1).isNotEqualTo(orderPackageDTO2);
        orderPackageDTO2.setId(orderPackageDTO1.getId());
        assertThat(orderPackageDTO1).isEqualTo(orderPackageDTO2);
        orderPackageDTO2.setId(2L);
        assertThat(orderPackageDTO1).isNotEqualTo(orderPackageDTO2);
        orderPackageDTO1.setId(null);
        assertThat(orderPackageDTO1).isNotEqualTo(orderPackageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderPackageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderPackageMapper.fromId(null)).isNull();
    }
}
