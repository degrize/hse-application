package com.degrize.hseapp.web.rest;

import com.degrize.hseapp.domain.Regle;
import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.repository.SignalementRepository;
import com.degrize.hseapp.service.SignalementService;
import com.degrize.hseapp.service.dto.SignalementDTO;
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
 * REST controller for managing {@link com.degrize.hseapp.domain.Signalement}.
 */
@RestController
@RequestMapping("/api")
public class SignalementResource {

    private final Logger log = LoggerFactory.getLogger(SignalementResource.class);

    private static final String ENTITY_NAME = "signalement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignalementService signalementService;

    private final SignalementRepository signalementRepository;

    public SignalementResource(SignalementService signalementService, SignalementRepository signalementRepository) {
        this.signalementService = signalementService;
        this.signalementRepository = signalementRepository;
    }

    /**
     * {@code POST  /signalements} : Create a new signalement.
     *
     * @param signalementDTO the signalementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signalementDTO, or with status {@code 400 (Bad Request)} if the signalement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signalements")
    public ResponseEntity<SignalementDTO> createSignalement(@Valid @RequestBody SignalementDTO signalementDTO) throws URISyntaxException {
        log.debug("REST request to save Signalement : {}", signalementDTO);
        if (signalementDTO.getId() != null) {
            throw new BadRequestAlertException("A new signalement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignalementDTO result = signalementService.save(signalementDTO);
        return ResponseEntity
            .created(new URI("/api/signalements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signalements/:id} : Updates an existing signalement.
     *
     * @param id the id of the signalementDTO to save.
     * @param signalementDTO the signalementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signalementDTO,
     * or with status {@code 400 (Bad Request)} if the signalementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signalementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signalements/{id}")
    public ResponseEntity<SignalementDTO> updateSignalement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SignalementDTO signalementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Signalement : {}, {}", id, signalementDTO);
        if (signalementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signalementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signalementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SignalementDTO result = signalementService.update(signalementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signalementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /signalements/:id} : Partial updates given fields of an existing signalement, field will ignore if it is null
     *
     * @param id the id of the signalementDTO to save.
     * @param signalementDTO the signalementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signalementDTO,
     * or with status {@code 400 (Bad Request)} if the signalementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the signalementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the signalementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/signalements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SignalementDTO> partialUpdateSignalement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SignalementDTO signalementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Signalement partially : {}, {}", id, signalementDTO);
        if (signalementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, signalementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!signalementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SignalementDTO> result = signalementService.partialUpdate(signalementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signalementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /signalements} : get all the signalements.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signalements in body.
     */
    @GetMapping("/signalements")
    public ResponseEntity<List<SignalementDTO>> getAllSignalements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Signalements");
        Page<SignalementDTO> page;
        if (eagerload) {
            page = signalementService.findAllWithEagerRelationships(pageable);
        } else {
            page = signalementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /signalements/:id} : get the "id" signalement.
     *
     * @param id the id of the signalementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signalementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signalements/{id}")
    public ResponseEntity<SignalementDTO> getSignalement(@PathVariable Long id) {
        log.debug("REST request to get Signalement : {}", id);
        Optional<SignalementDTO> signalementDTO = signalementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signalementDTO);
    }

    /**
     * {@code DELETE  /signalements/:id} : delete the "id" signalement.
     *
     * @param id the id of the signalementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signalements/{id}")
    public ResponseEntity<Void> deleteSignalement(@PathVariable Long id) {
        log.debug("REST request to delete Signalement : {}", id);
        signalementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/signalements/listeByProjetId/{id}")
    public ResponseEntity<List<Signalement>> getAllByProjetId(@PathVariable Long id) {
        log.debug("REST request to get list of signalement of Projets ");
        List<Signalement> signalementList = signalementService.findAllByProjetId(id);
        return ResponseEntity.ok().body(signalementList);
    }
}
