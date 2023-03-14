package com.degrize.hseapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.degrize.hseapp.IntegrationTest;
import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.repository.SignalementRepository;
import com.degrize.hseapp.service.SignalementService;
import com.degrize.hseapp.service.dto.SignalementDTO;
import com.degrize.hseapp.service.mapper.SignalementMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SignalementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SignalementResourceIT {

    private static final String DEFAULT_TEXTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/signalements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SignalementRepository signalementRepository;

    @Mock
    private SignalementRepository signalementRepositoryMock;

    @Autowired
    private SignalementMapper signalementMapper;

    @Mock
    private SignalementService signalementServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignalementMockMvc;

    private Signalement signalement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Signalement createEntity(EntityManager em) {
        Signalement signalement = new Signalement().texte(DEFAULT_TEXTE).date(DEFAULT_DATE);
        return signalement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Signalement createUpdatedEntity(EntityManager em) {
        Signalement signalement = new Signalement().texte(UPDATED_TEXTE).date(UPDATED_DATE);
        return signalement;
    }

    @BeforeEach
    public void initTest() {
        signalement = createEntity(em);
    }

    @Test
    @Transactional
    void createSignalement() throws Exception {
        int databaseSizeBeforeCreate = signalementRepository.findAll().size();
        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);
        restSignalementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeCreate + 1);
        Signalement testSignalement = signalementList.get(signalementList.size() - 1);
        assertThat(testSignalement.getTexte()).isEqualTo(DEFAULT_TEXTE);
        assertThat(testSignalement.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createSignalementWithExistingId() throws Exception {
        // Create the Signalement with an existing ID
        signalement.setId(1L);
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        int databaseSizeBeforeCreate = signalementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignalementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTexteIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalementRepository.findAll().size();
        // set the field null
        signalement.setTexte(null);

        // Create the Signalement, which fails.
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        restSignalementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = signalementRepository.findAll().size();
        // set the field null
        signalement.setDate(null);

        // Create the Signalement, which fails.
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        restSignalementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSignalements() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        // Get all the signalementList
        restSignalementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signalement.getId().intValue())))
            .andExpect(jsonPath("$.[*].texte").value(hasItem(DEFAULT_TEXTE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSignalementsWithEagerRelationshipsIsEnabled() throws Exception {
        when(signalementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSignalementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(signalementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSignalementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(signalementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSignalementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(signalementRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSignalement() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        // Get the signalement
        restSignalementMockMvc
            .perform(get(ENTITY_API_URL_ID, signalement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signalement.getId().intValue()))
            .andExpect(jsonPath("$.texte").value(DEFAULT_TEXTE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSignalement() throws Exception {
        // Get the signalement
        restSignalementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSignalement() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();

        // Update the signalement
        Signalement updatedSignalement = signalementRepository.findById(signalement.getId()).get();
        // Disconnect from session so that the updates on updatedSignalement are not directly saved in db
        em.detach(updatedSignalement);
        updatedSignalement.texte(UPDATED_TEXTE).date(UPDATED_DATE);
        SignalementDTO signalementDTO = signalementMapper.toDto(updatedSignalement);

        restSignalementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, signalementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
        Signalement testSignalement = signalementList.get(signalementList.size() - 1);
        assertThat(testSignalement.getTexte()).isEqualTo(UPDATED_TEXTE);
        assertThat(testSignalement.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, signalementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(signalementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSignalementWithPatch() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();

        // Update the signalement using partial update
        Signalement partialUpdatedSignalement = new Signalement();
        partialUpdatedSignalement.setId(signalement.getId());

        partialUpdatedSignalement.date(UPDATED_DATE);

        restSignalementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSignalement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSignalement))
            )
            .andExpect(status().isOk());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
        Signalement testSignalement = signalementList.get(signalementList.size() - 1);
        assertThat(testSignalement.getTexte()).isEqualTo(DEFAULT_TEXTE);
        assertThat(testSignalement.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSignalementWithPatch() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();

        // Update the signalement using partial update
        Signalement partialUpdatedSignalement = new Signalement();
        partialUpdatedSignalement.setId(signalement.getId());

        partialUpdatedSignalement.texte(UPDATED_TEXTE).date(UPDATED_DATE);

        restSignalementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSignalement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSignalement))
            )
            .andExpect(status().isOk());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
        Signalement testSignalement = signalementList.get(signalementList.size() - 1);
        assertThat(testSignalement.getTexte()).isEqualTo(UPDATED_TEXTE);
        assertThat(testSignalement.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, signalementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSignalement() throws Exception {
        int databaseSizeBeforeUpdate = signalementRepository.findAll().size();
        signalement.setId(count.incrementAndGet());

        // Create the Signalement
        SignalementDTO signalementDTO = signalementMapper.toDto(signalement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSignalementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(signalementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Signalement in the database
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSignalement() throws Exception {
        // Initialize the database
        signalementRepository.saveAndFlush(signalement);

        int databaseSizeBeforeDelete = signalementRepository.findAll().size();

        // Delete the signalement
        restSignalementMockMvc
            .perform(delete(ENTITY_API_URL_ID, signalement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Signalement> signalementList = signalementRepository.findAll();
        assertThat(signalementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
