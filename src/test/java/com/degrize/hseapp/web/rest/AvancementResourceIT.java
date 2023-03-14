package com.degrize.hseapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.degrize.hseapp.IntegrationTest;
import com.degrize.hseapp.domain.Avancement;
import com.degrize.hseapp.repository.AvancementRepository;
import com.degrize.hseapp.service.AvancementService;
import com.degrize.hseapp.service.dto.AvancementDTO;
import com.degrize.hseapp.service.mapper.AvancementMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AvancementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AvancementResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FICHIER_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FICHIER_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FICHIER_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FICHIER_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHIER_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHIER_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHIER_4_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/avancements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvancementRepository avancementRepository;

    @Mock
    private AvancementRepository avancementRepositoryMock;

    @Autowired
    private AvancementMapper avancementMapper;

    @Mock
    private AvancementService avancementServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvancementMockMvc;

    private Avancement avancement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avancement createEntity(EntityManager em) {
        Avancement avancement = new Avancement()
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .fichier1(DEFAULT_FICHIER_1)
            .fichier1ContentType(DEFAULT_FICHIER_1_CONTENT_TYPE)
            .fichier2(DEFAULT_FICHIER_2)
            .fichier2ContentType(DEFAULT_FICHIER_2_CONTENT_TYPE)
            .fichier3(DEFAULT_FICHIER_3)
            .fichier3ContentType(DEFAULT_FICHIER_3_CONTENT_TYPE)
            .fichier4(DEFAULT_FICHIER_4)
            .fichier4ContentType(DEFAULT_FICHIER_4_CONTENT_TYPE);
        return avancement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avancement createUpdatedEntity(EntityManager em) {
        Avancement avancement = new Avancement()
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);
        return avancement;
    }

    @BeforeEach
    public void initTest() {
        avancement = createEntity(em);
    }

    @Test
    @Transactional
    void createAvancement() throws Exception {
        int databaseSizeBeforeCreate = avancementRepository.findAll().size();
        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);
        restAvancementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avancementDTO)))
            .andExpect(status().isCreated());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeCreate + 1);
        Avancement testAvancement = avancementList.get(avancementList.size() - 1);
        assertThat(testAvancement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAvancement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAvancement.getFichier1()).isEqualTo(DEFAULT_FICHIER_1);
        assertThat(testAvancement.getFichier1ContentType()).isEqualTo(DEFAULT_FICHIER_1_CONTENT_TYPE);
        assertThat(testAvancement.getFichier2()).isEqualTo(DEFAULT_FICHIER_2);
        assertThat(testAvancement.getFichier2ContentType()).isEqualTo(DEFAULT_FICHIER_2_CONTENT_TYPE);
        assertThat(testAvancement.getFichier3()).isEqualTo(DEFAULT_FICHIER_3);
        assertThat(testAvancement.getFichier3ContentType()).isEqualTo(DEFAULT_FICHIER_3_CONTENT_TYPE);
        assertThat(testAvancement.getFichier4()).isEqualTo(DEFAULT_FICHIER_4);
        assertThat(testAvancement.getFichier4ContentType()).isEqualTo(DEFAULT_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createAvancementWithExistingId() throws Exception {
        // Create the Avancement with an existing ID
        avancement.setId(1L);
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        int databaseSizeBeforeCreate = avancementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvancementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avancementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = avancementRepository.findAll().size();
        // set the field null
        avancement.setDescription(null);

        // Create the Avancement, which fails.
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        restAvancementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avancementDTO)))
            .andExpect(status().isBadRequest());

        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = avancementRepository.findAll().size();
        // set the field null
        avancement.setDate(null);

        // Create the Avancement, which fails.
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        restAvancementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avancementDTO)))
            .andExpect(status().isBadRequest());

        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvancements() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        // Get all the avancementList
        restAvancementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avancement.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fichier1ContentType").value(hasItem(DEFAULT_FICHIER_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_1))))
            .andExpect(jsonPath("$.[*].fichier2ContentType").value(hasItem(DEFAULT_FICHIER_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_2))))
            .andExpect(jsonPath("$.[*].fichier3ContentType").value(hasItem(DEFAULT_FICHIER_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_3))))
            .andExpect(jsonPath("$.[*].fichier4ContentType").value(hasItem(DEFAULT_FICHIER_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier4").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_4))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAvancementsWithEagerRelationshipsIsEnabled() throws Exception {
        when(avancementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAvancementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(avancementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAvancementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(avancementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAvancementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(avancementRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAvancement() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        // Get the avancement
        restAvancementMockMvc
            .perform(get(ENTITY_API_URL_ID, avancement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avancement.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.fichier1ContentType").value(DEFAULT_FICHIER_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier1").value(Base64Utils.encodeToString(DEFAULT_FICHIER_1)))
            .andExpect(jsonPath("$.fichier2ContentType").value(DEFAULT_FICHIER_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier2").value(Base64Utils.encodeToString(DEFAULT_FICHIER_2)))
            .andExpect(jsonPath("$.fichier3ContentType").value(DEFAULT_FICHIER_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier3").value(Base64Utils.encodeToString(DEFAULT_FICHIER_3)))
            .andExpect(jsonPath("$.fichier4ContentType").value(DEFAULT_FICHIER_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.fichier4").value(Base64Utils.encodeToString(DEFAULT_FICHIER_4)));
    }

    @Test
    @Transactional
    void getNonExistingAvancement() throws Exception {
        // Get the avancement
        restAvancementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAvancement() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();

        // Update the avancement
        Avancement updatedAvancement = avancementRepository.findById(avancement.getId()).get();
        // Disconnect from session so that the updates on updatedAvancement are not directly saved in db
        em.detach(updatedAvancement);
        updatedAvancement
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);
        AvancementDTO avancementDTO = avancementMapper.toDto(updatedAvancement);

        restAvancementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avancementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
        Avancement testAvancement = avancementList.get(avancementList.size() - 1);
        assertThat(testAvancement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAvancement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAvancement.getFichier1()).isEqualTo(UPDATED_FICHIER_1);
        assertThat(testAvancement.getFichier1ContentType()).isEqualTo(UPDATED_FICHIER_1_CONTENT_TYPE);
        assertThat(testAvancement.getFichier2()).isEqualTo(UPDATED_FICHIER_2);
        assertThat(testAvancement.getFichier2ContentType()).isEqualTo(UPDATED_FICHIER_2_CONTENT_TYPE);
        assertThat(testAvancement.getFichier3()).isEqualTo(UPDATED_FICHIER_3);
        assertThat(testAvancement.getFichier3ContentType()).isEqualTo(UPDATED_FICHIER_3_CONTENT_TYPE);
        assertThat(testAvancement.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testAvancement.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avancementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avancementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvancementWithPatch() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();

        // Update the avancement using partial update
        Avancement partialUpdatedAvancement = new Avancement();
        partialUpdatedAvancement.setId(avancement.getId());

        partialUpdatedAvancement
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);

        restAvancementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvancement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvancement))
            )
            .andExpect(status().isOk());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
        Avancement testAvancement = avancementList.get(avancementList.size() - 1);
        assertThat(testAvancement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAvancement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAvancement.getFichier1()).isEqualTo(DEFAULT_FICHIER_1);
        assertThat(testAvancement.getFichier1ContentType()).isEqualTo(DEFAULT_FICHIER_1_CONTENT_TYPE);
        assertThat(testAvancement.getFichier2()).isEqualTo(DEFAULT_FICHIER_2);
        assertThat(testAvancement.getFichier2ContentType()).isEqualTo(DEFAULT_FICHIER_2_CONTENT_TYPE);
        assertThat(testAvancement.getFichier3()).isEqualTo(UPDATED_FICHIER_3);
        assertThat(testAvancement.getFichier3ContentType()).isEqualTo(UPDATED_FICHIER_3_CONTENT_TYPE);
        assertThat(testAvancement.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testAvancement.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAvancementWithPatch() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();

        // Update the avancement using partial update
        Avancement partialUpdatedAvancement = new Avancement();
        partialUpdatedAvancement.setId(avancement.getId());

        partialUpdatedAvancement
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);

        restAvancementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvancement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvancement))
            )
            .andExpect(status().isOk());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
        Avancement testAvancement = avancementList.get(avancementList.size() - 1);
        assertThat(testAvancement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAvancement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAvancement.getFichier1()).isEqualTo(UPDATED_FICHIER_1);
        assertThat(testAvancement.getFichier1ContentType()).isEqualTo(UPDATED_FICHIER_1_CONTENT_TYPE);
        assertThat(testAvancement.getFichier2()).isEqualTo(UPDATED_FICHIER_2);
        assertThat(testAvancement.getFichier2ContentType()).isEqualTo(UPDATED_FICHIER_2_CONTENT_TYPE);
        assertThat(testAvancement.getFichier3()).isEqualTo(UPDATED_FICHIER_3);
        assertThat(testAvancement.getFichier3ContentType()).isEqualTo(UPDATED_FICHIER_3_CONTENT_TYPE);
        assertThat(testAvancement.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testAvancement.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avancementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvancement() throws Exception {
        int databaseSizeBeforeUpdate = avancementRepository.findAll().size();
        avancement.setId(count.incrementAndGet());

        // Create the Avancement
        AvancementDTO avancementDTO = avancementMapper.toDto(avancement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvancementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avancementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avancement in the database
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvancement() throws Exception {
        // Initialize the database
        avancementRepository.saveAndFlush(avancement);

        int databaseSizeBeforeDelete = avancementRepository.findAll().size();

        // Delete the avancement
        restAvancementMockMvc
            .perform(delete(ENTITY_API_URL_ID, avancement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avancement> avancementList = avancementRepository.findAll();
        assertThat(avancementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
