package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderPackageHistory;
import com.dropshipping.repository.OrderPackageHistoryRepository;
import com.dropshipping.service.OrderPackageHistoryService;
import com.dropshipping.service.dto.OrderPackageHistoryDTO;
import com.dropshipping.service.mapper.OrderPackageHistoryMapper;
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
 * Integration tests for the {@Link OrderPackageHistoryResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderPackageHistoryResourceIT {

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
    private OrderPackageHistoryRepository orderPackageHistoryRepository;

    @Autowired
    private OrderPackageHistoryMapper orderPackageHistoryMapper;

    @Autowired
    private OrderPackageHistoryService orderPackageHistoryService;

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

    private MockMvc restOrderPackageHistoryMockMvc;

    private OrderPackageHistory orderPackageHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderPackageHistoryResource orderPackageHistoryResource = new OrderPackageHistoryResource(orderPackageHistoryService);
        this.restOrderPackageHistoryMockMvc = MockMvcBuilders.standaloneSetup(orderPackageHistoryResource)
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
    public static OrderPackageHistory createEntity(EntityManager em) {
        OrderPackageHistory orderPackageHistory = new OrderPackageHistory()
            .status(DEFAULT_STATUS)
            .statusName(DEFAULT_STATUS_NAME)
            .statusStyle(DEFAULT_STATUS_STYLE)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return orderPackageHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderPackageHistory createUpdatedEntity(EntityManager em) {
        OrderPackageHistory orderPackageHistory = new OrderPackageHistory()
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return orderPackageHistory;
    }

    @BeforeEach
    public void initTest() {
        orderPackageHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPackageHistory() throws Exception {
        int databaseSizeBeforeCreate = orderPackageHistoryRepository.findAll().size();

        // Create the OrderPackageHistory
        OrderPackageHistoryDTO orderPackageHistoryDTO = orderPackageHistoryMapper.toDto(orderPackageHistory);
        restOrderPackageHistoryMockMvc.perform(post("/api/order-package-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderPackageHistory in the database
        List<OrderPackageHistory> orderPackageHistoryList = orderPackageHistoryRepository.findAll();
        assertThat(orderPackageHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPackageHistory testOrderPackageHistory = orderPackageHistoryList.get(orderPackageHistoryList.size() - 1);
        assertThat(testOrderPackageHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderPackageHistory.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testOrderPackageHistory.getStatusStyle()).isEqualTo(DEFAULT_STATUS_STYLE);
        assertThat(testOrderPackageHistory.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testOrderPackageHistory.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createOrderPackageHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPackageHistoryRepository.findAll().size();

        // Create the OrderPackageHistory with an existing ID
        orderPackageHistory.setId(1L);
        OrderPackageHistoryDTO orderPackageHistoryDTO = orderPackageHistoryMapper.toDto(orderPackageHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPackageHistoryMockMvc.perform(post("/api/order-package-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackageHistory in the database
        List<OrderPackageHistory> orderPackageHistoryList = orderPackageHistoryRepository.findAll();
        assertThat(orderPackageHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderPackageHistories() throws Exception {
        // Initialize the database
        orderPackageHistoryRepository.saveAndFlush(orderPackageHistory);

        // Get all the orderPackageHistoryList
        restOrderPackageHistoryMockMvc.perform(get("/api/order-package-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPackageHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].statusStyle").value(hasItem(DEFAULT_STATUS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderPackageHistory() throws Exception {
        // Initialize the database
        orderPackageHistoryRepository.saveAndFlush(orderPackageHistory);

        // Get the orderPackageHistory
        restOrderPackageHistoryMockMvc.perform(get("/api/order-package-histories/{id}", orderPackageHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderPackageHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.statusStyle").value(DEFAULT_STATUS_STYLE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPackageHistory() throws Exception {
        // Get the orderPackageHistory
        restOrderPackageHistoryMockMvc.perform(get("/api/order-package-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPackageHistory() throws Exception {
        // Initialize the database
        orderPackageHistoryRepository.saveAndFlush(orderPackageHistory);

        int databaseSizeBeforeUpdate = orderPackageHistoryRepository.findAll().size();

        // Update the orderPackageHistory
        OrderPackageHistory updatedOrderPackageHistory = orderPackageHistoryRepository.findById(orderPackageHistory.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPackageHistory are not directly saved in db
        em.detach(updatedOrderPackageHistory);
        updatedOrderPackageHistory
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        OrderPackageHistoryDTO orderPackageHistoryDTO = orderPackageHistoryMapper.toDto(updatedOrderPackageHistory);

        restOrderPackageHistoryMockMvc.perform(put("/api/order-package-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the OrderPackageHistory in the database
        List<OrderPackageHistory> orderPackageHistoryList = orderPackageHistoryRepository.findAll();
        assertThat(orderPackageHistoryList).hasSize(databaseSizeBeforeUpdate);
        OrderPackageHistory testOrderPackageHistory = orderPackageHistoryList.get(orderPackageHistoryList.size() - 1);
        assertThat(testOrderPackageHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderPackageHistory.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testOrderPackageHistory.getStatusStyle()).isEqualTo(UPDATED_STATUS_STYLE);
        assertThat(testOrderPackageHistory.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testOrderPackageHistory.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPackageHistory() throws Exception {
        int databaseSizeBeforeUpdate = orderPackageHistoryRepository.findAll().size();

        // Create the OrderPackageHistory
        OrderPackageHistoryDTO orderPackageHistoryDTO = orderPackageHistoryMapper.toDto(orderPackageHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPackageHistoryMockMvc.perform(put("/api/order-package-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderPackageHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackageHistory in the database
        List<OrderPackageHistory> orderPackageHistoryList = orderPackageHistoryRepository.findAll();
        assertThat(orderPackageHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderPackageHistory() throws Exception {
        // Initialize the database
        orderPackageHistoryRepository.saveAndFlush(orderPackageHistory);

        int databaseSizeBeforeDelete = orderPackageHistoryRepository.findAll().size();

        // Delete the orderPackageHistory
        restOrderPackageHistoryMockMvc.perform(delete("/api/order-package-histories/{id}", orderPackageHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderPackageHistory> orderPackageHistoryList = orderPackageHistoryRepository.findAll();
        assertThat(orderPackageHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackageHistory.class);
        OrderPackageHistory orderPackageHistory1 = new OrderPackageHistory();
        orderPackageHistory1.setId(1L);
        OrderPackageHistory orderPackageHistory2 = new OrderPackageHistory();
        orderPackageHistory2.setId(orderPackageHistory1.getId());
        assertThat(orderPackageHistory1).isEqualTo(orderPackageHistory2);
        orderPackageHistory2.setId(2L);
        assertThat(orderPackageHistory1).isNotEqualTo(orderPackageHistory2);
        orderPackageHistory1.setId(null);
        assertThat(orderPackageHistory1).isNotEqualTo(orderPackageHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPackageHistoryDTO.class);
        OrderPackageHistoryDTO orderPackageHistoryDTO1 = new OrderPackageHistoryDTO();
        orderPackageHistoryDTO1.setId(1L);
        OrderPackageHistoryDTO orderPackageHistoryDTO2 = new OrderPackageHistoryDTO();
        assertThat(orderPackageHistoryDTO1).isNotEqualTo(orderPackageHistoryDTO2);
        orderPackageHistoryDTO2.setId(orderPackageHistoryDTO1.getId());
        assertThat(orderPackageHistoryDTO1).isEqualTo(orderPackageHistoryDTO2);
        orderPackageHistoryDTO2.setId(2L);
        assertThat(orderPackageHistoryDTO1).isNotEqualTo(orderPackageHistoryDTO2);
        orderPackageHistoryDTO1.setId(null);
        assertThat(orderPackageHistoryDTO1).isNotEqualTo(orderPackageHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderPackageHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderPackageHistoryMapper.fromId(null)).isNull();
    }
}
