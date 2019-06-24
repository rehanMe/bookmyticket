package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.BookingappApp;
import com.demo.bookingapp.domain.Airplane;
import com.demo.bookingapp.repository.AirplaneRepository;
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

/**
 * Integration tests for the {@Link AirplaneResource} REST controller.
 */
@SpringBootTest(classes = BookingappApp.class)
public class AirplaneResourceIT {

    private static final String DEFAULT_FLIGHT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_F_FARE = 1;
    private static final Integer UPDATED_F_FARE = 2;

    private static final String DEFAULT_F_TIMING = "AAAAAAAAAA";
    private static final String UPDATED_F_TIMING = "BBBBBBBBBB";

    @Autowired
    private AirplaneRepository airplaneRepository;

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

    private MockMvc restAirplaneMockMvc;

    private Airplane airplane;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AirplaneResource airplaneResource = new AirplaneResource(airplaneRepository);
        this.restAirplaneMockMvc = MockMvcBuilders.standaloneSetup(airplaneResource)
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
    public static Airplane createEntity(EntityManager em) {
        Airplane airplane = new Airplane()
            .flightName(DEFAULT_FLIGHT_NAME)
            .flightNumber(DEFAULT_FLIGHT_NUMBER)
            .fFare(DEFAULT_F_FARE)
            .fTiming(DEFAULT_F_TIMING);
        return airplane;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airplane createUpdatedEntity(EntityManager em) {
        Airplane airplane = new Airplane()
            .flightName(UPDATED_FLIGHT_NAME)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .fFare(UPDATED_F_FARE)
            .fTiming(UPDATED_F_TIMING);
        return airplane;
    }

    @BeforeEach
    public void initTest() {
        airplane = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirplane() throws Exception {
        int databaseSizeBeforeCreate = airplaneRepository.findAll().size();

        // Create the Airplane
        restAirplaneMockMvc.perform(post("/api/airplanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isCreated());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeCreate + 1);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getFlightName()).isEqualTo(DEFAULT_FLIGHT_NAME);
        assertThat(testAirplane.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testAirplane.getfFare()).isEqualTo(DEFAULT_F_FARE);
        assertThat(testAirplane.getfTiming()).isEqualTo(DEFAULT_F_TIMING);
    }

    @Test
    @Transactional
    public void createAirplaneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airplaneRepository.findAll().size();

        // Create the Airplane with an existing ID
        airplane.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirplaneMockMvc.perform(post("/api/airplanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAirplanes() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        // Get all the airplaneList
        restAirplaneMockMvc.perform(get("/api/airplanes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airplane.getId().intValue())))
            .andExpect(jsonPath("$.[*].flightName").value(hasItem(DEFAULT_FLIGHT_NAME.toString())))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].fFare").value(hasItem(DEFAULT_F_FARE)))
            .andExpect(jsonPath("$.[*].fTiming").value(hasItem(DEFAULT_F_TIMING.toString())));
    }
    
    @Test
    @Transactional
    public void getAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        // Get the airplane
        restAirplaneMockMvc.perform(get("/api/airplanes/{id}", airplane.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airplane.getId().intValue()))
            .andExpect(jsonPath("$.flightName").value(DEFAULT_FLIGHT_NAME.toString()))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.fFare").value(DEFAULT_F_FARE))
            .andExpect(jsonPath("$.fTiming").value(DEFAULT_F_TIMING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAirplane() throws Exception {
        // Get the airplane
        restAirplaneMockMvc.perform(get("/api/airplanes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();

        // Update the airplane
        Airplane updatedAirplane = airplaneRepository.findById(airplane.getId()).get();
        // Disconnect from session so that the updates on updatedAirplane are not directly saved in db
        em.detach(updatedAirplane);
        updatedAirplane
            .flightName(UPDATED_FLIGHT_NAME)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .fFare(UPDATED_F_FARE)
            .fTiming(UPDATED_F_TIMING);

        restAirplaneMockMvc.perform(put("/api/airplanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirplane)))
            .andExpect(status().isOk());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getFlightName()).isEqualTo(UPDATED_FLIGHT_NAME);
        assertThat(testAirplane.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testAirplane.getfFare()).isEqualTo(UPDATED_F_FARE);
        assertThat(testAirplane.getfTiming()).isEqualTo(UPDATED_F_TIMING);
    }

    @Test
    @Transactional
    public void updateNonExistingAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();

        // Create the Airplane

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplaneMockMvc.perform(put("/api/airplanes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeDelete = airplaneRepository.findAll().size();

        // Delete the airplane
        restAirplaneMockMvc.perform(delete("/api/airplanes/{id}", airplane.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Airplane.class);
        Airplane airplane1 = new Airplane();
        airplane1.setId(1L);
        Airplane airplane2 = new Airplane();
        airplane2.setId(airplane1.getId());
        assertThat(airplane1).isEqualTo(airplane2);
        airplane2.setId(2L);
        assertThat(airplane1).isNotEqualTo(airplane2);
        airplane1.setId(null);
        assertThat(airplane1).isNotEqualTo(airplane2);
    }
}
