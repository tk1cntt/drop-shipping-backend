package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderHistory;
import com.dropshipping.repository.OrderHistoryRepository;
import com.dropshipping.service.OrderHistoryService;
import com.dropshipping.service.dto.OrderHistoryDTO;
import com.dropshipping.service.mapper.OrderHistoryMapper;
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

import com.dropshipping.domain.enumeration.OrderStatus;
/**
 * Integration tests for the {@Link OrderHistoryResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderHistoryResourceIT {

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.DEPOSITED;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.ARE_BUYING;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Autowired
    private OrderHistoryService orderHistoryService;

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

    private MockMvc restOrderHistoryMockMvc;

    private OrderHistory orderHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderHistoryResource orderHistoryResource = new OrderHistoryResource(orderHistoryService);
        this.restOrderHistoryMockMvc = MockMvcBuilders.standaloneSetup(orderHistoryResource)
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
    public static OrderHistory createEntity(EntityManager em) {
        OrderHistory orderHistory = new OrderHistory()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .createAt(DEFAULT_CREATE_AT);
        return orderHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderHistory createUpdatedEntity(EntityManager em) {
        OrderHistory orderHistory = new OrderHistory()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .createAt(UPDATED_CREATE_AT);
        return orderHistory;
    }

    @BeforeEach
    public void initTest() {
        orderHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderHistory() throws Exception {
        int databaseSizeBeforeCreate = orderHistoryRepository.findAll().size();

        // Create the OrderHistory
        OrderHistoryDTO orderHistoryDTO = orderHistoryMapper.toDto(orderHistory);
        restOrderHistoryMockMvc.perform(post("/api/order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderHistory in the database
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        OrderHistory testOrderHistory = orderHistoryList.get(orderHistoryList.size() - 1);
        assertThat(testOrderHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderHistory.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    public void createOrderHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderHistoryRepository.findAll().size();

        // Create the OrderHistory with an existing ID
        orderHistory.setId(1L);
        OrderHistoryDTO orderHistoryDTO = orderHistoryMapper.toDto(orderHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderHistoryMockMvc.perform(post("/api/order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderHistory in the database
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderHistories() throws Exception {
        // Initialize the database
        orderHistoryRepository.saveAndFlush(orderHistory);

        // Get all the orderHistoryList
        restOrderHistoryMockMvc.perform(get("/api/order-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderHistory() throws Exception {
        // Initialize the database
        orderHistoryRepository.saveAndFlush(orderHistory);

        // Get the orderHistory
        restOrderHistoryMockMvc.perform(get("/api/order-histories/{id}", orderHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderHistory() throws Exception {
        // Get the orderHistory
        restOrderHistoryMockMvc.perform(get("/api/order-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderHistory() throws Exception {
        // Initialize the database
        orderHistoryRepository.saveAndFlush(orderHistory);

        int databaseSizeBeforeUpdate = orderHistoryRepository.findAll().size();

        // Update the orderHistory
        OrderHistory updatedOrderHistory = orderHistoryRepository.findById(orderHistory.getId()).get();
        // Disconnect from session so that the updates on updatedOrderHistory are not directly saved in db
        em.detach(updatedOrderHistory);
        updatedOrderHistory
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .createAt(UPDATED_CREATE_AT);
        OrderHistoryDTO orderHistoryDTO = orderHistoryMapper.toDto(updatedOrderHistory);

        restOrderHistoryMockMvc.perform(put("/api/order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the OrderHistory in the database
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList).hasSize(databaseSizeBeforeUpdate);
        OrderHistory testOrderHistory = orderHistoryList.get(orderHistoryList.size() - 1);
        assertThat(testOrderHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderHistory.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderHistory() throws Exception {
        int databaseSizeBeforeUpdate = orderHistoryRepository.findAll().size();

        // Create the OrderHistory
        OrderHistoryDTO orderHistoryDTO = orderHistoryMapper.toDto(orderHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderHistoryMockMvc.perform(put("/api/order-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderHistory in the database
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderHistory() throws Exception {
        // Initialize the database
        orderHistoryRepository.saveAndFlush(orderHistory);

        int databaseSizeBeforeDelete = orderHistoryRepository.findAll().size();

        // Delete the orderHistory
        restOrderHistoryMockMvc.perform(delete("/api/order-histories/{id}", orderHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderHistory.class);
        OrderHistory orderHistory1 = new OrderHistory();
        orderHistory1.setId(1L);
        OrderHistory orderHistory2 = new OrderHistory();
        orderHistory2.setId(orderHistory1.getId());
        assertThat(orderHistory1).isEqualTo(orderHistory2);
        orderHistory2.setId(2L);
        assertThat(orderHistory1).isNotEqualTo(orderHistory2);
        orderHistory1.setId(null);
        assertThat(orderHistory1).isNotEqualTo(orderHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderHistoryDTO.class);
        OrderHistoryDTO orderHistoryDTO1 = new OrderHistoryDTO();
        orderHistoryDTO1.setId(1L);
        OrderHistoryDTO orderHistoryDTO2 = new OrderHistoryDTO();
        assertThat(orderHistoryDTO1).isNotEqualTo(orderHistoryDTO2);
        orderHistoryDTO2.setId(orderHistoryDTO1.getId());
        assertThat(orderHistoryDTO1).isEqualTo(orderHistoryDTO2);
        orderHistoryDTO2.setId(2L);
        assertThat(orderHistoryDTO1).isNotEqualTo(orderHistoryDTO2);
        orderHistoryDTO1.setId(null);
        assertThat(orderHistoryDTO1).isNotEqualTo(orderHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderHistoryMapper.fromId(null)).isNull();
    }
}
