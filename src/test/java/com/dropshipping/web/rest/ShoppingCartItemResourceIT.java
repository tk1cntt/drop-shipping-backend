package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.ShoppingCartItem;
import com.dropshipping.repository.ShoppingCartItemRepository;
import com.dropshipping.service.ShoppingCartItemService;
import com.dropshipping.service.dto.ShoppingCartItemDTO;
import com.dropshipping.service.mapper.ShoppingCartItemMapper;
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
 * Integration tests for the {@Link ShoppingCartItemResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class ShoppingCartItemResourceIT {

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_LINK = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_LINK = "BBBBBBBBBB";

    private static final Float DEFAULT_ITEM_PRICE = 1F;
    private static final Float UPDATED_ITEM_PRICE = 2F;

    private static final Float DEFAULT_ITEM_PRICE_NDT = 1F;
    private static final Float UPDATED_ITEM_PRICE_NDT = 2F;

    private static final String DEFAULT_ITEM_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NOTE = "BBBBBBBBBB";

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

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_REQUIRE_MIN = 1;
    private static final Integer UPDATED_REQUIRE_MIN = 2;

    private static final Float DEFAULT_TOTAL_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT = 2F;

    private static final Float DEFAULT_TOTAL_AMOUNT_NDT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT_NDT = 2F;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

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

    private MockMvc restShoppingCartItemMockMvc;

    private ShoppingCartItem shoppingCartItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartItemResource shoppingCartItemResource = new ShoppingCartItemResource(shoppingCartItemService);
        this.restShoppingCartItemMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartItemResource)
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
    public static ShoppingCartItem createEntity(EntityManager em) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem()
            .itemId(DEFAULT_ITEM_ID)
            .itemImage(DEFAULT_ITEM_IMAGE)
            .itemLink(DEFAULT_ITEM_LINK)
            .itemPrice(DEFAULT_ITEM_PRICE)
            .itemPriceNDT(DEFAULT_ITEM_PRICE_NDT)
            .itemNote(DEFAULT_ITEM_NOTE)
            .propertiesId(DEFAULT_PROPERTIES_ID)
            .propertiesImage(DEFAULT_PROPERTIES_IMAGE)
            .propertiesMD5(DEFAULT_PROPERTIES_MD_5)
            .propertiesName(DEFAULT_PROPERTIES_NAME)
            .propertiesType(DEFAULT_PROPERTIES_TYPE)
            .quantity(DEFAULT_QUANTITY)
            .requireMin(DEFAULT_REQUIRE_MIN)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalAmountNDT(DEFAULT_TOTAL_AMOUNT_NDT)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return shoppingCartItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCartItem createUpdatedEntity(EntityManager em) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem()
            .itemId(UPDATED_ITEM_ID)
            .itemImage(UPDATED_ITEM_IMAGE)
            .itemLink(UPDATED_ITEM_LINK)
            .itemPrice(UPDATED_ITEM_PRICE)
            .itemPriceNDT(UPDATED_ITEM_PRICE_NDT)
            .itemNote(UPDATED_ITEM_NOTE)
            .propertiesId(UPDATED_PROPERTIES_ID)
            .propertiesImage(UPDATED_PROPERTIES_IMAGE)
            .propertiesMD5(UPDATED_PROPERTIES_MD_5)
            .propertiesName(UPDATED_PROPERTIES_NAME)
            .propertiesType(UPDATED_PROPERTIES_TYPE)
            .quantity(UPDATED_QUANTITY)
            .requireMin(UPDATED_REQUIRE_MIN)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountNDT(UPDATED_TOTAL_AMOUNT_NDT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return shoppingCartItem;
    }

    @BeforeEach
    public void initTest() {
        shoppingCartItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCartItem() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemMapper.toDto(shoppingCartItem);
        restShoppingCartItemMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCartItem testShoppingCartItem = shoppingCartItemList.get(shoppingCartItemList.size() - 1);
        assertThat(testShoppingCartItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testShoppingCartItem.getItemImage()).isEqualTo(DEFAULT_ITEM_IMAGE);
        assertThat(testShoppingCartItem.getItemLink()).isEqualTo(DEFAULT_ITEM_LINK);
        assertThat(testShoppingCartItem.getItemPrice()).isEqualTo(DEFAULT_ITEM_PRICE);
        assertThat(testShoppingCartItem.getItemPriceNDT()).isEqualTo(DEFAULT_ITEM_PRICE_NDT);
        assertThat(testShoppingCartItem.getItemNote()).isEqualTo(DEFAULT_ITEM_NOTE);
        assertThat(testShoppingCartItem.getPropertiesId()).isEqualTo(DEFAULT_PROPERTIES_ID);
        assertThat(testShoppingCartItem.getPropertiesImage()).isEqualTo(DEFAULT_PROPERTIES_IMAGE);
        assertThat(testShoppingCartItem.getPropertiesMD5()).isEqualTo(DEFAULT_PROPERTIES_MD_5);
        assertThat(testShoppingCartItem.getPropertiesName()).isEqualTo(DEFAULT_PROPERTIES_NAME);
        assertThat(testShoppingCartItem.getPropertiesType()).isEqualTo(DEFAULT_PROPERTIES_TYPE);
        assertThat(testShoppingCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testShoppingCartItem.getRequireMin()).isEqualTo(DEFAULT_REQUIRE_MIN);
        assertThat(testShoppingCartItem.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testShoppingCartItem.getTotalAmountNDT()).isEqualTo(DEFAULT_TOTAL_AMOUNT_NDT);
        assertThat(testShoppingCartItem.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testShoppingCartItem.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createShoppingCartItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem with an existing ID
        shoppingCartItem.setId(1L);
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemMapper.toDto(shoppingCartItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartItemMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        // Get all the shoppingCartItemList
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].itemImage").value(hasItem(DEFAULT_ITEM_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].itemLink").value(hasItem(DEFAULT_ITEM_LINK.toString())))
            .andExpect(jsonPath("$.[*].itemPrice").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].itemPriceNDT").value(hasItem(DEFAULT_ITEM_PRICE_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemNote").value(hasItem(DEFAULT_ITEM_NOTE.toString())))
            .andExpect(jsonPath("$.[*].propertiesId").value(hasItem(DEFAULT_PROPERTIES_ID.toString())))
            .andExpect(jsonPath("$.[*].propertiesImage").value(hasItem(DEFAULT_PROPERTIES_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].propertiesMD5").value(hasItem(DEFAULT_PROPERTIES_MD_5.toString())))
            .andExpect(jsonPath("$.[*].propertiesName").value(hasItem(DEFAULT_PROPERTIES_NAME.toString())))
            .andExpect(jsonPath("$.[*].propertiesType").value(hasItem(DEFAULT_PROPERTIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].requireMin").value(hasItem(DEFAULT_REQUIRE_MIN)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountNDT").value(hasItem(DEFAULT_TOTAL_AMOUNT_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        // Get the shoppingCartItem
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items/{id}", shoppingCartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCartItem.getId().intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.toString()))
            .andExpect(jsonPath("$.itemImage").value(DEFAULT_ITEM_IMAGE.toString()))
            .andExpect(jsonPath("$.itemLink").value(DEFAULT_ITEM_LINK.toString()))
            .andExpect(jsonPath("$.itemPrice").value(DEFAULT_ITEM_PRICE.doubleValue()))
            .andExpect(jsonPath("$.itemPriceNDT").value(DEFAULT_ITEM_PRICE_NDT.doubleValue()))
            .andExpect(jsonPath("$.itemNote").value(DEFAULT_ITEM_NOTE.toString()))
            .andExpect(jsonPath("$.propertiesId").value(DEFAULT_PROPERTIES_ID.toString()))
            .andExpect(jsonPath("$.propertiesImage").value(DEFAULT_PROPERTIES_IMAGE.toString()))
            .andExpect(jsonPath("$.propertiesMD5").value(DEFAULT_PROPERTIES_MD_5.toString()))
            .andExpect(jsonPath("$.propertiesName").value(DEFAULT_PROPERTIES_NAME.toString()))
            .andExpect(jsonPath("$.propertiesType").value(DEFAULT_PROPERTIES_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.requireMin").value(DEFAULT_REQUIRE_MIN))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmountNDT").value(DEFAULT_TOTAL_AMOUNT_NDT.doubleValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCartItem() throws Exception {
        // Get the shoppingCartItem
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        int databaseSizeBeforeUpdate = shoppingCartItemRepository.findAll().size();

        // Update the shoppingCartItem
        ShoppingCartItem updatedShoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItem.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCartItem are not directly saved in db
        em.detach(updatedShoppingCartItem);
        updatedShoppingCartItem
            .itemId(UPDATED_ITEM_ID)
            .itemImage(UPDATED_ITEM_IMAGE)
            .itemLink(UPDATED_ITEM_LINK)
            .itemPrice(UPDATED_ITEM_PRICE)
            .itemPriceNDT(UPDATED_ITEM_PRICE_NDT)
            .itemNote(UPDATED_ITEM_NOTE)
            .propertiesId(UPDATED_PROPERTIES_ID)
            .propertiesImage(UPDATED_PROPERTIES_IMAGE)
            .propertiesMD5(UPDATED_PROPERTIES_MD_5)
            .propertiesName(UPDATED_PROPERTIES_NAME)
            .propertiesType(UPDATED_PROPERTIES_TYPE)
            .quantity(UPDATED_QUANTITY)
            .requireMin(UPDATED_REQUIRE_MIN)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountNDT(UPDATED_TOTAL_AMOUNT_NDT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemMapper.toDto(updatedShoppingCartItem);

        restShoppingCartItemMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCartItem testShoppingCartItem = shoppingCartItemList.get(shoppingCartItemList.size() - 1);
        assertThat(testShoppingCartItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testShoppingCartItem.getItemImage()).isEqualTo(UPDATED_ITEM_IMAGE);
        assertThat(testShoppingCartItem.getItemLink()).isEqualTo(UPDATED_ITEM_LINK);
        assertThat(testShoppingCartItem.getItemPrice()).isEqualTo(UPDATED_ITEM_PRICE);
        assertThat(testShoppingCartItem.getItemPriceNDT()).isEqualTo(UPDATED_ITEM_PRICE_NDT);
        assertThat(testShoppingCartItem.getItemNote()).isEqualTo(UPDATED_ITEM_NOTE);
        assertThat(testShoppingCartItem.getPropertiesId()).isEqualTo(UPDATED_PROPERTIES_ID);
        assertThat(testShoppingCartItem.getPropertiesImage()).isEqualTo(UPDATED_PROPERTIES_IMAGE);
        assertThat(testShoppingCartItem.getPropertiesMD5()).isEqualTo(UPDATED_PROPERTIES_MD_5);
        assertThat(testShoppingCartItem.getPropertiesName()).isEqualTo(UPDATED_PROPERTIES_NAME);
        assertThat(testShoppingCartItem.getPropertiesType()).isEqualTo(UPDATED_PROPERTIES_TYPE);
        assertThat(testShoppingCartItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testShoppingCartItem.getRequireMin()).isEqualTo(UPDATED_REQUIRE_MIN);
        assertThat(testShoppingCartItem.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testShoppingCartItem.getTotalAmountNDT()).isEqualTo(UPDATED_TOTAL_AMOUNT_NDT);
        assertThat(testShoppingCartItem.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testShoppingCartItem.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem
        ShoppingCartItemDTO shoppingCartItemDTO = shoppingCartItemMapper.toDto(shoppingCartItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartItemMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        int databaseSizeBeforeDelete = shoppingCartItemRepository.findAll().size();

        // Delete the shoppingCartItem
        restShoppingCartItemMockMvc.perform(delete("/api/shopping-cart-items/{id}", shoppingCartItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItem.class);
        ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem();
        shoppingCartItem1.setId(1L);
        ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem();
        shoppingCartItem2.setId(shoppingCartItem1.getId());
        assertThat(shoppingCartItem1).isEqualTo(shoppingCartItem2);
        shoppingCartItem2.setId(2L);
        assertThat(shoppingCartItem1).isNotEqualTo(shoppingCartItem2);
        shoppingCartItem1.setId(null);
        assertThat(shoppingCartItem1).isNotEqualTo(shoppingCartItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItemDTO.class);
        ShoppingCartItemDTO shoppingCartItemDTO1 = new ShoppingCartItemDTO();
        shoppingCartItemDTO1.setId(1L);
        ShoppingCartItemDTO shoppingCartItemDTO2 = new ShoppingCartItemDTO();
        assertThat(shoppingCartItemDTO1).isNotEqualTo(shoppingCartItemDTO2);
        shoppingCartItemDTO2.setId(shoppingCartItemDTO1.getId());
        assertThat(shoppingCartItemDTO1).isEqualTo(shoppingCartItemDTO2);
        shoppingCartItemDTO2.setId(2L);
        assertThat(shoppingCartItemDTO1).isNotEqualTo(shoppingCartItemDTO2);
        shoppingCartItemDTO1.setId(null);
        assertThat(shoppingCartItemDTO1).isNotEqualTo(shoppingCartItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingCartItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingCartItemMapper.fromId(null)).isNull();
    }
}
