package com.demo.bookingapp.web.rest;

import com.demo.bookingapp.BookingappApp;
import com.demo.bookingapp.domain.Train;
import com.demo.bookingapp.repository.TrainRepository;
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
 * Integration tests for the {@Link TrainResource} REST controller.
 */
@SpringBootTest(classes = BookingappApp.class)
public class TrainResourceIT {

    private static final String DEFAULT_TRAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRAIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRAIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRAIN_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_T_FARE = 1;
    private static final Integer UPDATED_T_FARE = 2;

    private static final String DEFAULT_T_TIMING = "AAAAAAAAAA";
    private static final String UPDATED_T_TIMING = "BBBBBBBBBB";

    @Autowired
    private TrainRepository trainRepository;

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

    private MockMvc restTrainMockMvc;

    private Train train;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainResource trainResource = new TrainResource(trainRepository);
        this.restTrainMockMvc = MockMvcBuilders.standaloneSetup(trainResource)
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
    public static Train createEntity(EntityManager em) {
        Train train = new Train()
            .trainName(DEFAULT_TRAIN_NAME)
            .trainNumber(DEFAULT_TRAIN_NUMBER)
            .tFare(DEFAULT_T_FARE)
            .tTiming(DEFAULT_T_TIMING);
        return train;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Train createUpdatedEntity(EntityManager em) {
        Train train = new Train()
            .trainName(UPDATED_TRAIN_NAME)
            .trainNumber(UPDATED_TRAIN_NUMBER)
            .tFare(UPDATED_T_FARE)
            .tTiming(UPDATED_T_TIMING);
        return train;
    }

    @BeforeEach
    public void initTest() {
        train = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrain() throws Exception {
        int databaseSizeBeforeCreate = trainRepository.findAll().size();

        // Create the Train
        restTrainMockMvc.perform(post("/api/trains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isCreated());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate + 1);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTrainName()).isEqualTo(DEFAULT_TRAIN_NAME);
        assertThat(testTrain.getTrainNumber()).isEqualTo(DEFAULT_TRAIN_NUMBER);
        assertThat(testTrain.gettFare()).isEqualTo(DEFAULT_T_FARE);
        assertThat(testTrain.gettTiming()).isEqualTo(DEFAULT_T_TIMING);
    }

    @Test
    @Transactional
    public void createTrainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainRepository.findAll().size();

        // Create the Train with an existing ID
        train.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainMockMvc.perform(post("/api/trains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrains() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get all the trainList
        restTrainMockMvc.perform(get("/api/trains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(train.getId().intValue())))
            .andExpect(jsonPath("$.[*].trainName").value(hasItem(DEFAULT_TRAIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].trainNumber").value(hasItem(DEFAULT_TRAIN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tFare").value(hasItem(DEFAULT_T_FARE)))
            .andExpect(jsonPath("$.[*].tTiming").value(hasItem(DEFAULT_T_TIMING.toString())));
    }
    
    @Test
    @Transactional
    public void getTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get the train
        restTrainMockMvc.perform(get("/api/trains/{id}", train.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(train.getId().intValue()))
            .andExpect(jsonPath("$.trainName").value(DEFAULT_TRAIN_NAME.toString()))
            .andExpect(jsonPath("$.trainNumber").value(DEFAULT_TRAIN_NUMBER.toString()))
            .andExpect(jsonPath("$.tFare").value(DEFAULT_T_FARE))
            .andExpect(jsonPath("$.tTiming").value(DEFAULT_T_TIMING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrain() throws Exception {
        // Get the train
        restTrainMockMvc.perform(get("/api/trains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train
        Train updatedTrain = trainRepository.findById(train.getId()).get();
        // Disconnect from session so that the updates on updatedTrain are not directly saved in db
        em.detach(updatedTrain);
        updatedTrain
            .trainName(UPDATED_TRAIN_NAME)
            .trainNumber(UPDATED_TRAIN_NUMBER)
            .tFare(UPDATED_T_FARE)
            .tTiming(UPDATED_T_TIMING);

        restTrainMockMvc.perform(put("/api/trains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrain)))
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getTrainName()).isEqualTo(UPDATED_TRAIN_NAME);
        assertThat(testTrain.getTrainNumber()).isEqualTo(UPDATED_TRAIN_NUMBER);
        assertThat(testTrain.gettFare()).isEqualTo(UPDATED_T_FARE);
        assertThat(testTrain.gettTiming()).isEqualTo(UPDATED_T_TIMING);
    }

    @Test
    @Transactional
    public void updateNonExistingTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Create the Train

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainMockMvc.perform(put("/api/trains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(train)))
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeDelete = trainRepository.findAll().size();

        // Delete the train
        restTrainMockMvc.perform(delete("/api/trains/{id}", train.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Train.class);
        Train train1 = new Train();
        train1.setId(1L);
        Train train2 = new Train();
        train2.setId(train1.getId());
        assertThat(train1).isEqualTo(train2);
        train2.setId(2L);
        assertThat(train1).isNotEqualTo(train2);
        train1.setId(null);
        assertThat(train1).isNotEqualTo(train2);
    }
}
