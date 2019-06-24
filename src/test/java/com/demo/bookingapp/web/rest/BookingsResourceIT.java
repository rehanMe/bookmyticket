package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.BookingappApp;
import com.demo.bookingapp.domain.Bookings;
import com.demo.bookingapp.repository.BookingsRepository;
import com.demo.bookingapp.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.demo.bookingapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.demo.bookingapp.domain.enumeration.Status;
/**
 * Integration tests for the {@Link BookingsResource} REST controller.
 */
@SpringBootTest(classes = BookingappApp.class)
public class BookingsResourceIT {

    private static final String DEFAULT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VALID_THRU = "AAAAA";
    private static final String UPDATED_VALID_THRU = "BBBBB";

    private static final Integer DEFAULT_CVV = 1;
    private static final Integer UPDATED_CVV = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.CONFIRMED;
    private static final Status UPDATED_STATUS = Status.CANCELLED;

    @Autowired
    private BookingsRepository bookingsRepository;

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

    private MockMvc restBookingsMockMvc;

    private Bookings bookings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookingsResource bookingsResource = new BookingsResource(bookingsRepository);
        this.restBookingsMockMvc = MockMvcBuilders.standaloneSetup(bookingsResource)
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
    public static Bookings createEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .cardNo(DEFAULT_CARD_NO)
            .validThru(DEFAULT_VALID_THRU)
            .cvv(DEFAULT_CVV)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS);
        return bookings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookings createUpdatedEntity(EntityManager em) {
        Bookings bookings = new Bookings()
            .cardNo(UPDATED_CARD_NO)
            .validThru(UPDATED_VALID_THRU)
            .cvv(UPDATED_CVV)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);
        return bookings;
    }

    @BeforeEach
    public void initTest() {
        bookings = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookings() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isCreated());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate + 1);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getCardNo()).isEqualTo(DEFAULT_CARD_NO);
        assertThat(testBookings.getValidThru()).isEqualTo(DEFAULT_VALID_THRU);
        assertThat(testBookings.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testBookings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBookings.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBookingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingsRepository.findAll().size();

        // Create the Bookings with an existing ID
        bookings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCardNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setCardNo(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidThruIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setValidThru(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvvIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingsRepository.findAll().size();
        // set the field null
        bookings.setCvv(null);

        // Create the Bookings, which fails.

        restBookingsMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get all the bookingsList
        restBookingsMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookings.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNo").value(hasItem(DEFAULT_CARD_NO.toString())))
            .andExpect(jsonPath("$.[*].validThru").value(hasItem(DEFAULT_VALID_THRU.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", bookings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookings.getId().intValue()))
            .andExpect(jsonPath("$.cardNo").value(DEFAULT_CARD_NO.toString()))
            .andExpect(jsonPath("$.validThru").value(DEFAULT_VALID_THRU.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookings() throws Exception {
        // Get the bookings
        restBookingsMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Update the bookings
        Bookings updatedBookings = bookingsRepository.findById(bookings.getId()).get();
        // Disconnect from session so that the updates on updatedBookings are not directly saved in db
        em.detach(updatedBookings);
        updatedBookings
            .cardNo(UPDATED_CARD_NO)
            .validThru(UPDATED_VALID_THRU)
            .cvv(UPDATED_CVV)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS);

        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookings)))
            .andExpect(status().isOk());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
        Bookings testBookings = bookingsList.get(bookingsList.size() - 1);
        assertThat(testBookings.getCardNo()).isEqualTo(UPDATED_CARD_NO);
        assertThat(testBookings.getValidThru()).isEqualTo(UPDATED_VALID_THRU);
        assertThat(testBookings.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testBookings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBookings.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookings() throws Exception {
        int databaseSizeBeforeUpdate = bookingsRepository.findAll().size();

        // Create the Bookings

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingsMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookings)))
            .andExpect(status().isBadRequest());

        // Validate the Bookings in the database
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookings() throws Exception {
        // Initialize the database
        bookingsRepository.saveAndFlush(bookings);

        int databaseSizeBeforeDelete = bookingsRepository.findAll().size();

        // Delete the bookings
        restBookingsMockMvc.perform(delete("/api/bookings/{id}", bookings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookings> bookingsList = bookingsRepository.findAll();
        assertThat(bookingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookings.class);
        Bookings bookings1 = new Bookings();
        bookings1.setId(1L);
        Bookings bookings2 = new Bookings();
        bookings2.setId(bookings1.getId());
        assertThat(bookings1).isEqualTo(bookings2);
        bookings2.setId(2L);
        assertThat(bookings1).isNotEqualTo(bookings2);
        bookings1.setId(null);
        assertThat(bookings1).isNotEqualTo(bookings2);
    }
}
