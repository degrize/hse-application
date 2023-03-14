package com.degrize.hseapp.web.rest;

import com.degrize.hseapp.repository.RegleRepository;
import com.degrize.hseapp.service.RegleService;
import com.degrize.hseapp.service.dto.RegleDTO;
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
 * REST controller for managing {@link com.degrize.hseapp.domain.Regle}.
 */
@RestController
@RequestMapping("/api")
public class RegleResource {

    private final Logger log = LoggerFactory.getLogger(RegleResource.class);

    private static final String ENTITY_NAME = "regle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegleService regleService;

    private final RegleRepository regleRepository;

    public RegleResource(RegleService regleService, RegleRepository regleRepository) {
        this.regleService = regleService;
        this.regleRepository = regleRepository;
    }

    /**
     * {@code POST  /regles} : Create a new regle.
     *
     * @param regleDTO the regleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regleDTO, or with status {@code 400 (Bad Request)} if the regle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regles")
    public ResponseEntity<RegleDTO> createRegle(@Valid @RequestBody RegleDTO regleDTO) throws URISyntaxException {
        log.debug("REST request to save Regle : {}", regleDTO);
        if (regleDTO.getId() != null) {
            throw new BadRequestAlertException("A new regle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegleDTO result = regleService.save(regleDTO);
        return ResponseEntity
            .created(new URI("/api/regles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regles/:id} : Updates an existing regle.
     *
     * @param id the id of the regleDTO to save.
     * @param regleDTO the regleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regleDTO,
     * or with status {@code 400 (Bad Request)} if the regleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regles/{id}")
    public ResponseEntity<RegleDTO> updateRegle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegleDTO regleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Regle : {}, {}", id, regleDTO);
        if (regleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegleDTO result = regleService.update(regleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /regles/:id} : Partial updates given fields of an existing regle, field will ignore if it is null
     *
     * @param id the id of the regleDTO to save.
     * @param regleDTO the regleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regleDTO,
     * or with status {@code 400 (Bad Request)} if the regleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the regleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the regleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegleDTO> partialUpdateRegle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegleDTO regleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regle partially : {}, {}", id, regleDTO);
        if (regleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegleDTO> result = regleService.partialUpdate(regleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /regles} : get all the regles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regles in body.
     */
    @GetMapping("/regles")
    public ResponseEntity<List<RegleDTO>> getAllRegles(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Regles");
        Page<RegleDTO> page;
        if (eagerload) {
            page = regleService.findAllWithEagerRelationships(pageable);
        } else {
            page = regleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regles/:id} : get the "id" regle.
     *
     * @param id the id of the regleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regles/{id}")
    public ResponseEntity<RegleDTO> getRegle(@PathVariable Long id) {
        log.debug("REST request to get Regle : {}", id);
        Optional<RegleDTO> regleDTO = regleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regleDTO);
    }

    /**
     * {@code DELETE  /regles/:id} : delete the "id" regle.
     *
     * @param id the id of the regleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regles/{id}")
    public ResponseEntity<Void> deleteRegle(@PathVariable Long id) {
        log.debug("REST request to delete Regle : {}", id);
        regleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
