package com.degrize.hseapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.degrize.hseapp.IntegrationTest;
import com.degrize.hseapp.domain.Regle;
import com.degrize.hseapp.repository.RegleRepository;
import com.degrize.hseapp.service.RegleService;
import com.degrize.hseapp.service.dto.RegleDTO;
import com.degrize.hseapp.service.mapper.RegleMapper;
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
 * Integration tests for the {@link RegleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RegleResourceIT {

    private static final String DEFAULT_TEXTE = "AAAAAAAAAA";
    private static final String UPDATED_TEXTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/regles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegleRepository regleRepository;

    @Mock
    private RegleRepository regleRepositoryMock;

    @Autowired
    private RegleMapper regleMapper;

    @Mock
    private RegleService regleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegleMockMvc;

    private Regle regle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regle createEntity(EntityManager em) {
        Regle regle = new Regle().texte(DEFAULT_TEXTE).date(DEFAULT_DATE);
        return regle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regle createUpdatedEntity(EntityManager em) {
        Regle regle = new Regle().texte(UPDATED_TEXTE).date(UPDATED_DATE);
        return regle;
    }

    @BeforeEach
    public void initTest() {
        regle = createEntity(em);
    }

    @Test
    @Transactional
    void createRegle() throws Exception {
        int databaseSizeBeforeCreate = regleRepository.findAll().size();
        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);
        restRegleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isCreated());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeCreate + 1);
        Regle testRegle = regleList.get(regleList.size() - 1);
        assertThat(testRegle.getTexte()).isEqualTo(DEFAULT_TEXTE);
        assertThat(testRegle.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createRegleWithExistingId() throws Exception {
        // Create the Regle with an existing ID
        regle.setId(1L);
        RegleDTO regleDTO = regleMapper.toDto(regle);

        int databaseSizeBeforeCreate = regleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTexteIsRequired() throws Exception {
        int databaseSizeBeforeTest = regleRepository.findAll().size();
        // set the field null
        regle.setTexte(null);

        // Create the Regle, which fails.
        RegleDTO regleDTO = regleMapper.toDto(regle);

        restRegleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isBadRequest());

        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = regleRepository.findAll().size();
        // set the field null
        regle.setDate(null);

        // Create the Regle, which fails.
        RegleDTO regleDTO = regleMapper.toDto(regle);

        restRegleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isBadRequest());

        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegles() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        // Get all the regleList
        restRegleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regle.getId().intValue())))
            .andExpect(jsonPath("$.[*].texte").value(hasItem(DEFAULT_TEXTE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReglesWithEagerRelationshipsIsEnabled() throws Exception {
        when(regleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(regleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReglesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(regleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRegleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(regleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRegle() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        // Get the regle
        restRegleMockMvc
            .perform(get(ENTITY_API_URL_ID, regle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regle.getId().intValue()))
            .andExpect(jsonPath("$.texte").value(DEFAULT_TEXTE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRegle() throws Exception {
        // Get the regle
        restRegleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegle() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        int databaseSizeBeforeUpdate = regleRepository.findAll().size();

        // Update the regle
        Regle updatedRegle = regleRepository.findById(regle.getId()).get();
        // Disconnect from session so that the updates on updatedRegle are not directly saved in db
        em.detach(updatedRegle);
        updatedRegle.texte(UPDATED_TEXTE).date(UPDATED_DATE);
        RegleDTO regleDTO = regleMapper.toDto(updatedRegle);

        restRegleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
        Regle testRegle = regleList.get(regleList.size() - 1);
        assertThat(testRegle.getTexte()).isEqualTo(UPDATED_TEXTE);
        assertThat(testRegle.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegleWithPatch() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        int databaseSizeBeforeUpdate = regleRepository.findAll().size();

        // Update the regle using partial update
        Regle partialUpdatedRegle = new Regle();
        partialUpdatedRegle.setId(regle.getId());

        partialUpdatedRegle.date(UPDATED_DATE);

        restRegleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegle))
            )
            .andExpect(status().isOk());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
        Regle testRegle = regleList.get(regleList.size() - 1);
        assertThat(testRegle.getTexte()).isEqualTo(DEFAULT_TEXTE);
        assertThat(testRegle.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRegleWithPatch() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        int databaseSizeBeforeUpdate = regleRepository.findAll().size();

        // Update the regle using partial update
        Regle partialUpdatedRegle = new Regle();
        partialUpdatedRegle.setId(regle.getId());

        partialUpdatedRegle.texte(UPDATED_TEXTE).date(UPDATED_DATE);

        restRegleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegle))
            )
            .andExpect(status().isOk());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
        Regle testRegle = regleList.get(regleList.size() - 1);
        assertThat(testRegle.getTexte()).isEqualTo(UPDATED_TEXTE);
        assertThat(testRegle.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegle() throws Exception {
        int databaseSizeBeforeUpdate = regleRepository.findAll().size();
        regle.setId(count.incrementAndGet());

        // Create the Regle
        RegleDTO regleDTO = regleMapper.toDto(regle);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(regleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regle in the database
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegle() throws Exception {
        // Initialize the database
        regleRepository.saveAndFlush(regle);

        int databaseSizeBeforeDelete = regleRepository.findAll().size();

        // Delete the regle
        restRegleMockMvc
            .perform(delete(ENTITY_API_URL_ID, regle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regle> regleList = regleRepository.findAll();
        assertThat(regleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
