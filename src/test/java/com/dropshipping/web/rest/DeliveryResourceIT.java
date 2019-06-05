package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.Delivery;
import com.dropshipping.repository.DeliveryRepository;
import com.dropshipping.service.DeliveryService;
import com.dropshipping.service.dto.DeliveryDTO;
import com.dropshipping.service.mapper.DeliveryMapper;
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
 * Integration tests for the {@Link DeliveryResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class DeliveryResourceIT {

    private static final String DEFAULT_DELIVERY_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_METHOD_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPORT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPORT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PACKED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PACKED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_STYLE = "BBBBBBBBBB";

    private static final Float DEFAULT_TOTAL_WEIGHT = 1F;
    private static final Float UPDATED_TOTAL_WEIGHT = 2F;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private DeliveryService deliveryService;

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

    private MockMvc restDeliveryMockMvc;

    private Delivery delivery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryResource deliveryResource = new DeliveryResource(deliveryService);
        this.restDeliveryMockMvc = MockMvcBuilders.standaloneSetup(deliveryResource)
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
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .deliveryMethod(DEFAULT_DELIVERY_METHOD)
            .deliveryMethodName(DEFAULT_DELIVERY_METHOD_NAME)
            .exportTime(DEFAULT_EXPORT_TIME)
            .packedTime(DEFAULT_PACKED_TIME)
            .status(DEFAULT_STATUS)
            .statusName(DEFAULT_STATUS_NAME)
            .statusStyle(DEFAULT_STATUS_STYLE)
            .totalWeight(DEFAULT_TOTAL_WEIGHT)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return delivery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createUpdatedEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .deliveryMethodName(UPDATED_DELIVERY_METHOD_NAME)
            .exportTime(UPDATED_EXPORT_TIME)
            .packedTime(UPDATED_PACKED_TIME)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return delivery;
    }

    @BeforeEach
    public void initTest() {
        delivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getDeliveryMethod()).isEqualTo(DEFAULT_DELIVERY_METHOD);
        assertThat(testDelivery.getDeliveryMethodName()).isEqualTo(DEFAULT_DELIVERY_METHOD_NAME);
        assertThat(testDelivery.getExportTime()).isEqualTo(DEFAULT_EXPORT_TIME);
        assertThat(testDelivery.getPackedTime()).isEqualTo(DEFAULT_PACKED_TIME);
        assertThat(testDelivery.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDelivery.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testDelivery.getStatusStyle()).isEqualTo(DEFAULT_STATUS_STYLE);
        assertThat(testDelivery.getTotalWeight()).isEqualTo(DEFAULT_TOTAL_WEIGHT);
        assertThat(testDelivery.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testDelivery.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery with an existing ID
        delivery.setId(1L);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryMethod").value(hasItem(DEFAULT_DELIVERY_METHOD.toString())))
            .andExpect(jsonPath("$.[*].deliveryMethodName").value(hasItem(DEFAULT_DELIVERY_METHOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].exportTime").value(hasItem(DEFAULT_EXPORT_TIME.toString())))
            .andExpect(jsonPath("$.[*].packedTime").value(hasItem(DEFAULT_PACKED_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].statusStyle").value(hasItem(DEFAULT_STATUS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].totalWeight").value(hasItem(DEFAULT_TOTAL_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", delivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delivery.getId().intValue()))
            .andExpect(jsonPath("$.deliveryMethod").value(DEFAULT_DELIVERY_METHOD.toString()))
            .andExpect(jsonPath("$.deliveryMethodName").value(DEFAULT_DELIVERY_METHOD_NAME.toString()))
            .andExpect(jsonPath("$.exportTime").value(DEFAULT_EXPORT_TIME.toString()))
            .andExpect(jsonPath("$.packedTime").value(DEFAULT_PACKED_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.statusStyle").value(DEFAULT_STATUS_STYLE.toString()))
            .andExpect(jsonPath("$.totalWeight").value(DEFAULT_TOTAL_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelivery() throws Exception {
        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findById(delivery.getId()).get();
        // Disconnect from session so that the updates on updatedDelivery are not directly saved in db
        em.detach(updatedDelivery);
        updatedDelivery
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .deliveryMethodName(UPDATED_DELIVERY_METHOD_NAME)
            .exportTime(UPDATED_EXPORT_TIME)
            .packedTime(UPDATED_PACKED_TIME)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .totalWeight(UPDATED_TOTAL_WEIGHT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(updatedDelivery);

        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getDeliveryMethod()).isEqualTo(UPDATED_DELIVERY_METHOD);
        assertThat(testDelivery.getDeliveryMethodName()).isEqualTo(UPDATED_DELIVERY_METHOD_NAME);
        assertThat(testDelivery.getExportTime()).isEqualTo(UPDATED_EXPORT_TIME);
        assertThat(testDelivery.getPackedTime()).isEqualTo(UPDATED_PACKED_TIME);
        assertThat(testDelivery.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDelivery.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testDelivery.getStatusStyle()).isEqualTo(UPDATED_STATUS_STYLE);
        assertThat(testDelivery.getTotalWeight()).isEqualTo(UPDATED_TOTAL_WEIGHT);
        assertThat(testDelivery.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testDelivery.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeDelete = deliveryRepository.findAll().size();

        // Delete the delivery
        restDeliveryMockMvc.perform(delete("/api/deliveries/{id}", delivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delivery.class);
        Delivery delivery1 = new Delivery();
        delivery1.setId(1L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(delivery1.getId());
        assertThat(delivery1).isEqualTo(delivery2);
        delivery2.setId(2L);
        assertThat(delivery1).isNotEqualTo(delivery2);
        delivery1.setId(null);
        assertThat(delivery1).isNotEqualTo(delivery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryDTO.class);
        DeliveryDTO deliveryDTO1 = new DeliveryDTO();
        deliveryDTO1.setId(1L);
        DeliveryDTO deliveryDTO2 = new DeliveryDTO();
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO2.setId(deliveryDTO1.getId());
        assertThat(deliveryDTO1).isEqualTo(deliveryDTO2);
        deliveryDTO2.setId(2L);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO1.setId(null);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryMapper.fromId(null)).isNull();
    }
}
