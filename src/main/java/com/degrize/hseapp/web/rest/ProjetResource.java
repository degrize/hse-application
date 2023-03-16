package com.degrize.hseapp.web.rest;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.repository.ProjetRepository;
import com.degrize.hseapp.service.ProjetService;
import com.degrize.hseapp.service.dto.ProjetDTO;
import com.degrize.hseapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.degrize.hseapp.domain.Projet}.
 */
@RestController
@RequestMapping("/api")
public class ProjetResource {

    private final Logger log = LoggerFactory.getLogger(ProjetResource.class);

    private static final String ENTITY_NAME = "projet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjetService projetService;

    private final ProjetRepository projetRepository;

    public ProjetResource(ProjetService projetService, ProjetRepository projetRepository) {
        this.projetService = projetService;
        this.projetRepository = projetRepository;
    }

    /**
     * {@code POST  /projets} : Create a new projet.
     *
     * @param projetDTO the projetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projetDTO, or with status {@code 400 (Bad Request)} if the projet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projets")
    public ResponseEntity<ProjetDTO> createProjet(@Valid @RequestBody ProjetDTO projetDTO) throws URISyntaxException {
        log.debug("REST request to save Projet : {}", projetDTO);
        if (projetDTO.getId() != null) {
            throw new BadRequestAlertException("A new projet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjetDTO result = projetService.save(projetDTO);
        return ResponseEntity
            .created(new URI("/api/projets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projets/:id} : Updates an existing projet.
     *
     * @param id the id of the projetDTO to save.
     * @param projetDTO the projetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projetDTO,
     * or with status {@code 400 (Bad Request)} if the projetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projets/{id}")
    public ResponseEntity<ProjetDTO> updateProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjetDTO projetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Projet : {}, {}", id, projetDTO);
        if (projetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjetDTO result = projetService.update(projetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projets/:id} : Partial updates given fields of an existing projet, field will ignore if it is null
     *
     * @param id the id of the projetDTO to save.
     * @param projetDTO the projetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projetDTO,
     * or with status {@code 400 (Bad Request)} if the projetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/projets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjetDTO> partialUpdateProjet(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjetDTO projetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projet partially : {}, {}", id, projetDTO);
        if (projetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjetDTO> result = projetService.partialUpdate(projetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /projets} : get all the projets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projets in body.
     */
    @GetMapping("/projets")
    public ResponseEntity<List<ProjetDTO>> getAllProjets(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Projets");
        Page<ProjetDTO> page = projetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projets/:id} : get the "id" projet.
     *
     * @param id the id of the projetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projets/{id}")
    public ResponseEntity<ProjetDTO> getProjet(@PathVariable Long id) {
        log.debug("REST request to get Projet : {}", id);
        Optional<ProjetDTO> projetDTO = projetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projetDTO);
    }

    /**
     * {@code DELETE  /projets/:id} : delete the "id" projet.
     *
     * @param id the id of the projetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projets/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        log.debug("REST request to delete Projet : {}", id);
        projetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/projets/liste")
    public ResponseEntity<List<Projet>> getAllProjetsNoPageble() {
        log.debug("REST request to get list of Projets");
        List<Projet> projetList = projetService.findAllNoPageble();
        return ResponseEntity.ok().body(projetList);
    }

    @GetMapping("/projets/search/{projetTitre}")
    public ResponseEntity<List<Projet>> getSearchProjetNoPageable(@PathVariable String projetTitre) {
        log.debug("REST request to get a page of Projet serach ");

        List<Projet> projets = projetService.findAllByProjetTitre(projetTitre);
        return ResponseEntity.ok().body(projets);
    }
}
