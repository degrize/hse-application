package com.degrize.hseapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.degrize.hseapp.IntegrationTest;
import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.repository.ProjetRepository;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.service.mapper.ProjetMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProjetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjetResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ProjetMapper projetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjetMockMvc;

    private Projet projet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createEntity(EntityManager em) {
        Projet projet = new Projet()
            .titre(DEFAULT_TITRE)
            .description(DEFAULT_DESCRIPTION)
            .duree(DEFAULT_DUREE)
            .ville(DEFAULT_VILLE)
            .code(DEFAULT_CODE)
            .fichier1(DEFAULT_FICHIER_1)
            .fichier1ContentType(DEFAULT_FICHIER_1_CONTENT_TYPE)
            .fichier2(DEFAULT_FICHIER_2)
            .fichier2ContentType(DEFAULT_FICHIER_2_CONTENT_TYPE)
            .fichier3(DEFAULT_FICHIER_3)
            .fichier3ContentType(DEFAULT_FICHIER_3_CONTENT_TYPE)
            .fichier4(DEFAULT_FICHIER_4)
            .fichier4ContentType(DEFAULT_FICHIER_4_CONTENT_TYPE);
        return projet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createUpdatedEntity(EntityManager em) {
        Projet projet = new Projet()
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .ville(UPDATED_VILLE)
            .code(UPDATED_CODE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);
        return projet;
    }

    @BeforeEach
    public void initTest() {
        projet = createEntity(em);
    }

    @Test
    @Transactional
    void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();
        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);
        restProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testProjet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjet.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testProjet.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testProjet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjet.getFichier1()).isEqualTo(DEFAULT_FICHIER_1);
        assertThat(testProjet.getFichier1ContentType()).isEqualTo(DEFAULT_FICHIER_1_CONTENT_TYPE);
        assertThat(testProjet.getFichier2()).isEqualTo(DEFAULT_FICHIER_2);
        assertThat(testProjet.getFichier2ContentType()).isEqualTo(DEFAULT_FICHIER_2_CONTENT_TYPE);
        assertThat(testProjet.getFichier3()).isEqualTo(DEFAULT_FICHIER_3);
        assertThat(testProjet.getFichier3ContentType()).isEqualTo(DEFAULT_FICHIER_3_CONTENT_TYPE);
        assertThat(testProjet.getFichier4()).isEqualTo(DEFAULT_FICHIER_4);
        assertThat(testProjet.getFichier4ContentType()).isEqualTo(DEFAULT_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createProjetWithExistingId() throws Exception {
        // Create the Projet with an existing ID
        projet.setId(1L);
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = projetRepository.findAll().size();
        // set the field null
        projet.setTitre(null);

        // Create the Projet, which fails.
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        restProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isBadRequest());

        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList
        restProjetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fichier1ContentType").value(hasItem(DEFAULT_FICHIER_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_1))))
            .andExpect(jsonPath("$.[*].fichier2ContentType").value(hasItem(DEFAULT_FICHIER_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_2))))
            .andExpect(jsonPath("$.[*].fichier3ContentType").value(hasItem(DEFAULT_FICHIER_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier3").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_3))))
            .andExpect(jsonPath("$.[*].fichier4ContentType").value(hasItem(DEFAULT_FICHIER_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fichier4").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHIER_4))));
    }

    @Test
    @Transactional
    void getProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get the projet
        restProjetMockMvc
            .perform(get(ENTITY_API_URL_ID, projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
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
    void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findById(projet.getId()).get();
        // Disconnect from session so that the updates on updatedProjet are not directly saved in db
        em.detach(updatedProjet);
        updatedProjet
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .ville(UPDATED_VILLE)
            .code(UPDATED_CODE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);
        ProjetDTO projetDTO = projetMapper.toDto(updatedProjet);

        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjet.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testProjet.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testProjet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjet.getFichier1()).isEqualTo(UPDATED_FICHIER_1);
        assertThat(testProjet.getFichier1ContentType()).isEqualTo(UPDATED_FICHIER_1_CONTENT_TYPE);
        assertThat(testProjet.getFichier2()).isEqualTo(UPDATED_FICHIER_2);
        assertThat(testProjet.getFichier2ContentType()).isEqualTo(UPDATED_FICHIER_2_CONTENT_TYPE);
        assertThat(testProjet.getFichier3()).isEqualTo(UPDATED_FICHIER_3);
        assertThat(testProjet.getFichier3ContentType()).isEqualTo(UPDATED_FICHIER_3_CONTENT_TYPE);
        assertThat(testProjet.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testProjet.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.duree(UPDATED_DUREE).fichier4(UPDATED_FICHIER_4).fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testProjet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjet.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testProjet.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testProjet.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjet.getFichier1()).isEqualTo(DEFAULT_FICHIER_1);
        assertThat(testProjet.getFichier1ContentType()).isEqualTo(DEFAULT_FICHIER_1_CONTENT_TYPE);
        assertThat(testProjet.getFichier2()).isEqualTo(DEFAULT_FICHIER_2);
        assertThat(testProjet.getFichier2ContentType()).isEqualTo(DEFAULT_FICHIER_2_CONTENT_TYPE);
        assertThat(testProjet.getFichier3()).isEqualTo(DEFAULT_FICHIER_3);
        assertThat(testProjet.getFichier3ContentType()).isEqualTo(DEFAULT_FICHIER_3_CONTENT_TYPE);
        assertThat(testProjet.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testProjet.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .duree(UPDATED_DUREE)
            .ville(UPDATED_VILLE)
            .code(UPDATED_CODE)
            .fichier1(UPDATED_FICHIER_1)
            .fichier1ContentType(UPDATED_FICHIER_1_CONTENT_TYPE)
            .fichier2(UPDATED_FICHIER_2)
            .fichier2ContentType(UPDATED_FICHIER_2_CONTENT_TYPE)
            .fichier3(UPDATED_FICHIER_3)
            .fichier3ContentType(UPDATED_FICHIER_3_CONTENT_TYPE)
            .fichier4(UPDATED_FICHIER_4)
            .fichier4ContentType(UPDATED_FICHIER_4_CONTENT_TYPE);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testProjet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjet.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testProjet.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testProjet.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjet.getFichier1()).isEqualTo(UPDATED_FICHIER_1);
        assertThat(testProjet.getFichier1ContentType()).isEqualTo(UPDATED_FICHIER_1_CONTENT_TYPE);
        assertThat(testProjet.getFichier2()).isEqualTo(UPDATED_FICHIER_2);
        assertThat(testProjet.getFichier2ContentType()).isEqualTo(UPDATED_FICHIER_2_CONTENT_TYPE);
        assertThat(testProjet.getFichier3()).isEqualTo(UPDATED_FICHIER_3);
        assertThat(testProjet.getFichier3ContentType()).isEqualTo(UPDATED_FICHIER_3_CONTENT_TYPE);
        assertThat(testProjet.getFichier4()).isEqualTo(UPDATED_FICHIER_4);
        assertThat(testProjet.getFichier4ContentType()).isEqualTo(UPDATED_FICHIER_4_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Delete the projet
        restProjetMockMvc
            .perform(delete(ENTITY_API_URL_ID, projet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
