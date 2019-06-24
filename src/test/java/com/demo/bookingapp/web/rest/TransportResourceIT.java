package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.BookingappApp;
import com.demo.bookingapp.domain.Transport;
import com.demo.bookingapp.repository.TransportRepository;
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

import com.demo.bookingapp.domain.enumeration.TransportType;
import com.demo.bookingapp.domain.enumeration.WeekDays;
/**
 * Integration tests for the {@Link TransportResource} REST controller.
 */
@SpringBootTest(classes = BookingappApp.class)
public class TransportResourceIT {

    private static final TransportType DEFAULT_TRANSPORT_TYPE = TransportType.Roadways;
    private static final TransportType UPDATED_TRANSPORT_TYPE = TransportType.Railways;

    private static final String DEFAULT_SERVICE_PROVIDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PROVIDER_NAME = "BBBBBBBBBB";

    private static final WeekDays DEFAULT_AVAILABILITY = WeekDays.SUNDAY;
    private static final WeekDays UPDATED_AVAILABILITY = WeekDays.MONDAY;

    @Autowired
    private TransportRepository transportRepository;

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

    private MockMvc restTransportMockMvc;

    private Transport transport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransportResource transportResource = new TransportResource(transportRepository);
        this.restTransportMockMvc = MockMvcBuilders.standaloneSetup(transportResource)
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
    public static Transport createEntity(EntityManager em) {
        Transport transport = new Transport()
            .transportType(DEFAULT_TRANSPORT_TYPE)
            .serviceProviderName(DEFAULT_SERVICE_PROVIDER_NAME)
            .availability(DEFAULT_AVAILABILITY);
        return transport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transport createUpdatedEntity(EntityManager em) {
        Transport transport = new Transport()
            .transportType(UPDATED_TRANSPORT_TYPE)
            .serviceProviderName(UPDATED_SERVICE_PROVIDER_NAME)
            .availability(UPDATED_AVAILABILITY);
        return transport;
    }

    @BeforeEach
    public void initTest() {
        transport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransport() throws Exception {
        int databaseSizeBeforeCreate = transportRepository.findAll().size();

        // Create the Transport
        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transport)))
            .andExpect(status().isCreated());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeCreate + 1);
        Transport testTransport = transportList.get(transportList.size() - 1);
        assertThat(testTransport.getTransportType()).isEqualTo(DEFAULT_TRANSPORT_TYPE);
        assertThat(testTransport.getServiceProviderName()).isEqualTo(DEFAULT_SERVICE_PROVIDER_NAME);
        assertThat(testTransport.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
    }

    @Test
    @Transactional
    public void createTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transportRepository.findAll().size();

        // Create the Transport with an existing ID
        transport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transport)))
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransportTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportRepository.findAll().size();
        // set the field null
        transport.setTransportType(null);

        // Create the Transport, which fails.

        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transport)))
            .andExpect(status().isBadRequest());

        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceProviderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transportRepository.findAll().size();
        // set the field null
        transport.setServiceProviderName(null);

        // Create the Transport, which fails.

        restTransportMockMvc.perform(post("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transport)))
            .andExpect(status().isBadRequest());

        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransports() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get all the transportList
        restTransportMockMvc.perform(get("/api/transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transport.getId().intValue())))
            .andExpect(jsonPath("$.[*].transportType").value(hasItem(DEFAULT_TRANSPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].serviceProviderName").value(hasItem(DEFAULT_SERVICE_PROVIDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.toString())));
    }
    
    @Test
    @Transactional
    public void getTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        // Get the transport
        restTransportMockMvc.perform(get("/api/transports/{id}", transport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transport.getId().intValue()))
            .andExpect(jsonPath("$.transportType").value(DEFAULT_TRANSPORT_TYPE.toString()))
            .andExpect(jsonPath("$.serviceProviderName").value(DEFAULT_SERVICE_PROVIDER_NAME.toString()))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransport() throws Exception {
        // Get the transport
        restTransportMockMvc.perform(get("/api/transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        int databaseSizeBeforeUpdate = transportRepository.findAll().size();

        // Update the transport
        Transport updatedTransport = transportRepository.findById(transport.getId()).get();
        // Disconnect from session so that the updates on updatedTransport are not directly saved in db
        em.detach(updatedTransport);
        updatedTransport
            .transportType(UPDATED_TRANSPORT_TYPE)
            .serviceProviderName(UPDATED_SERVICE_PROVIDER_NAME)
            .availability(UPDATED_AVAILABILITY);

        restTransportMockMvc.perform(put("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransport)))
            .andExpect(status().isOk());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeUpdate);
        Transport testTransport = transportList.get(transportList.size() - 1);
        assertThat(testTransport.getTransportType()).isEqualTo(UPDATED_TRANSPORT_TYPE);
        assertThat(testTransport.getServiceProviderName()).isEqualTo(UPDATED_SERVICE_PROVIDER_NAME);
        assertThat(testTransport.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingTransport() throws Exception {
        int databaseSizeBeforeUpdate = transportRepository.findAll().size();

        // Create the Transport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransportMockMvc.perform(put("/api/transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transport)))
            .andExpect(status().isBadRequest());

        // Validate the Transport in the database
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransport() throws Exception {
        // Initialize the database
        transportRepository.saveAndFlush(transport);

        int databaseSizeBeforeDelete = transportRepository.findAll().size();

        // Delete the transport
        restTransportMockMvc.perform(delete("/api/transports/{id}", transport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transport> transportList = transportRepository.findAll();
        assertThat(transportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transport.class);
        Transport transport1 = new Transport();
        transport1.setId(1L);
        Transport transport2 = new Transport();
        transport2.setId(transport1.getId());
        assertThat(transport1).isEqualTo(transport2);
        transport2.setId(2L);
        assertThat(transport1).isNotEqualTo(transport2);
        transport1.setId(null);
        assertThat(transport1).isNotEqualTo(transport2);
    }
}
