package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.ShoppingCart;
import com.dropshipping.repository.ShoppingCartRepository;
import com.dropshipping.service.ShoppingCartService;
import com.dropshipping.service.dto.ShoppingCartDTO;
import com.dropshipping.service.mapper.ShoppingCartMapper;
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
 * Integration tests for the {@Link ShoppingCartResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class ShoppingCartResourceIT {

    private static final String DEFAULT_ALIWANGWANG = "AAAAAAAAAA";
    private static final String UPDATED_ALIWANGWANG = "BBBBBBBBBB";

    private static final Float DEFAULT_DEPOSIT_AMOUNT = 1F;
    private static final Float UPDATED_DEPOSIT_AMOUNT = 2F;

    private static final Float DEFAULT_DEPOSIT_RATIO = 1F;
    private static final Float UPDATED_DEPOSIT_RATIO = 2F;

    private static final Float DEFAULT_SERVICE_FEE = 1F;
    private static final Float UPDATED_SERVICE_FEE = 2F;

    private static final Float DEFAULT_SERVICE_FEE_DISCOUNT = 1F;
    private static final Float UPDATED_SERVICE_FEE_DISCOUNT = 2F;

    private static final Boolean DEFAULT_ITEM_CHECKING = false;
    private static final Boolean UPDATED_ITEM_CHECKING = true;

    private static final Boolean DEFAULT_ITEM_WOOD_CRATING = false;
    private static final Boolean UPDATED_ITEM_WOOD_CRATING = true;

    private static final String DEFAULT_SHOP_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_LINK = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_DATA = "BBBBBBBBBB";

    private static final Float DEFAULT_TOTAL_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT = 2F;

    private static final Integer DEFAULT_TOTAL_LINK = 1;
    private static final Integer UPDATED_TOTAL_LINK = 2;

    private static final Integer DEFAULT_TOTAL_QUANTITY = 1;
    private static final Integer UPDATED_TOTAL_QUANTITY = 2;

    private static final Float DEFAULT_FINAL_AMOUNT = 1F;
    private static final Float UPDATED_FINAL_AMOUNT = 2F;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private ShoppingCartService shoppingCartService;

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

    private MockMvc restShoppingCartMockMvc;

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingCartResource shoppingCartResource = new ShoppingCartResource(shoppingCartService);
        this.restShoppingCartMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartResource)
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
    public static ShoppingCart createEntity(EntityManager em) {
        ShoppingCart shoppingCart = new ShoppingCart()
            .aliwangwang(DEFAULT_ALIWANGWANG)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT)
            .depositRatio(DEFAULT_DEPOSIT_RATIO)
            .serviceFee(DEFAULT_SERVICE_FEE)
            .serviceFeeDiscount(DEFAULT_SERVICE_FEE_DISCOUNT)
            .itemChecking(DEFAULT_ITEM_CHECKING)
            .itemWoodCrating(DEFAULT_ITEM_WOOD_CRATING)
            .shopId(DEFAULT_SHOP_ID)
            .shopLink(DEFAULT_SHOP_LINK)
            .shopName(DEFAULT_SHOP_NAME)
            .shopNote(DEFAULT_SHOP_NOTE)
            .sourceData(DEFAULT_SOURCE_DATA)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalLink(DEFAULT_TOTAL_LINK)
            .totalQuantity(DEFAULT_TOTAL_QUANTITY)
            .finalAmount(DEFAULT_FINAL_AMOUNT)
            .website(DEFAULT_WEBSITE)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return shoppingCart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingCart createUpdatedEntity(EntityManager em) {
        ShoppingCart shoppingCart = new ShoppingCart()
            .aliwangwang(UPDATED_ALIWANGWANG)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .depositRatio(UPDATED_DEPOSIT_RATIO)
            .serviceFee(UPDATED_SERVICE_FEE)
            .serviceFeeDiscount(UPDATED_SERVICE_FEE_DISCOUNT)
            .itemChecking(UPDATED_ITEM_CHECKING)
            .itemWoodCrating(UPDATED_ITEM_WOOD_CRATING)
            .shopId(UPDATED_SHOP_ID)
            .shopLink(UPDATED_SHOP_LINK)
            .shopName(UPDATED_SHOP_NAME)
            .shopNote(UPDATED_SHOP_NOTE)
            .sourceData(UPDATED_SOURCE_DATA)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalLink(UPDATED_TOTAL_LINK)
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .finalAmount(UPDATED_FINAL_AMOUNT)
            .website(UPDATED_WEBSITE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return shoppingCart;
    }

    @BeforeEach
    public void initTest() {
        shoppingCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCart() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getAliwangwang()).isEqualTo(DEFAULT_ALIWANGWANG);
        assertThat(testShoppingCart.getDepositAmount()).isEqualTo(DEFAULT_DEPOSIT_AMOUNT);
        assertThat(testShoppingCart.getDepositRatio()).isEqualTo(DEFAULT_DEPOSIT_RATIO);
        assertThat(testShoppingCart.getServiceFee()).isEqualTo(DEFAULT_SERVICE_FEE);
        assertThat(testShoppingCart.getServiceFeeDiscount()).isEqualTo(DEFAULT_SERVICE_FEE_DISCOUNT);
        assertThat(testShoppingCart.isItemChecking()).isEqualTo(DEFAULT_ITEM_CHECKING);
        assertThat(testShoppingCart.isItemWoodCrating()).isEqualTo(DEFAULT_ITEM_WOOD_CRATING);
        assertThat(testShoppingCart.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testShoppingCart.getShopLink()).isEqualTo(DEFAULT_SHOP_LINK);
        assertThat(testShoppingCart.getShopName()).isEqualTo(DEFAULT_SHOP_NAME);
        assertThat(testShoppingCart.getShopNote()).isEqualTo(DEFAULT_SHOP_NOTE);
        assertThat(testShoppingCart.getSourceData()).isEqualTo(DEFAULT_SOURCE_DATA);
        assertThat(testShoppingCart.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testShoppingCart.getTotalLink()).isEqualTo(DEFAULT_TOTAL_LINK);
        assertThat(testShoppingCart.getTotalQuantity()).isEqualTo(DEFAULT_TOTAL_QUANTITY);
        assertThat(testShoppingCart.getFinalAmount()).isEqualTo(DEFAULT_FINAL_AMOUNT);
        assertThat(testShoppingCart.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testShoppingCart.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testShoppingCart.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createShoppingCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart with an existing ID
        shoppingCart.setId(1L);
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get all the shoppingCartList
        restShoppingCartMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].aliwangwang").value(hasItem(DEFAULT_ALIWANGWANG.toString())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositRatio").value(hasItem(DEFAULT_DEPOSIT_RATIO.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceFee").value(hasItem(DEFAULT_SERVICE_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceFeeDiscount").value(hasItem(DEFAULT_SERVICE_FEE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].itemChecking").value(hasItem(DEFAULT_ITEM_CHECKING.booleanValue())))
            .andExpect(jsonPath("$.[*].itemWoodCrating").value(hasItem(DEFAULT_ITEM_WOOD_CRATING.booleanValue())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.toString())))
            .andExpect(jsonPath("$.[*].shopLink").value(hasItem(DEFAULT_SHOP_LINK.toString())))
            .andExpect(jsonPath("$.[*].shopName").value(hasItem(DEFAULT_SHOP_NAME.toString())))
            .andExpect(jsonPath("$.[*].shopNote").value(hasItem(DEFAULT_SHOP_NOTE.toString())))
            .andExpect(jsonPath("$.[*].sourceData").value(hasItem(DEFAULT_SOURCE_DATA.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalLink").value(hasItem(DEFAULT_TOTAL_LINK)))
            .andExpect(jsonPath("$.[*].totalQuantity").value(hasItem(DEFAULT_TOTAL_QUANTITY)))
            .andExpect(jsonPath("$.[*].finalAmount").value(hasItem(DEFAULT_FINAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCart.getId().intValue()))
            .andExpect(jsonPath("$.aliwangwang").value(DEFAULT_ALIWANGWANG.toString()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.depositRatio").value(DEFAULT_DEPOSIT_RATIO.doubleValue()))
            .andExpect(jsonPath("$.serviceFee").value(DEFAULT_SERVICE_FEE.doubleValue()))
            .andExpect(jsonPath("$.serviceFeeDiscount").value(DEFAULT_SERVICE_FEE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.itemChecking").value(DEFAULT_ITEM_CHECKING.booleanValue()))
            .andExpect(jsonPath("$.itemWoodCrating").value(DEFAULT_ITEM_WOOD_CRATING.booleanValue()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.toString()))
            .andExpect(jsonPath("$.shopLink").value(DEFAULT_SHOP_LINK.toString()))
            .andExpect(jsonPath("$.shopName").value(DEFAULT_SHOP_NAME.toString()))
            .andExpect(jsonPath("$.shopNote").value(DEFAULT_SHOP_NOTE.toString()))
            .andExpect(jsonPath("$.sourceData").value(DEFAULT_SOURCE_DATA.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalLink").value(DEFAULT_TOTAL_LINK))
            .andExpect(jsonPath("$.totalQuantity").value(DEFAULT_TOTAL_QUANTITY))
            .andExpect(jsonPath("$.finalAmount").value(DEFAULT_FINAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCart() throws Exception {
        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart
        ShoppingCart updatedShoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingCart are not directly saved in db
        em.detach(updatedShoppingCart);
        updatedShoppingCart
            .aliwangwang(UPDATED_ALIWANGWANG)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .depositRatio(UPDATED_DEPOSIT_RATIO)
            .serviceFee(UPDATED_SERVICE_FEE)
            .serviceFeeDiscount(UPDATED_SERVICE_FEE_DISCOUNT)
            .itemChecking(UPDATED_ITEM_CHECKING)
            .itemWoodCrating(UPDATED_ITEM_WOOD_CRATING)
            .shopId(UPDATED_SHOP_ID)
            .shopLink(UPDATED_SHOP_LINK)
            .shopName(UPDATED_SHOP_NAME)
            .shopNote(UPDATED_SHOP_NOTE)
            .sourceData(UPDATED_SOURCE_DATA)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalLink(UPDATED_TOTAL_LINK)
            .totalQuantity(UPDATED_TOTAL_QUANTITY)
            .finalAmount(UPDATED_FINAL_AMOUNT)
            .website(UPDATED_WEBSITE)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(updatedShoppingCart);

        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getAliwangwang()).isEqualTo(UPDATED_ALIWANGWANG);
        assertThat(testShoppingCart.getDepositAmount()).isEqualTo(UPDATED_DEPOSIT_AMOUNT);
        assertThat(testShoppingCart.getDepositRatio()).isEqualTo(UPDATED_DEPOSIT_RATIO);
        assertThat(testShoppingCart.getServiceFee()).isEqualTo(UPDATED_SERVICE_FEE);
        assertThat(testShoppingCart.getServiceFeeDiscount()).isEqualTo(UPDATED_SERVICE_FEE_DISCOUNT);
        assertThat(testShoppingCart.isItemChecking()).isEqualTo(UPDATED_ITEM_CHECKING);
        assertThat(testShoppingCart.isItemWoodCrating()).isEqualTo(UPDATED_ITEM_WOOD_CRATING);
        assertThat(testShoppingCart.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testShoppingCart.getShopLink()).isEqualTo(UPDATED_SHOP_LINK);
        assertThat(testShoppingCart.getShopName()).isEqualTo(UPDATED_SHOP_NAME);
        assertThat(testShoppingCart.getShopNote()).isEqualTo(UPDATED_SHOP_NOTE);
        assertThat(testShoppingCart.getSourceData()).isEqualTo(UPDATED_SOURCE_DATA);
        assertThat(testShoppingCart.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testShoppingCart.getTotalLink()).isEqualTo(UPDATED_TOTAL_LINK);
        assertThat(testShoppingCart.getTotalQuantity()).isEqualTo(UPDATED_TOTAL_QUANTITY);
        assertThat(testShoppingCart.getFinalAmount()).isEqualTo(UPDATED_FINAL_AMOUNT);
        assertThat(testShoppingCart.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testShoppingCart.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testShoppingCart.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toDto(shoppingCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        int databaseSizeBeforeDelete = shoppingCartRepository.findAll().size();

        // Delete the shoppingCart
        restShoppingCartMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCart.class);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(1L);
        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(shoppingCart1.getId());
        assertThat(shoppingCart1).isEqualTo(shoppingCart2);
        shoppingCart2.setId(2L);
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
        shoppingCart1.setId(null);
        assertThat(shoppingCart1).isNotEqualTo(shoppingCart2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartDTO.class);
        ShoppingCartDTO shoppingCartDTO1 = new ShoppingCartDTO();
        shoppingCartDTO1.setId(1L);
        ShoppingCartDTO shoppingCartDTO2 = new ShoppingCartDTO();
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
        shoppingCartDTO2.setId(shoppingCartDTO1.getId());
        assertThat(shoppingCartDTO1).isEqualTo(shoppingCartDTO2);
        shoppingCartDTO2.setId(2L);
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
        shoppingCartDTO1.setId(null);
        assertThat(shoppingCartDTO1).isNotEqualTo(shoppingCartDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingCartMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingCartMapper.fromId(null)).isNull();
    }
}
