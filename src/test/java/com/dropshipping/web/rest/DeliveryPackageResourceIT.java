package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.DeliveryPackage;
import com.dropshipping.repository.DeliveryPackageRepository;
import com.dropshipping.service.DeliveryPackageService;
import com.dropshipping.service.dto.DeliveryPackageDTO;
import com.dropshipping.service.mapper.DeliveryPackageMapper;
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
 * Integration tests for the {@Link DeliveryPackageResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class DeliveryPackageResourceIT {

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Autowired
    private DeliveryPackageMapper deliveryPackageMapper;

    @Autowired
    private DeliveryPackageService deliveryPackageService;

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

    private MockMvc restDeliveryPackageMockMvc;

    private DeliveryPackage deliveryPackage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryPackageResource deliveryPackageResource = new DeliveryPackageResource(deliveryPackageService);
        this.restDeliveryPackageMockMvc = MockMvcBuilders.standaloneSetup(deliveryPackageResource)
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
    public static DeliveryPackage createEntity(EntityManager em) {
        DeliveryPackage deliveryPackage = new DeliveryPackage()
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return deliveryPackage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryPackage createUpdatedEntity(EntityManager em) {
        DeliveryPackage deliveryPackage = new DeliveryPackage()
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return deliveryPackage;
    }

    @BeforeEach
    public void initTest() {
        deliveryPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryPackage() throws Exception {
        int databaseSizeBeforeCreate = deliveryPackageRepository.findAll().size();

        // Create the DeliveryPackage
        DeliveryPackageDTO deliveryPackageDTO = deliveryPackageMapper.toDto(deliveryPackage);
        restDeliveryPackageMockMvc.perform(post("/api/delivery-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPackageDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryPackage in the database
        List<DeliveryPackage> deliveryPackageList = deliveryPackageRepository.findAll();
        assertThat(deliveryPackageList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryPackage testDeliveryPackage = deliveryPackageList.get(deliveryPackageList.size() - 1);
        assertThat(testDeliveryPackage.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testDeliveryPackage.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createDeliveryPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryPackageRepository.findAll().size();

        // Create the DeliveryPackage with an existing ID
        deliveryPackage.setId(1L);
        DeliveryPackageDTO deliveryPackageDTO = deliveryPackageMapper.toDto(deliveryPackage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryPackageMockMvc.perform(post("/api/delivery-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryPackage in the database
        List<DeliveryPackage> deliveryPackageList = deliveryPackageRepository.findAll();
        assertThat(deliveryPackageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeliveryPackages() throws Exception {
        // Initialize the database
        deliveryPackageRepository.saveAndFlush(deliveryPackage);

        // Get all the deliveryPackageList
        restDeliveryPackageMockMvc.perform(get("/api/delivery-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryPackage() throws Exception {
        // Initialize the database
        deliveryPackageRepository.saveAndFlush(deliveryPackage);

        // Get the deliveryPackage
        restDeliveryPackageMockMvc.perform(get("/api/delivery-packages/{id}", deliveryPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryPackage.getId().intValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryPackage() throws Exception {
        // Get the deliveryPackage
        restDeliveryPackageMockMvc.perform(get("/api/delivery-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryPackage() throws Exception {
        // Initialize the database
        deliveryPackageRepository.saveAndFlush(deliveryPackage);

        int databaseSizeBeforeUpdate = deliveryPackageRepository.findAll().size();

        // Update the deliveryPackage
        DeliveryPackage updatedDeliveryPackage = deliveryPackageRepository.findById(deliveryPackage.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryPackage are not directly saved in db
        em.detach(updatedDeliveryPackage);
        updatedDeliveryPackage
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        DeliveryPackageDTO deliveryPackageDTO = deliveryPackageMapper.toDto(updatedDeliveryPackage);

        restDeliveryPackageMockMvc.perform(put("/api/delivery-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPackageDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryPackage in the database
        List<DeliveryPackage> deliveryPackageList = deliveryPackageRepository.findAll();
        assertThat(deliveryPackageList).hasSize(databaseSizeBeforeUpdate);
        DeliveryPackage testDeliveryPackage = deliveryPackageList.get(deliveryPackageList.size() - 1);
        assertThat(testDeliveryPackage.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testDeliveryPackage.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryPackage() throws Exception {
        int databaseSizeBeforeUpdate = deliveryPackageRepository.findAll().size();

        // Create the DeliveryPackage
        DeliveryPackageDTO deliveryPackageDTO = deliveryPackageMapper.toDto(deliveryPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryPackageMockMvc.perform(put("/api/delivery-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryPackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryPackage in the database
        List<DeliveryPackage> deliveryPackageList = deliveryPackageRepository.findAll();
        assertThat(deliveryPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryPackage() throws Exception {
        // Initialize the database
        deliveryPackageRepository.saveAndFlush(deliveryPackage);

        int databaseSizeBeforeDelete = deliveryPackageRepository.findAll().size();

        // Delete the deliveryPackage
        restDeliveryPackageMockMvc.perform(delete("/api/delivery-packages/{id}", deliveryPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DeliveryPackage> deliveryPackageList = deliveryPackageRepository.findAll();
        assertThat(deliveryPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryPackage.class);
        DeliveryPackage deliveryPackage1 = new DeliveryPackage();
        deliveryPackage1.setId(1L);
        DeliveryPackage deliveryPackage2 = new DeliveryPackage();
        deliveryPackage2.setId(deliveryPackage1.getId());
        assertThat(deliveryPackage1).isEqualTo(deliveryPackage2);
        deliveryPackage2.setId(2L);
        assertThat(deliveryPackage1).isNotEqualTo(deliveryPackage2);
        deliveryPackage1.setId(null);
        assertThat(deliveryPackage1).isNotEqualTo(deliveryPackage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryPackageDTO.class);
        DeliveryPackageDTO deliveryPackageDTO1 = new DeliveryPackageDTO();
        deliveryPackageDTO1.setId(1L);
        DeliveryPackageDTO deliveryPackageDTO2 = new DeliveryPackageDTO();
        assertThat(deliveryPackageDTO1).isNotEqualTo(deliveryPackageDTO2);
        deliveryPackageDTO2.setId(deliveryPackageDTO1.getId());
        assertThat(deliveryPackageDTO1).isEqualTo(deliveryPackageDTO2);
        deliveryPackageDTO2.setId(2L);
        assertThat(deliveryPackageDTO1).isNotEqualTo(deliveryPackageDTO2);
        deliveryPackageDTO1.setId(null);
        assertThat(deliveryPackageDTO1).isNotEqualTo(deliveryPackageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryPackageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryPackageMapper.fromId(null)).isNull();
    }
}
