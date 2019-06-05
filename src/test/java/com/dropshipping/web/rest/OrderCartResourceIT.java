package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderCart;
import com.dropshipping.repository.OrderCartRepository;
import com.dropshipping.service.OrderCartService;
import com.dropshipping.service.dto.OrderCartDTO;
import com.dropshipping.service.mapper.OrderCartMapper;
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
 * Integration tests for the {@Link OrderCartResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderCartResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final Float DEFAULT_AMOUNT_DISCOUNT = 1F;
    private static final Float UPDATED_AMOUNT_DISCOUNT = 2F;

    private static final Float DEFAULT_AMOUNT_PAID = 1F;
    private static final Float UPDATED_AMOUNT_PAID = 2F;

    private static final Float DEFAULT_DEPOSIT_AMOUNT = 1F;
    private static final Float UPDATED_DEPOSIT_AMOUNT = 2F;

    private static final Float DEFAULT_DEPOSIT_RATIO = 1F;
    private static final Float UPDATED_DEPOSIT_RATIO = 2F;

    private static final LocalDate DEFAULT_DEPOSIT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEPOSIT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE_NDT = 1F;
    private static final Float UPDATED_DOMESTIC_SHIPPING_CHINA_FEE_NDT = 2F;

    private static final Float DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE = 1F;
    private static final Float UPDATED_DOMESTIC_SHIPPING_CHINA_FEE = 2F;

    private static final Float DEFAULT_DOMESTIC_SHIPPING_VIETNAM_FEE = 1F;
    private static final Float UPDATED_DOMESTIC_SHIPPING_VIETNAM_FEE = 2F;

    private static final Integer DEFAULT_QUANTITY_ORDER = 1;
    private static final Integer UPDATED_QUANTITY_ORDER = 2;

    private static final Integer DEFAULT_QUANTITY_PENDING = 1;
    private static final Integer UPDATED_QUANTITY_PENDING = 2;

    private static final Integer DEFAULT_QUANTITY_RECEIVED = 1;
    private static final Integer UPDATED_QUANTITY_RECEIVED = 2;

    private static final Integer DEFAULT_RATE = 1;
    private static final Integer UPDATED_RATE = 2;

    private static final String DEFAULT_RECEIVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_NOTE = "BBBBBBBBBB";

    private static final Float DEFAULT_REFUND_AMOUNT_BY_ALIPAY = 1F;
    private static final Float UPDATED_REFUND_AMOUNT_BY_ALIPAY = 2F;

    private static final Float DEFAULT_REFUND_AMOUNT_BY_COMPLAINT = 1F;
    private static final Float UPDATED_REFUND_AMOUNT_BY_COMPLAINT = 2F;

    private static final Float DEFAULT_REFUND_AMOUNT_BY_ORDER = 1F;
    private static final Float UPDATED_REFUND_AMOUNT_BY_ORDER = 2F;

    private static final Float DEFAULT_REFUND_AMOUNT_PENDING = 1F;
    private static final Float UPDATED_REFUND_AMOUNT_PENDING = 2F;

    private static final Float DEFAULT_SHIPPING_CHINA_VIETNAM_FEE = 1F;
    private static final Float UPDATED_SHIPPING_CHINA_VIETNAM_FEE = 2F;

    private static final Float DEFAULT_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT = 1F;
    private static final Float UPDATED_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT = 2F;

    private static final String DEFAULT_SHOP_ALIWANG = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_ALIWANG = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_LINK = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE_LADING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE_LADING_CODE = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.DEPOSITED;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.ARE_BUYING;

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_STYLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TALLY_FEE = 1;
    private static final Integer UPDATED_TALLY_FEE = 2;

    private static final Float DEFAULT_TOTAL_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT = 2F;

    private static final Float DEFAULT_TOTAL_AMOUNT_NDT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT_NDT = 2F;

    private static final Float DEFAULT_TOTAL_PAID_BY_CUSTOMER = 1F;
    private static final Float UPDATED_TOTAL_PAID_BY_CUSTOMER = 2F;

    private static final Float DEFAULT_SERVICE_FEE = 1F;
    private static final Float UPDATED_SERVICE_FEE = 2F;

    private static final Float DEFAULT_SERVICE_FEE_DISCOUNT = 1F;
    private static final Float UPDATED_SERVICE_FEE_DISCOUNT = 2F;

    private static final Float DEFAULT_TOTAL_SERVICE_FEE = 1F;
    private static final Float UPDATED_TOTAL_SERVICE_FEE = 2F;

    private static final Float DEFAULT_FINAL_AMOUNT = 1F;
    private static final Float UPDATED_FINAL_AMOUNT = 2F;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderCartRepository orderCartRepository;

    @Autowired
    private OrderCartMapper orderCartMapper;

    @Autowired
    private OrderCartService orderCartService;

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

    private MockMvc restOrderCartMockMvc;

    private OrderCart orderCart;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderCartResource orderCartResource = new OrderCartResource(orderCartService);
        this.restOrderCartMockMvc = MockMvcBuilders.standaloneSetup(orderCartResource)
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
    public static OrderCart createEntity(EntityManager em) {
        OrderCart orderCart = new OrderCart()
            .code(DEFAULT_CODE)
            .avatar(DEFAULT_AVATAR)
            .amountDiscount(DEFAULT_AMOUNT_DISCOUNT)
            .amountPaid(DEFAULT_AMOUNT_PAID)
            .depositAmount(DEFAULT_DEPOSIT_AMOUNT)
            .depositRatio(DEFAULT_DEPOSIT_RATIO)
            .depositTime(DEFAULT_DEPOSIT_TIME)
            .domesticShippingChinaFeeNDT(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE_NDT)
            .domesticShippingChinaFee(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE)
            .domesticShippingVietnamFee(DEFAULT_DOMESTIC_SHIPPING_VIETNAM_FEE)
            .quantityOrder(DEFAULT_QUANTITY_ORDER)
            .quantityPending(DEFAULT_QUANTITY_PENDING)
            .quantityReceived(DEFAULT_QUANTITY_RECEIVED)
            .rate(DEFAULT_RATE)
            .receiverName(DEFAULT_RECEIVER_NAME)
            .receiverAddress(DEFAULT_RECEIVER_ADDRESS)
            .receiverMobile(DEFAULT_RECEIVER_MOBILE)
            .receiverNote(DEFAULT_RECEIVER_NOTE)
            .refundAmountByAlipay(DEFAULT_REFUND_AMOUNT_BY_ALIPAY)
            .refundAmountByComplaint(DEFAULT_REFUND_AMOUNT_BY_COMPLAINT)
            .refundAmountByOrder(DEFAULT_REFUND_AMOUNT_BY_ORDER)
            .refundAmountPending(DEFAULT_REFUND_AMOUNT_PENDING)
            .shippingChinaVietnamFee(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE)
            .shippingChinaVietnamFeeDiscount(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT)
            .shopAliwang(DEFAULT_SHOP_ALIWANG)
            .shopId(DEFAULT_SHOP_ID)
            .shopLink(DEFAULT_SHOP_LINK)
            .shopName(DEFAULT_SHOP_NAME)
            .website(DEFAULT_WEBSITE)
            .websiteCode(DEFAULT_WEBSITE_CODE)
            .websiteLadingCode(DEFAULT_WEBSITE_LADING_CODE)
            .status(DEFAULT_STATUS)
            .statusName(DEFAULT_STATUS_NAME)
            .statusStyle(DEFAULT_STATUS_STYLE)
            .tallyFee(DEFAULT_TALLY_FEE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalAmountNDT(DEFAULT_TOTAL_AMOUNT_NDT)
            .totalPaidByCustomer(DEFAULT_TOTAL_PAID_BY_CUSTOMER)
            .serviceFee(DEFAULT_SERVICE_FEE)
            .serviceFeeDiscount(DEFAULT_SERVICE_FEE_DISCOUNT)
            .totalServiceFee(DEFAULT_TOTAL_SERVICE_FEE)
            .finalAmount(DEFAULT_FINAL_AMOUNT)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return orderCart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderCart createUpdatedEntity(EntityManager em) {
        OrderCart orderCart = new OrderCart()
            .code(UPDATED_CODE)
            .avatar(UPDATED_AVATAR)
            .amountDiscount(UPDATED_AMOUNT_DISCOUNT)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .depositRatio(UPDATED_DEPOSIT_RATIO)
            .depositTime(UPDATED_DEPOSIT_TIME)
            .domesticShippingChinaFeeNDT(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE_NDT)
            .domesticShippingChinaFee(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE)
            .domesticShippingVietnamFee(UPDATED_DOMESTIC_SHIPPING_VIETNAM_FEE)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .quantityPending(UPDATED_QUANTITY_PENDING)
            .quantityReceived(UPDATED_QUANTITY_RECEIVED)
            .rate(UPDATED_RATE)
            .receiverName(UPDATED_RECEIVER_NAME)
            .receiverAddress(UPDATED_RECEIVER_ADDRESS)
            .receiverMobile(UPDATED_RECEIVER_MOBILE)
            .receiverNote(UPDATED_RECEIVER_NOTE)
            .refundAmountByAlipay(UPDATED_REFUND_AMOUNT_BY_ALIPAY)
            .refundAmountByComplaint(UPDATED_REFUND_AMOUNT_BY_COMPLAINT)
            .refundAmountByOrder(UPDATED_REFUND_AMOUNT_BY_ORDER)
            .refundAmountPending(UPDATED_REFUND_AMOUNT_PENDING)
            .shippingChinaVietnamFee(UPDATED_SHIPPING_CHINA_VIETNAM_FEE)
            .shippingChinaVietnamFeeDiscount(UPDATED_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT)
            .shopAliwang(UPDATED_SHOP_ALIWANG)
            .shopId(UPDATED_SHOP_ID)
            .shopLink(UPDATED_SHOP_LINK)
            .shopName(UPDATED_SHOP_NAME)
            .website(UPDATED_WEBSITE)
            .websiteCode(UPDATED_WEBSITE_CODE)
            .websiteLadingCode(UPDATED_WEBSITE_LADING_CODE)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .tallyFee(UPDATED_TALLY_FEE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountNDT(UPDATED_TOTAL_AMOUNT_NDT)
            .totalPaidByCustomer(UPDATED_TOTAL_PAID_BY_CUSTOMER)
            .serviceFee(UPDATED_SERVICE_FEE)
            .serviceFeeDiscount(UPDATED_SERVICE_FEE_DISCOUNT)
            .totalServiceFee(UPDATED_TOTAL_SERVICE_FEE)
            .finalAmount(UPDATED_FINAL_AMOUNT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return orderCart;
    }

    @BeforeEach
    public void initTest() {
        orderCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderCart() throws Exception {
        int databaseSizeBeforeCreate = orderCartRepository.findAll().size();

        // Create the OrderCart
        OrderCartDTO orderCartDTO = orderCartMapper.toDto(orderCart);
        restOrderCartMockMvc.perform(post("/api/order-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCartDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderCart in the database
        List<OrderCart> orderCartList = orderCartRepository.findAll();
        assertThat(orderCartList).hasSize(databaseSizeBeforeCreate + 1);
        OrderCart testOrderCart = orderCartList.get(orderCartList.size() - 1);
        assertThat(testOrderCart.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrderCart.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testOrderCart.getAmountDiscount()).isEqualTo(DEFAULT_AMOUNT_DISCOUNT);
        assertThat(testOrderCart.getAmountPaid()).isEqualTo(DEFAULT_AMOUNT_PAID);
        assertThat(testOrderCart.getDepositAmount()).isEqualTo(DEFAULT_DEPOSIT_AMOUNT);
        assertThat(testOrderCart.getDepositRatio()).isEqualTo(DEFAULT_DEPOSIT_RATIO);
        assertThat(testOrderCart.getDepositTime()).isEqualTo(DEFAULT_DEPOSIT_TIME);
        assertThat(testOrderCart.getDomesticShippingChinaFeeNDT()).isEqualTo(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE_NDT);
        assertThat(testOrderCart.getDomesticShippingChinaFee()).isEqualTo(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE);
        assertThat(testOrderCart.getDomesticShippingVietnamFee()).isEqualTo(DEFAULT_DOMESTIC_SHIPPING_VIETNAM_FEE);
        assertThat(testOrderCart.getQuantityOrder()).isEqualTo(DEFAULT_QUANTITY_ORDER);
        assertThat(testOrderCart.getQuantityPending()).isEqualTo(DEFAULT_QUANTITY_PENDING);
        assertThat(testOrderCart.getQuantityReceived()).isEqualTo(DEFAULT_QUANTITY_RECEIVED);
        assertThat(testOrderCart.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testOrderCart.getReceiverName()).isEqualTo(DEFAULT_RECEIVER_NAME);
        assertThat(testOrderCart.getReceiverAddress()).isEqualTo(DEFAULT_RECEIVER_ADDRESS);
        assertThat(testOrderCart.getReceiverMobile()).isEqualTo(DEFAULT_RECEIVER_MOBILE);
        assertThat(testOrderCart.getReceiverNote()).isEqualTo(DEFAULT_RECEIVER_NOTE);
        assertThat(testOrderCart.getRefundAmountByAlipay()).isEqualTo(DEFAULT_REFUND_AMOUNT_BY_ALIPAY);
        assertThat(testOrderCart.getRefundAmountByComplaint()).isEqualTo(DEFAULT_REFUND_AMOUNT_BY_COMPLAINT);
        assertThat(testOrderCart.getRefundAmountByOrder()).isEqualTo(DEFAULT_REFUND_AMOUNT_BY_ORDER);
        assertThat(testOrderCart.getRefundAmountPending()).isEqualTo(DEFAULT_REFUND_AMOUNT_PENDING);
        assertThat(testOrderCart.getShippingChinaVietnamFee()).isEqualTo(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE);
        assertThat(testOrderCart.getShippingChinaVietnamFeeDiscount()).isEqualTo(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT);
        assertThat(testOrderCart.getShopAliwang()).isEqualTo(DEFAULT_SHOP_ALIWANG);
        assertThat(testOrderCart.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testOrderCart.getShopLink()).isEqualTo(DEFAULT_SHOP_LINK);
        assertThat(testOrderCart.getShopName()).isEqualTo(DEFAULT_SHOP_NAME);
        assertThat(testOrderCart.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrderCart.getWebsiteCode()).isEqualTo(DEFAULT_WEBSITE_CODE);
        assertThat(testOrderCart.getWebsiteLadingCode()).isEqualTo(DEFAULT_WEBSITE_LADING_CODE);
        assertThat(testOrderCart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrderCart.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
        assertThat(testOrderCart.getStatusStyle()).isEqualTo(DEFAULT_STATUS_STYLE);
        assertThat(testOrderCart.getTallyFee()).isEqualTo(DEFAULT_TALLY_FEE);
        assertThat(testOrderCart.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testOrderCart.getTotalAmountNDT()).isEqualTo(DEFAULT_TOTAL_AMOUNT_NDT);
        assertThat(testOrderCart.getTotalPaidByCustomer()).isEqualTo(DEFAULT_TOTAL_PAID_BY_CUSTOMER);
        assertThat(testOrderCart.getServiceFee()).isEqualTo(DEFAULT_SERVICE_FEE);
        assertThat(testOrderCart.getServiceFeeDiscount()).isEqualTo(DEFAULT_SERVICE_FEE_DISCOUNT);
        assertThat(testOrderCart.getTotalServiceFee()).isEqualTo(DEFAULT_TOTAL_SERVICE_FEE);
        assertThat(testOrderCart.getFinalAmount()).isEqualTo(DEFAULT_FINAL_AMOUNT);
        assertThat(testOrderCart.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testOrderCart.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createOrderCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderCartRepository.findAll().size();

        // Create the OrderCart with an existing ID
        orderCart.setId(1L);
        OrderCartDTO orderCartDTO = orderCartMapper.toDto(orderCart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderCartMockMvc.perform(post("/api/order-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderCart in the database
        List<OrderCart> orderCartList = orderCartRepository.findAll();
        assertThat(orderCartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderCarts() throws Exception {
        // Initialize the database
        orderCartRepository.saveAndFlush(orderCart);

        // Get all the orderCartList
        restOrderCartMockMvc.perform(get("/api/order-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].amountDiscount").value(hasItem(DEFAULT_AMOUNT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].depositAmount").value(hasItem(DEFAULT_DEPOSIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].depositRatio").value(hasItem(DEFAULT_DEPOSIT_RATIO.doubleValue())))
            .andExpect(jsonPath("$.[*].depositTime").value(hasItem(DEFAULT_DEPOSIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].domesticShippingChinaFeeNDT").value(hasItem(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].domesticShippingChinaFee").value(hasItem(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].domesticShippingVietnamFee").value(hasItem(DEFAULT_DOMESTIC_SHIPPING_VIETNAM_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityOrder").value(hasItem(DEFAULT_QUANTITY_ORDER)))
            .andExpect(jsonPath("$.[*].quantityPending").value(hasItem(DEFAULT_QUANTITY_PENDING)))
            .andExpect(jsonPath("$.[*].quantityReceived").value(hasItem(DEFAULT_QUANTITY_RECEIVED)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].receiverName").value(hasItem(DEFAULT_RECEIVER_NAME.toString())))
            .andExpect(jsonPath("$.[*].receiverAddress").value(hasItem(DEFAULT_RECEIVER_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].receiverMobile").value(hasItem(DEFAULT_RECEIVER_MOBILE.toString())))
            .andExpect(jsonPath("$.[*].receiverNote").value(hasItem(DEFAULT_RECEIVER_NOTE.toString())))
            .andExpect(jsonPath("$.[*].refundAmountByAlipay").value(hasItem(DEFAULT_REFUND_AMOUNT_BY_ALIPAY.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmountByComplaint").value(hasItem(DEFAULT_REFUND_AMOUNT_BY_COMPLAINT.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmountByOrder").value(hasItem(DEFAULT_REFUND_AMOUNT_BY_ORDER.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmountPending").value(hasItem(DEFAULT_REFUND_AMOUNT_PENDING.doubleValue())))
            .andExpect(jsonPath("$.[*].shippingChinaVietnamFee").value(hasItem(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].shippingChinaVietnamFeeDiscount").value(hasItem(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].shopAliwang").value(hasItem(DEFAULT_SHOP_ALIWANG.toString())))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID.toString())))
            .andExpect(jsonPath("$.[*].shopLink").value(hasItem(DEFAULT_SHOP_LINK.toString())))
            .andExpect(jsonPath("$.[*].shopName").value(hasItem(DEFAULT_SHOP_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].websiteCode").value(hasItem(DEFAULT_WEBSITE_CODE.toString())))
            .andExpect(jsonPath("$.[*].websiteLadingCode").value(hasItem(DEFAULT_WEBSITE_LADING_CODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].statusStyle").value(hasItem(DEFAULT_STATUS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].tallyFee").value(hasItem(DEFAULT_TALLY_FEE)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountNDT").value(hasItem(DEFAULT_TOTAL_AMOUNT_NDT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPaidByCustomer").value(hasItem(DEFAULT_TOTAL_PAID_BY_CUSTOMER.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceFee").value(hasItem(DEFAULT_SERVICE_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceFeeDiscount").value(hasItem(DEFAULT_SERVICE_FEE_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalServiceFee").value(hasItem(DEFAULT_TOTAL_SERVICE_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].finalAmount").value(hasItem(DEFAULT_FINAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(DEFAULT_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderCart() throws Exception {
        // Initialize the database
        orderCartRepository.saveAndFlush(orderCart);

        // Get the orderCart
        restOrderCartMockMvc.perform(get("/api/order-carts/{id}", orderCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderCart.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.amountDiscount").value(DEFAULT_AMOUNT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID.doubleValue()))
            .andExpect(jsonPath("$.depositAmount").value(DEFAULT_DEPOSIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.depositRatio").value(DEFAULT_DEPOSIT_RATIO.doubleValue()))
            .andExpect(jsonPath("$.depositTime").value(DEFAULT_DEPOSIT_TIME.toString()))
            .andExpect(jsonPath("$.domesticShippingChinaFeeNDT").value(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE_NDT.doubleValue()))
            .andExpect(jsonPath("$.domesticShippingChinaFee").value(DEFAULT_DOMESTIC_SHIPPING_CHINA_FEE.doubleValue()))
            .andExpect(jsonPath("$.domesticShippingVietnamFee").value(DEFAULT_DOMESTIC_SHIPPING_VIETNAM_FEE.doubleValue()))
            .andExpect(jsonPath("$.quantityOrder").value(DEFAULT_QUANTITY_ORDER))
            .andExpect(jsonPath("$.quantityPending").value(DEFAULT_QUANTITY_PENDING))
            .andExpect(jsonPath("$.quantityReceived").value(DEFAULT_QUANTITY_RECEIVED))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.receiverName").value(DEFAULT_RECEIVER_NAME.toString()))
            .andExpect(jsonPath("$.receiverAddress").value(DEFAULT_RECEIVER_ADDRESS.toString()))
            .andExpect(jsonPath("$.receiverMobile").value(DEFAULT_RECEIVER_MOBILE.toString()))
            .andExpect(jsonPath("$.receiverNote").value(DEFAULT_RECEIVER_NOTE.toString()))
            .andExpect(jsonPath("$.refundAmountByAlipay").value(DEFAULT_REFUND_AMOUNT_BY_ALIPAY.doubleValue()))
            .andExpect(jsonPath("$.refundAmountByComplaint").value(DEFAULT_REFUND_AMOUNT_BY_COMPLAINT.doubleValue()))
            .andExpect(jsonPath("$.refundAmountByOrder").value(DEFAULT_REFUND_AMOUNT_BY_ORDER.doubleValue()))
            .andExpect(jsonPath("$.refundAmountPending").value(DEFAULT_REFUND_AMOUNT_PENDING.doubleValue()))
            .andExpect(jsonPath("$.shippingChinaVietnamFee").value(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE.doubleValue()))
            .andExpect(jsonPath("$.shippingChinaVietnamFeeDiscount").value(DEFAULT_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.shopAliwang").value(DEFAULT_SHOP_ALIWANG.toString()))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID.toString()))
            .andExpect(jsonPath("$.shopLink").value(DEFAULT_SHOP_LINK.toString()))
            .andExpect(jsonPath("$.shopName").value(DEFAULT_SHOP_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.websiteCode").value(DEFAULT_WEBSITE_CODE.toString()))
            .andExpect(jsonPath("$.websiteLadingCode").value(DEFAULT_WEBSITE_LADING_CODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.statusStyle").value(DEFAULT_STATUS_STYLE.toString()))
            .andExpect(jsonPath("$.tallyFee").value(DEFAULT_TALLY_FEE))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmountNDT").value(DEFAULT_TOTAL_AMOUNT_NDT.doubleValue()))
            .andExpect(jsonPath("$.totalPaidByCustomer").value(DEFAULT_TOTAL_PAID_BY_CUSTOMER.doubleValue()))
            .andExpect(jsonPath("$.serviceFee").value(DEFAULT_SERVICE_FEE.doubleValue()))
            .andExpect(jsonPath("$.serviceFeeDiscount").value(DEFAULT_SERVICE_FEE_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalServiceFee").value(DEFAULT_TOTAL_SERVICE_FEE.doubleValue()))
            .andExpect(jsonPath("$.finalAmount").value(DEFAULT_FINAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
            .andExpect(jsonPath("$.updateAt").value(DEFAULT_UPDATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderCart() throws Exception {
        // Get the orderCart
        restOrderCartMockMvc.perform(get("/api/order-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderCart() throws Exception {
        // Initialize the database
        orderCartRepository.saveAndFlush(orderCart);

        int databaseSizeBeforeUpdate = orderCartRepository.findAll().size();

        // Update the orderCart
        OrderCart updatedOrderCart = orderCartRepository.findById(orderCart.getId()).get();
        // Disconnect from session so that the updates on updatedOrderCart are not directly saved in db
        em.detach(updatedOrderCart);
        updatedOrderCart
            .code(UPDATED_CODE)
            .avatar(UPDATED_AVATAR)
            .amountDiscount(UPDATED_AMOUNT_DISCOUNT)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .depositAmount(UPDATED_DEPOSIT_AMOUNT)
            .depositRatio(UPDATED_DEPOSIT_RATIO)
            .depositTime(UPDATED_DEPOSIT_TIME)
            .domesticShippingChinaFeeNDT(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE_NDT)
            .domesticShippingChinaFee(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE)
            .domesticShippingVietnamFee(UPDATED_DOMESTIC_SHIPPING_VIETNAM_FEE)
            .quantityOrder(UPDATED_QUANTITY_ORDER)
            .quantityPending(UPDATED_QUANTITY_PENDING)
            .quantityReceived(UPDATED_QUANTITY_RECEIVED)
            .rate(UPDATED_RATE)
            .receiverName(UPDATED_RECEIVER_NAME)
            .receiverAddress(UPDATED_RECEIVER_ADDRESS)
            .receiverMobile(UPDATED_RECEIVER_MOBILE)
            .receiverNote(UPDATED_RECEIVER_NOTE)
            .refundAmountByAlipay(UPDATED_REFUND_AMOUNT_BY_ALIPAY)
            .refundAmountByComplaint(UPDATED_REFUND_AMOUNT_BY_COMPLAINT)
            .refundAmountByOrder(UPDATED_REFUND_AMOUNT_BY_ORDER)
            .refundAmountPending(UPDATED_REFUND_AMOUNT_PENDING)
            .shippingChinaVietnamFee(UPDATED_SHIPPING_CHINA_VIETNAM_FEE)
            .shippingChinaVietnamFeeDiscount(UPDATED_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT)
            .shopAliwang(UPDATED_SHOP_ALIWANG)
            .shopId(UPDATED_SHOP_ID)
            .shopLink(UPDATED_SHOP_LINK)
            .shopName(UPDATED_SHOP_NAME)
            .website(UPDATED_WEBSITE)
            .websiteCode(UPDATED_WEBSITE_CODE)
            .websiteLadingCode(UPDATED_WEBSITE_LADING_CODE)
            .status(UPDATED_STATUS)
            .statusName(UPDATED_STATUS_NAME)
            .statusStyle(UPDATED_STATUS_STYLE)
            .tallyFee(UPDATED_TALLY_FEE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountNDT(UPDATED_TOTAL_AMOUNT_NDT)
            .totalPaidByCustomer(UPDATED_TOTAL_PAID_BY_CUSTOMER)
            .serviceFee(UPDATED_SERVICE_FEE)
            .serviceFeeDiscount(UPDATED_SERVICE_FEE_DISCOUNT)
            .totalServiceFee(UPDATED_TOTAL_SERVICE_FEE)
            .finalAmount(UPDATED_FINAL_AMOUNT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        OrderCartDTO orderCartDTO = orderCartMapper.toDto(updatedOrderCart);

        restOrderCartMockMvc.perform(put("/api/order-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCartDTO)))
            .andExpect(status().isOk());

        // Validate the OrderCart in the database
        List<OrderCart> orderCartList = orderCartRepository.findAll();
        assertThat(orderCartList).hasSize(databaseSizeBeforeUpdate);
        OrderCart testOrderCart = orderCartList.get(orderCartList.size() - 1);
        assertThat(testOrderCart.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrderCart.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testOrderCart.getAmountDiscount()).isEqualTo(UPDATED_AMOUNT_DISCOUNT);
        assertThat(testOrderCart.getAmountPaid()).isEqualTo(UPDATED_AMOUNT_PAID);
        assertThat(testOrderCart.getDepositAmount()).isEqualTo(UPDATED_DEPOSIT_AMOUNT);
        assertThat(testOrderCart.getDepositRatio()).isEqualTo(UPDATED_DEPOSIT_RATIO);
        assertThat(testOrderCart.getDepositTime()).isEqualTo(UPDATED_DEPOSIT_TIME);
        assertThat(testOrderCart.getDomesticShippingChinaFeeNDT()).isEqualTo(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE_NDT);
        assertThat(testOrderCart.getDomesticShippingChinaFee()).isEqualTo(UPDATED_DOMESTIC_SHIPPING_CHINA_FEE);
        assertThat(testOrderCart.getDomesticShippingVietnamFee()).isEqualTo(UPDATED_DOMESTIC_SHIPPING_VIETNAM_FEE);
        assertThat(testOrderCart.getQuantityOrder()).isEqualTo(UPDATED_QUANTITY_ORDER);
        assertThat(testOrderCart.getQuantityPending()).isEqualTo(UPDATED_QUANTITY_PENDING);
        assertThat(testOrderCart.getQuantityReceived()).isEqualTo(UPDATED_QUANTITY_RECEIVED);
        assertThat(testOrderCart.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testOrderCart.getReceiverName()).isEqualTo(UPDATED_RECEIVER_NAME);
        assertThat(testOrderCart.getReceiverAddress()).isEqualTo(UPDATED_RECEIVER_ADDRESS);
        assertThat(testOrderCart.getReceiverMobile()).isEqualTo(UPDATED_RECEIVER_MOBILE);
        assertThat(testOrderCart.getReceiverNote()).isEqualTo(UPDATED_RECEIVER_NOTE);
        assertThat(testOrderCart.getRefundAmountByAlipay()).isEqualTo(UPDATED_REFUND_AMOUNT_BY_ALIPAY);
        assertThat(testOrderCart.getRefundAmountByComplaint()).isEqualTo(UPDATED_REFUND_AMOUNT_BY_COMPLAINT);
        assertThat(testOrderCart.getRefundAmountByOrder()).isEqualTo(UPDATED_REFUND_AMOUNT_BY_ORDER);
        assertThat(testOrderCart.getRefundAmountPending()).isEqualTo(UPDATED_REFUND_AMOUNT_PENDING);
        assertThat(testOrderCart.getShippingChinaVietnamFee()).isEqualTo(UPDATED_SHIPPING_CHINA_VIETNAM_FEE);
        assertThat(testOrderCart.getShippingChinaVietnamFeeDiscount()).isEqualTo(UPDATED_SHIPPING_CHINA_VIETNAM_FEE_DISCOUNT);
        assertThat(testOrderCart.getShopAliwang()).isEqualTo(UPDATED_SHOP_ALIWANG);
        assertThat(testOrderCart.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testOrderCart.getShopLink()).isEqualTo(UPDATED_SHOP_LINK);
        assertThat(testOrderCart.getShopName()).isEqualTo(UPDATED_SHOP_NAME);
        assertThat(testOrderCart.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrderCart.getWebsiteCode()).isEqualTo(UPDATED_WEBSITE_CODE);
        assertThat(testOrderCart.getWebsiteLadingCode()).isEqualTo(UPDATED_WEBSITE_LADING_CODE);
        assertThat(testOrderCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrderCart.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
        assertThat(testOrderCart.getStatusStyle()).isEqualTo(UPDATED_STATUS_STYLE);
        assertThat(testOrderCart.getTallyFee()).isEqualTo(UPDATED_TALLY_FEE);
        assertThat(testOrderCart.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testOrderCart.getTotalAmountNDT()).isEqualTo(UPDATED_TOTAL_AMOUNT_NDT);
        assertThat(testOrderCart.getTotalPaidByCustomer()).isEqualTo(UPDATED_TOTAL_PAID_BY_CUSTOMER);
        assertThat(testOrderCart.getServiceFee()).isEqualTo(UPDATED_SERVICE_FEE);
        assertThat(testOrderCart.getServiceFeeDiscount()).isEqualTo(UPDATED_SERVICE_FEE_DISCOUNT);
        assertThat(testOrderCart.getTotalServiceFee()).isEqualTo(UPDATED_TOTAL_SERVICE_FEE);
        assertThat(testOrderCart.getFinalAmount()).isEqualTo(UPDATED_FINAL_AMOUNT);
        assertThat(testOrderCart.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testOrderCart.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderCart() throws Exception {
        int databaseSizeBeforeUpdate = orderCartRepository.findAll().size();

        // Create the OrderCart
        OrderCartDTO orderCartDTO = orderCartMapper.toDto(orderCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderCartMockMvc.perform(put("/api/order-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderCart in the database
        List<OrderCart> orderCartList = orderCartRepository.findAll();
        assertThat(orderCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderCart() throws Exception {
        // Initialize the database
        orderCartRepository.saveAndFlush(orderCart);

        int databaseSizeBeforeDelete = orderCartRepository.findAll().size();

        // Delete the orderCart
        restOrderCartMockMvc.perform(delete("/api/order-carts/{id}", orderCart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderCart> orderCartList = orderCartRepository.findAll();
        assertThat(orderCartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCart.class);
        OrderCart orderCart1 = new OrderCart();
        orderCart1.setId(1L);
        OrderCart orderCart2 = new OrderCart();
        orderCart2.setId(orderCart1.getId());
        assertThat(orderCart1).isEqualTo(orderCart2);
        orderCart2.setId(2L);
        assertThat(orderCart1).isNotEqualTo(orderCart2);
        orderCart1.setId(null);
        assertThat(orderCart1).isNotEqualTo(orderCart2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCartDTO.class);
        OrderCartDTO orderCartDTO1 = new OrderCartDTO();
        orderCartDTO1.setId(1L);
        OrderCartDTO orderCartDTO2 = new OrderCartDTO();
        assertThat(orderCartDTO1).isNotEqualTo(orderCartDTO2);
        orderCartDTO2.setId(orderCartDTO1.getId());
        assertThat(orderCartDTO1).isEqualTo(orderCartDTO2);
        orderCartDTO2.setId(2L);
        assertThat(orderCartDTO1).isNotEqualTo(orderCartDTO2);
        orderCartDTO1.setId(null);
        assertThat(orderCartDTO1).isNotEqualTo(orderCartDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderCartMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderCartMapper.fromId(null)).isNull();
    }
}
