package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Choice;
import ihotel.app.domain.Question;
import ihotel.app.repository.ChoiceRepository;
import ihotel.app.repository.search.ChoiceSearchRepository;
import ihotel.app.service.criteria.ChoiceCriteria;
import ihotel.app.service.dto.ChoiceDTO;
import ihotel.app.service.mapper.ChoiceMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChoiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChoiceResourceIT {

    private static final Instant DEFAULT_CREATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATEDAT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATEDAT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_VOTES = 1L;
    private static final Long UPDATED_VOTES = 2L;
    private static final Long SMALLER_VOTES = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/choices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/choices";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private ChoiceMapper choiceMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ChoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChoiceSearchRepository mockChoiceSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChoiceMockMvc;

    private Choice choice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choice createEntity(EntityManager em) {
        Choice choice = new Choice().createdat(DEFAULT_CREATEDAT).updatedat(DEFAULT_UPDATEDAT).text(DEFAULT_TEXT).votes(DEFAULT_VOTES);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        choice.setQuestion(question);
        return choice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choice createUpdatedEntity(EntityManager em) {
        Choice choice = new Choice().createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).text(UPDATED_TEXT).votes(UPDATED_VOTES);
        // Add required entity
        Question question;
        if (TestUtil.findAll(em, Question.class).isEmpty()) {
            question = QuestionResourceIT.createUpdatedEntity(em);
            em.persist(question);
            em.flush();
        } else {
            question = TestUtil.findAll(em, Question.class).get(0);
        }
        choice.setQuestion(question);
        return choice;
    }

    @BeforeEach
    public void initTest() {
        choice = createEntity(em);
    }

    @Test
    @Transactional
    void createChoice() throws Exception {
        int databaseSizeBeforeCreate = choiceRepository.findAll().size();
        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);
        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isCreated());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate + 1);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testChoice.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testChoice.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testChoice.getVotes()).isEqualTo(DEFAULT_VOTES);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(1)).save(testChoice);
    }

    @Test
    @Transactional
    void createChoiceWithExistingId() throws Exception {
        // Create the Choice with an existing ID
        choice.setId(1L);
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        int databaseSizeBeforeCreate = choiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void checkCreatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = choiceRepository.findAll().size();
        // set the field null
        choice.setCreatedat(null);

        // Create the Choice, which fails.
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isBadRequest());

        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedatIsRequired() throws Exception {
        int databaseSizeBeforeTest = choiceRepository.findAll().size();
        // set the field null
        choice.setUpdatedat(null);

        // Create the Choice, which fails.
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isBadRequest());

        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = choiceRepository.findAll().size();
        // set the field null
        choice.setText(null);

        // Create the Choice, which fails.
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isBadRequest());

        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = choiceRepository.findAll().size();
        // set the field null
        choice.setVotes(null);

        // Create the Choice, which fails.
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        restChoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isBadRequest());

        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChoices() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES.intValue())));
    }

    @Test
    @Transactional
    void getChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get the choice
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, choice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(choice.getId().intValue()))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.votes").value(DEFAULT_VOTES.intValue()));
    }

    @Test
    @Transactional
    void getChoicesByIdFiltering() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        Long id = choice.getId();

        defaultChoiceShouldBeFound("id.equals=" + id);
        defaultChoiceShouldNotBeFound("id.notEquals=" + id);

        defaultChoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultChoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChoiceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllChoicesByCreatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where createdat equals to DEFAULT_CREATEDAT
        defaultChoiceShouldBeFound("createdat.equals=" + DEFAULT_CREATEDAT);

        // Get all the choiceList where createdat equals to UPDATED_CREATEDAT
        defaultChoiceShouldNotBeFound("createdat.equals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByCreatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where createdat not equals to DEFAULT_CREATEDAT
        defaultChoiceShouldNotBeFound("createdat.notEquals=" + DEFAULT_CREATEDAT);

        // Get all the choiceList where createdat not equals to UPDATED_CREATEDAT
        defaultChoiceShouldBeFound("createdat.notEquals=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByCreatedatIsInShouldWork() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where createdat in DEFAULT_CREATEDAT or UPDATED_CREATEDAT
        defaultChoiceShouldBeFound("createdat.in=" + DEFAULT_CREATEDAT + "," + UPDATED_CREATEDAT);

        // Get all the choiceList where createdat equals to UPDATED_CREATEDAT
        defaultChoiceShouldNotBeFound("createdat.in=" + UPDATED_CREATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByCreatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where createdat is not null
        defaultChoiceShouldBeFound("createdat.specified=true");

        // Get all the choiceList where createdat is null
        defaultChoiceShouldNotBeFound("createdat.specified=false");
    }

    @Test
    @Transactional
    void getAllChoicesByUpdatedatIsEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where updatedat equals to DEFAULT_UPDATEDAT
        defaultChoiceShouldBeFound("updatedat.equals=" + DEFAULT_UPDATEDAT);

        // Get all the choiceList where updatedat equals to UPDATED_UPDATEDAT
        defaultChoiceShouldNotBeFound("updatedat.equals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByUpdatedatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where updatedat not equals to DEFAULT_UPDATEDAT
        defaultChoiceShouldNotBeFound("updatedat.notEquals=" + DEFAULT_UPDATEDAT);

        // Get all the choiceList where updatedat not equals to UPDATED_UPDATEDAT
        defaultChoiceShouldBeFound("updatedat.notEquals=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByUpdatedatIsInShouldWork() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where updatedat in DEFAULT_UPDATEDAT or UPDATED_UPDATEDAT
        defaultChoiceShouldBeFound("updatedat.in=" + DEFAULT_UPDATEDAT + "," + UPDATED_UPDATEDAT);

        // Get all the choiceList where updatedat equals to UPDATED_UPDATEDAT
        defaultChoiceShouldNotBeFound("updatedat.in=" + UPDATED_UPDATEDAT);
    }

    @Test
    @Transactional
    void getAllChoicesByUpdatedatIsNullOrNotNull() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where updatedat is not null
        defaultChoiceShouldBeFound("updatedat.specified=true");

        // Get all the choiceList where updatedat is null
        defaultChoiceShouldNotBeFound("updatedat.specified=false");
    }

    @Test
    @Transactional
    void getAllChoicesByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text equals to DEFAULT_TEXT
        defaultChoiceShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the choiceList where text equals to UPDATED_TEXT
        defaultChoiceShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllChoicesByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text not equals to DEFAULT_TEXT
        defaultChoiceShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the choiceList where text not equals to UPDATED_TEXT
        defaultChoiceShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllChoicesByTextIsInShouldWork() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultChoiceShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the choiceList where text equals to UPDATED_TEXT
        defaultChoiceShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllChoicesByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text is not null
        defaultChoiceShouldBeFound("text.specified=true");

        // Get all the choiceList where text is null
        defaultChoiceShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllChoicesByTextContainsSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text contains DEFAULT_TEXT
        defaultChoiceShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the choiceList where text contains UPDATED_TEXT
        defaultChoiceShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllChoicesByTextNotContainsSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where text does not contain DEFAULT_TEXT
        defaultChoiceShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the choiceList where text does not contain UPDATED_TEXT
        defaultChoiceShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes equals to DEFAULT_VOTES
        defaultChoiceShouldBeFound("votes.equals=" + DEFAULT_VOTES);

        // Get all the choiceList where votes equals to UPDATED_VOTES
        defaultChoiceShouldNotBeFound("votes.equals=" + UPDATED_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes not equals to DEFAULT_VOTES
        defaultChoiceShouldNotBeFound("votes.notEquals=" + DEFAULT_VOTES);

        // Get all the choiceList where votes not equals to UPDATED_VOTES
        defaultChoiceShouldBeFound("votes.notEquals=" + UPDATED_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsInShouldWork() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes in DEFAULT_VOTES or UPDATED_VOTES
        defaultChoiceShouldBeFound("votes.in=" + DEFAULT_VOTES + "," + UPDATED_VOTES);

        // Get all the choiceList where votes equals to UPDATED_VOTES
        defaultChoiceShouldNotBeFound("votes.in=" + UPDATED_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes is not null
        defaultChoiceShouldBeFound("votes.specified=true");

        // Get all the choiceList where votes is null
        defaultChoiceShouldNotBeFound("votes.specified=false");
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes is greater than or equal to DEFAULT_VOTES
        defaultChoiceShouldBeFound("votes.greaterThanOrEqual=" + DEFAULT_VOTES);

        // Get all the choiceList where votes is greater than or equal to UPDATED_VOTES
        defaultChoiceShouldNotBeFound("votes.greaterThanOrEqual=" + UPDATED_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes is less than or equal to DEFAULT_VOTES
        defaultChoiceShouldBeFound("votes.lessThanOrEqual=" + DEFAULT_VOTES);

        // Get all the choiceList where votes is less than or equal to SMALLER_VOTES
        defaultChoiceShouldNotBeFound("votes.lessThanOrEqual=" + SMALLER_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsLessThanSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes is less than DEFAULT_VOTES
        defaultChoiceShouldNotBeFound("votes.lessThan=" + DEFAULT_VOTES);

        // Get all the choiceList where votes is less than UPDATED_VOTES
        defaultChoiceShouldBeFound("votes.lessThan=" + UPDATED_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByVotesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        // Get all the choiceList where votes is greater than DEFAULT_VOTES
        defaultChoiceShouldNotBeFound("votes.greaterThan=" + DEFAULT_VOTES);

        // Get all the choiceList where votes is greater than SMALLER_VOTES
        defaultChoiceShouldBeFound("votes.greaterThan=" + SMALLER_VOTES);
    }

    @Test
    @Transactional
    void getAllChoicesByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);
        Question question = QuestionResourceIT.createEntity(em);
        em.persist(question);
        em.flush();
        choice.setQuestion(question);
        choiceRepository.saveAndFlush(choice);
        Long questionId = question.getId();

        // Get all the choiceList where question equals to questionId
        defaultChoiceShouldBeFound("questionId.equals=" + questionId);

        // Get all the choiceList where question equals to (questionId + 1)
        defaultChoiceShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChoiceShouldBeFound(String filter) throws Exception {
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES.intValue())));

        // Check, that the count call also returns 1
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChoiceShouldNotBeFound(String filter) throws Exception {
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingChoice() throws Exception {
        // Get the choice
        restChoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice
        Choice updatedChoice = choiceRepository.findById(choice.getId()).get();
        // Disconnect from session so that the updates on updatedChoice are not directly saved in db
        em.detach(updatedChoice);
        updatedChoice.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).text(UPDATED_TEXT).votes(UPDATED_VOTES);
        ChoiceDTO choiceDTO = choiceMapper.toDto(updatedChoice);

        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, choiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testChoice.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testChoice.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testChoice.getVotes()).isEqualTo(UPDATED_VOTES);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository).save(testChoice);
    }

    @Test
    @Transactional
    void putNonExistingChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, choiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void putWithIdMismatchChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(choiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void partialUpdateChoiceWithPatch() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice using partial update
        Choice partialUpdatedChoice = new Choice();
        partialUpdatedChoice.setId(choice.getId());

        partialUpdatedChoice.updatedat(UPDATED_UPDATEDAT);

        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoice))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testChoice.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testChoice.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testChoice.getVotes()).isEqualTo(DEFAULT_VOTES);
    }

    @Test
    @Transactional
    void fullUpdateChoiceWithPatch() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();

        // Update the choice using partial update
        Choice partialUpdatedChoice = new Choice();
        partialUpdatedChoice.setId(choice.getId());

        partialUpdatedChoice.createdat(UPDATED_CREATEDAT).updatedat(UPDATED_UPDATEDAT).text(UPDATED_TEXT).votes(UPDATED_VOTES);

        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChoice))
            )
            .andExpect(status().isOk());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);
        Choice testChoice = choiceList.get(choiceList.size() - 1);
        assertThat(testChoice.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testChoice.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testChoice.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testChoice.getVotes()).isEqualTo(UPDATED_VOTES);
    }

    @Test
    @Transactional
    void patchNonExistingChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, choiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChoice() throws Exception {
        int databaseSizeBeforeUpdate = choiceRepository.findAll().size();
        choice.setId(count.incrementAndGet());

        // Create the Choice
        ChoiceDTO choiceDTO = choiceMapper.toDto(choice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChoiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(choiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Choice in the database
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(0)).save(choice);
    }

    @Test
    @Transactional
    void deleteChoice() throws Exception {
        // Initialize the database
        choiceRepository.saveAndFlush(choice);

        int databaseSizeBeforeDelete = choiceRepository.findAll().size();

        // Delete the choice
        restChoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, choice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Choice> choiceList = choiceRepository.findAll();
        assertThat(choiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Choice in Elasticsearch
        verify(mockChoiceSearchRepository, times(1)).deleteById(choice.getId());
    }

    @Test
    @Transactional
    void searchChoice() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        choiceRepository.saveAndFlush(choice);
        when(mockChoiceSearchRepository.search(queryStringQuery("id:" + choice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(choice), PageRequest.of(0, 1), 1));

        // Search the choice
        restChoiceMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + choice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choice.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES.intValue())));
    }
}
