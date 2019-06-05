package com.dropshipping.web.rest;

import com.dropshipping.DropshippingApp;
import com.dropshipping.domain.OrderComment;
import com.dropshipping.repository.OrderCommentRepository;
import com.dropshipping.service.OrderCommentService;
import com.dropshipping.service.dto.OrderCommentDTO;
import com.dropshipping.service.mapper.OrderCommentMapper;
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

import com.dropshipping.domain.enumeration.CommentType;
/**
 * Integration tests for the {@Link OrderCommentResource} REST controller.
 */
@SpringBootTest(classes = DropshippingApp.class)
public class OrderCommentResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_SENDER = "AAAAAAAAAA";
    private static final String UPDATED_SENDER = "BBBBBBBBBB";

    private static final CommentType DEFAULT_TYPE = CommentType.CUSTOMER_LOG;
    private static final CommentType UPDATED_TYPE = CommentType.CUSTOMER_CHAT;

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderCommentRepository orderCommentRepository;

    @Autowired
    private OrderCommentMapper orderCommentMapper;

    @Autowired
    private OrderCommentService orderCommentService;

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

    private MockMvc restOrderCommentMockMvc;

    private OrderComment orderComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderCommentResource orderCommentResource = new OrderCommentResource(orderCommentService);
        this.restOrderCommentMockMvc = MockMvcBuilders.standaloneSetup(orderCommentResource)
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
    public static OrderComment createEntity(EntityManager em) {
        OrderComment orderComment = new OrderComment()
            .message(DEFAULT_MESSAGE)
            .sender(DEFAULT_SENDER)
            .type(DEFAULT_TYPE)
            .createAt(DEFAULT_CREATE_AT);
        return orderComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderComment createUpdatedEntity(EntityManager em) {
        OrderComment orderComment = new OrderComment()
            .message(UPDATED_MESSAGE)
            .sender(UPDATED_SENDER)
            .type(UPDATED_TYPE)
            .createAt(UPDATED_CREATE_AT);
        return orderComment;
    }

    @BeforeEach
    public void initTest() {
        orderComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderComment() throws Exception {
        int databaseSizeBeforeCreate = orderCommentRepository.findAll().size();

        // Create the OrderComment
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);
        restOrderCommentMockMvc.perform(post("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeCreate + 1);
        OrderComment testOrderComment = orderCommentList.get(orderCommentList.size() - 1);
        assertThat(testOrderComment.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testOrderComment.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testOrderComment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrderComment.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    public void createOrderCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderCommentRepository.findAll().size();

        // Create the OrderComment with an existing ID
        orderComment.setId(1L);
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderCommentMockMvc.perform(post("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderComments() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        // Get all the orderCommentList
        restOrderCommentMockMvc.perform(get("/api/order-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        // Get the orderComment
        restOrderCommentMockMvc.perform(get("/api/order-comments/{id}", orderComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderComment.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderComment() throws Exception {
        // Get the orderComment
        restOrderCommentMockMvc.perform(get("/api/order-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        int databaseSizeBeforeUpdate = orderCommentRepository.findAll().size();

        // Update the orderComment
        OrderComment updatedOrderComment = orderCommentRepository.findById(orderComment.getId()).get();
        // Disconnect from session so that the updates on updatedOrderComment are not directly saved in db
        em.detach(updatedOrderComment);
        updatedOrderComment
            .message(UPDATED_MESSAGE)
            .sender(UPDATED_SENDER)
            .type(UPDATED_TYPE)
            .createAt(UPDATED_CREATE_AT);
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(updatedOrderComment);

        restOrderCommentMockMvc.perform(put("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isOk());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeUpdate);
        OrderComment testOrderComment = orderCommentList.get(orderCommentList.size() - 1);
        assertThat(testOrderComment.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testOrderComment.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testOrderComment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrderComment.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderComment() throws Exception {
        int databaseSizeBeforeUpdate = orderCommentRepository.findAll().size();

        // Create the OrderComment
        OrderCommentDTO orderCommentDTO = orderCommentMapper.toDto(orderComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderCommentMockMvc.perform(put("/api/order-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderComment in the database
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderComment() throws Exception {
        // Initialize the database
        orderCommentRepository.saveAndFlush(orderComment);

        int databaseSizeBeforeDelete = orderCommentRepository.findAll().size();

        // Delete the orderComment
        restOrderCommentMockMvc.perform(delete("/api/order-comments/{id}", orderComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OrderComment> orderCommentList = orderCommentRepository.findAll();
        assertThat(orderCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderComment.class);
        OrderComment orderComment1 = new OrderComment();
        orderComment1.setId(1L);
        OrderComment orderComment2 = new OrderComment();
        orderComment2.setId(orderComment1.getId());
        assertThat(orderComment1).isEqualTo(orderComment2);
        orderComment2.setId(2L);
        assertThat(orderComment1).isNotEqualTo(orderComment2);
        orderComment1.setId(null);
        assertThat(orderComment1).isNotEqualTo(orderComment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCommentDTO.class);
        OrderCommentDTO orderCommentDTO1 = new OrderCommentDTO();
        orderCommentDTO1.setId(1L);
        OrderCommentDTO orderCommentDTO2 = new OrderCommentDTO();
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
        orderCommentDTO2.setId(orderCommentDTO1.getId());
        assertThat(orderCommentDTO1).isEqualTo(orderCommentDTO2);
        orderCommentDTO2.setId(2L);
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
        orderCommentDTO1.setId(null);
        assertThat(orderCommentDTO1).isNotEqualTo(orderCommentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderCommentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderCommentMapper.fromId(null)).isNull();
    }
}
