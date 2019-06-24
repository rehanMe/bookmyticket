package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.BookingappApp;
import com.demo.bookingapp.domain.Bus;
import com.demo.bookingapp.repository.BusRepository;
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
 * Integration tests for the {@Link BusResource} REST controller.
 */
@SpringBootTest(classes = BookingappApp.class)
public class BusResourceIT {

    private static final String DEFAULT_BUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUS_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_B_FARE = 1;
    private static final Integer UPDATED_B_FARE = 2;

    private static final String DEFAULT_B_TIMING = "AAAAAAAAAA";
    private static final String UPDATED_B_TIMING = "BBBBBBBBBB";

    @Autowired
    private BusRepository busRepository;

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

    private MockMvc restBusMockMvc;

    private Bus bus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusResource busResource = new BusResource(busRepository);
        this.restBusMockMvc = MockMvcBuilders.standaloneSetup(busResource)
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
    public static Bus createEntity(EntityManager em) {
        Bus bus = new Bus()
            .busName(DEFAULT_BUS_NAME)
            .busNumber(DEFAULT_BUS_NUMBER)
            .bFare(DEFAULT_B_FARE)
            .bTiming(DEFAULT_B_TIMING);
        return bus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bus createUpdatedEntity(EntityManager em) {
        Bus bus = new Bus()
            .busName(UPDATED_BUS_NAME)
            .busNumber(UPDATED_BUS_NUMBER)
            .bFare(UPDATED_B_FARE)
            .bTiming(UPDATED_B_TIMING);
        return bus;
    }

    @BeforeEach
    public void initTest() {
        bus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBus() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus
        restBusMockMvc.perform(post("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isCreated());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate + 1);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getBusName()).isEqualTo(DEFAULT_BUS_NAME);
        assertThat(testBus.getBusNumber()).isEqualTo(DEFAULT_BUS_NUMBER);
        assertThat(testBus.getbFare()).isEqualTo(DEFAULT_B_FARE);
        assertThat(testBus.getbTiming()).isEqualTo(DEFAULT_B_TIMING);
    }

    @Test
    @Transactional
    public void createBusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = busRepository.findAll().size();

        // Create the Bus with an existing ID
        bus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusMockMvc.perform(post("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBuses() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get all the busList
        restBusMockMvc.perform(get("/api/buses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bus.getId().intValue())))
            .andExpect(jsonPath("$.[*].busName").value(hasItem(DEFAULT_BUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].busNumber").value(hasItem(DEFAULT_BUS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bFare").value(hasItem(DEFAULT_B_FARE)))
            .andExpect(jsonPath("$.[*].bTiming").value(hasItem(DEFAULT_B_TIMING.toString())));
    }
    
    @Test
    @Transactional
    public void getBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", bus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bus.getId().intValue()))
            .andExpect(jsonPath("$.busName").value(DEFAULT_BUS_NAME.toString()))
            .andExpect(jsonPath("$.busNumber").value(DEFAULT_BUS_NUMBER.toString()))
            .andExpect(jsonPath("$.bFare").value(DEFAULT_B_FARE))
            .andExpect(jsonPath("$.bTiming").value(DEFAULT_B_TIMING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBus() throws Exception {
        // Get the bus
        restBusMockMvc.perform(get("/api/buses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Update the bus
        Bus updatedBus = busRepository.findById(bus.getId()).get();
        // Disconnect from session so that the updates on updatedBus are not directly saved in db
        em.detach(updatedBus);
        updatedBus
            .busName(UPDATED_BUS_NAME)
            .busNumber(UPDATED_BUS_NUMBER)
            .bFare(UPDATED_B_FARE)
            .bTiming(UPDATED_B_TIMING);

        restBusMockMvc.perform(put("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBus)))
            .andExpect(status().isOk());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
        Bus testBus = busList.get(busList.size() - 1);
        assertThat(testBus.getBusName()).isEqualTo(UPDATED_BUS_NAME);
        assertThat(testBus.getBusNumber()).isEqualTo(UPDATED_BUS_NUMBER);
        assertThat(testBus.getbFare()).isEqualTo(UPDATED_B_FARE);
        assertThat(testBus.getbTiming()).isEqualTo(UPDATED_B_TIMING);
    }

    @Test
    @Transactional
    public void updateNonExistingBus() throws Exception {
        int databaseSizeBeforeUpdate = busRepository.findAll().size();

        // Create the Bus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusMockMvc.perform(put("/api/buses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bus)))
            .andExpect(status().isBadRequest());

        // Validate the Bus in the database
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBus() throws Exception {
        // Initialize the database
        busRepository.saveAndFlush(bus);

        int databaseSizeBeforeDelete = busRepository.findAll().size();

        // Delete the bus
        restBusMockMvc.perform(delete("/api/buses/{id}", bus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bus> busList = busRepository.findAll();
        assertThat(busList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bus.class);
        Bus bus1 = new Bus();
        bus1.setId(1L);
        Bus bus2 = new Bus();
        bus2.setId(bus1.getId());
        assertThat(bus1).isEqualTo(bus2);
        bus2.setId(2L);
        assertThat(bus1).isNotEqualTo(bus2);
        bus1.setId(null);
        assertThat(bus1).isNotEqualTo(bus2);
    }
}
