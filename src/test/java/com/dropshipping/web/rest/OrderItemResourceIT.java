package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderItem;
import com.dropshipping.repository.OrderItemRepository;
import com.dropshipping.service.OrderItemService;
import com.dropshipping.service.dto.OrderItemDTO;
import com.dropshipping.service.mapper.OrderItemMapper;
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
 * Integration tests for the {@Link OrderItemResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderItemResourceIT {

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Float DEFAULT_PRICE_NDT = 1F;
    private static final Float UPDATED_PRICE_NDT = 2F;

    private static final String DEFAULT_PROPERTIES_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES_MD_5 = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES_MD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY_ORDER = 1;
    private static final Integer UPDATED_QUANTITY_ORDER = 2;

    private static final Integer DEFAULT_QUANTITY_PENDING = 1;
    private static final Integer UPDATED_QUANTITY_PENDING = 2;

    private static final Integer DEFAULT_QUANTITY_RECEIVED = 1;
    private static final Integer UPDATED_QUANTITY_RECEIVED = 2;

    private static final Float DEFAULT_TOTAL_PRICE = 1F;
    private static final Float UPDATED_TOTAL_PRICE = 2F;

    private static final Float DEFAULT_TOTAL_PRICE_NDT = 1F;
    private static final Float UPDATED_TOTAL_PRICE_NDT = 2F;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderItemService orderItemService;

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

    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderItemResource orderItemResource = new OrderItemResource(orderItemService);
        this.restOrderItemMockMvc = MockMvcBuilders.standaloneSetup(orderItemResource)
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
    public static OrderItem createEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .avatar(DEFAULT_AVATAR)
            .originLink(DEFAULT_ORIGIN_LINK)
            .name(DEFAULT_NAME)
            .note(DEFAULT_NOTE)
            .price(DEFAULT_PRICE)
            .priceNDT(DEFAULT_PRICE_NDT)
            .propertiesId(DEFAULT_PROPERTIES_ID)
            .propertiesImage(DEFAULT_PROPERTIES_IMAGE)
            .propertiesMD5(DEFAULT_PROPERTIES_MD_5)
            .propertiesName(DEFAULT_PROPERTIES_NAME)
            .propertiesType(DEFAULT_PROPERTIES_TYPE)
            .quantityOrder(DEFAULT_QUANTITY_ORDER)
            .quantityPending(DEFAULT_QUANTITY_PENDING)
            .quantityReceived(DEFAULT_QUANTITY_RECEIVED)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .totalPriceNDT(DEFAULT_TOTAL_PRICE_NDT)
            .website(DEFAULT_WEBSITE)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return orderItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createUpdatedEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .avatar(UPDATED_AVATAR)
            .originLink(UPDATED_ORIGIN_LINK)
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .price(UPDATED_PRICE)
            .priceNDT(UPDATED_PRICE_NDT)
            .propertiesId(UPDATED_PROPERTIES_ID)
            .propertiesImage(UPDATED_PROPERTIES_IMAGE)
            .propertiesMD5(UPDATED_PROPERTIES_MD_5)
            .propertiesName(UPDATED_PROPERTIES_NAME)
            .propertiesType(UPDATED_PROPERTIES_TYPE)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .quantityPending(UPDATED_QUANTITY_PENDING)
            .quantityReceived(UPDATED_QUANTITY_RECEIVED)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalPriceNDT(UPDATED_TOTAL_PRICE_NDT)
            .website(UPDATED_WEBSITE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return orderItem;
    }

    @BeforeEach
    public void initTest() {
        orderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testOrderItem.getOriginLink()).isEqualTo(DEFAULT_ORIGIN_LINK);
        assertThat(testOrderItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderItem.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOrderItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrderItem.getPriceNDT()).isEqualTo(DEFAULT_PRICE_NDT);
        assertThat(testOrderItem.getPropertiesId()).isEqualTo(DEFAULT_PROPERTIES_ID);
        assertThat(testOrderItem.getPropertiesImage()).isEqualTo(DEFAULT_PROPERTIES_IMAGE);
        assertThat(testOrderItem.getPropertiesMD5()).isEqualTo(DEFAULT_PROPERTIES_MD_5);
        assertThat(testOrderItem.getPropertiesName()).isEqualTo(DEFAULT_PROPERTIES_NAME);
        assertThat(testOrderItem.getPropertiesType()).isEqualTo(DEFAULT_PROPERTIES_TYPE);
        assertThat(testOrderItem.getQuantityOrder()).isEqualTo(DEFAULT_QUANTITY_ORDER);
        assertThat(testOrderItem.getQuantityPending()).isEqualTo(DEFAULT_QUANTITY_PENDING);
        assertThat(testOrderItem.getQuantityReceived()).isEqualTo(DEFAULT_QUANTITY_RECEIVED);
        assertThat(testOrderItem.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrderItem.getTotalPriceNDT()).isEqualTo(DEFAULT_TOTAL_PRICE_NDT);
        assertThat(testOrderItem.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrderItem.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testOrderItem.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem with an existing ID
        orderItem.setId(1L);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc.perform(get("/api/order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].originLink").value(hasItem(DEFAULT_ORIGIN_LINK.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceNDT").value(hasItem(DEFAULT_PRICE_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].propertiesId").value(hasItem(DEFAULT_PROPERTIES_ID.toString())))
            .andExpect(jsonPath("$.[*].propertiesImage").value(hasItem(DEFAULT_PROPERTIES_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].propertiesMD5").value(hasItem(DEFAULT_PROPERTIES_MD_5.toString())))
            .andExpect(jsonPath("$.[*].propertiesName").value(hasItem(DEFAULT_PROPERTIES_NAME.toString())))
            .andExpect(jsonPath("$.[*].propertiesType").value(hasItem(DEFAULT_PROPERTIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantityOrder").value(hasItem(DEFAULT_QUANTITY_ORDER)))
            .andExpect(jsonPath("$.[*].quantityPending").value(hasItem(DEFAULT_QUANTITY_PENDING)))
            .andExpect(jsonPath("$.[*].quantityReceived").value(hasItem(DEFAULT_QUANTITY_RECEIVED)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPriceNDT").value(hasItem(DEFAULT_TOTAL_PRICE_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.originLink").value(DEFAULT_ORIGIN_LINK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.priceNDT").value(DEFAULT_PRICE_NDT.doubleValue()))
            .andExpect(jsonPath("$.propertiesId").value(DEFAULT_PROPERTIES_ID.toString()))
            .andExpect(jsonPath("$.propertiesImage").value(DEFAULT_PROPERTIES_IMAGE.toString()))
            .andExpect(jsonPath("$.propertiesMD5").value(DEFAULT_PROPERTIES_MD_5.toString()))
            .andExpect(jsonPath("$.propertiesName").value(DEFAULT_PROPERTIES_NAME.toString()))
            .andExpect(jsonPath("$.propertiesType").value(DEFAULT_PROPERTIES_TYPE.toString()))
            .andExpect(jsonPath("$.quantityOrder").value(DEFAULT_QUANTITY_ORDER))
            .andExpect(jsonPath("$.quantityPending").value(DEFAULT_QUANTITY_PENDING))
            .andExpect(jsonPath("$.quantityReceived").value(DEFAULT_QUANTITY_RECEIVED))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.totalPriceNDT").value(DEFAULT_TOTAL_PRICE_NDT.doubleValue()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItem are not directly saved in db
        em.detach(updatedOrderItem);
        updatedOrderItem
            .avatar(UPDATED_AVATAR)
            .originLink(UPDATED_ORIGIN_LINK)
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .price(UPDATED_PRICE)
            .priceNDT(UPDATED_PRICE_NDT)
            .propertiesId(UPDATED_PROPERTIES_ID)
            .propertiesImage(UPDATED_PROPERTIES_IMAGE)
            .propertiesMD5(UPDATED_PROPERTIES_MD_5)
            .propertiesName(UPDATED_PROPERTIES_NAME)
            .propertiesType(UPDATED_PROPERTIES_TYPE)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .quantityPending(UPDATED_QUANTITY_PENDING)
            .quantityReceived(UPDATED_QUANTITY_RECEIVED)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalPriceNDT(UPDATED_TOTAL_PRICE_NDT)
            .website(UPDATED_WEBSITE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(updatedOrderItem);

        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testOrderItem.getOriginLink()).isEqualTo(UPDATED_ORIGIN_LINK);
        assertThat(testOrderItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderItem.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOrderItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderItem.getPriceNDT()).isEqualTo(UPDATED_PRICE_NDT);
        assertThat(testOrderItem.getPropertiesId()).isEqualTo(UPDATED_PROPERTIES_ID);
        assertThat(testOrderItem.getPropertiesImage()).isEqualTo(UPDATED_PROPERTIES_IMAGE);
        assertThat(testOrderItem.getPropertiesMD5()).isEqualTo(UPDATED_PROPERTIES_MD_5);
        assertThat(testOrderItem.getPropertiesName()).isEqualTo(UPDATED_PROPERTIES_NAME);
        assertThat(testOrderItem.getPropertiesType()).isEqualTo(UPDATED_PROPERTIES_TYPE);
        assertThat(testOrderItem.getQuantityOrder()).isEqualTo(UPDATED_QUANTITY_ORDER);
        assertThat(testOrderItem.getQuantityPending()).isEqualTo(UPDATED_QUANTITY_PENDING);
        assertThat(testOrderItem.getQuantityReceived()).isEqualTo(UPDATED_QUANTITY_RECEIVED);
        assertThat(testOrderItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrderItem.getTotalPriceNDT()).isEqualTo(UPDATED_TOTAL_PRICE_NDT);
        assertThat(testOrderItem.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrderItem.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testOrderItem.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Create the OrderItem
        OrderItemDTO orderItemDTO = orderItemMapper.toDto(orderItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Delete the orderItem
        restOrderItemMockMvc.perform(delete("/api/order-items/{id}", orderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItem.class);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(orderItem1.getId());
        assertThat(orderItem1).isEqualTo(orderItem2);
        orderItem2.setId(2L);
        assertThat(orderItem1).isNotEqualTo(orderItem2);
        orderItem1.setId(null);
        assertThat(orderItem1).isNotEqualTo(orderItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderItemDTO.class);
        OrderItemDTO orderItemDTO1 = new OrderItemDTO();
        orderItemDTO1.setId(1L);
        OrderItemDTO orderItemDTO2 = new OrderItemDTO();
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO2.setId(orderItemDTO1.getId());
        assertThat(orderItemDTO1).isEqualTo(orderItemDTO2);
        orderItemDTO2.setId(2L);
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
        orderItemDTO1.setId(null);
        assertThat(orderItemDTO1).isNotEqualTo(orderItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderItemMapper.fromId(null)).isNull();
    }
}
