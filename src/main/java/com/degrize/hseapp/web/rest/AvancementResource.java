package com.degrize.hseapp.web.rest;

import com.degrize.hseapp.repository.AvancementRepository;
import com.degrize.hseapp.service.AvancementService;
import com.degrize.hseapp.service.dto.AvancementDTO;
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
 * REST controller for managing {@link com.degrize.hseapp.domain.Avancement}.
 */
@RestController
@RequestMapping("/api")
public class AvancementResource {

    private final Logger log = LoggerFactory.getLogger(AvancementResource.class);

    private static final String ENTITY_NAME = "avancement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvancementService avancementService;

    private final AvancementRepository avancementRepository;

    public AvancementResource(AvancementService avancementService, AvancementRepository avancementRepository) {
        this.avancementService = avancementService;
        this.avancementRepository = avancementRepository;
    }

    /**
     * {@code POST  /avancements} : Create a new avancement.
     *
     * @param avancementDTO the avancementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avancementDTO, or with status {@code 400 (Bad Request)} if the avancement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/avancements")
    public ResponseEntity<AvancementDTO> createAvancement(@Valid @RequestBody AvancementDTO avancementDTO) throws URISyntaxException {
        log.debug("REST request to save Avancement : {}", avancementDTO);
        if (avancementDTO.getId() != null) {
            throw new BadRequestAlertException("A new avancement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvancementDTO result = avancementService.save(avancementDTO);
        return ResponseEntity
            .created(new URI("/api/avancements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avancements/:id} : Updates an existing avancement.
     *
     * @param id the id of the avancementDTO to save.
     * @param avancementDTO the avancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avancementDTO,
     * or with status {@code 400 (Bad Request)} if the avancementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/avancements/{id}")
    public ResponseEntity<AvancementDTO> updateAvancement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvancementDTO avancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Avancement : {}, {}", id, avancementDTO);
        if (avancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avancementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvancementDTO result = avancementService.update(avancementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avancementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avancements/:id} : Partial updates given fields of an existing avancement, field will ignore if it is null
     *
     * @param id the id of the avancementDTO to save.
     * @param avancementDTO the avancementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avancementDTO,
     * or with status {@code 400 (Bad Request)} if the avancementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avancementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avancementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/avancements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvancementDTO> partialUpdateAvancement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvancementDTO avancementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avancement partially : {}, {}", id, avancementDTO);
        if (avancementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avancementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avancementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvancementDTO> result = avancementService.partialUpdate(avancementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avancementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avancements} : get all the avancements.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avancements in body.
     */
    @GetMapping("/avancements")
    public ResponseEntity<List<AvancementDTO>> getAllAvancements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Avancements");
        Page<AvancementDTO> page;
        if (eagerload) {
            page = avancementService.findAllWithEagerRelationships(pageable);
        } else {
            page = avancementService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avancements/:id} : get the "id" avancement.
     *
     * @param id the id of the avancementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avancementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/avancements/{id}")
    public ResponseEntity<AvancementDTO> getAvancement(@PathVariable Long id) {
        log.debug("REST request to get Avancement : {}", id);
        Optional<AvancementDTO> avancementDTO = avancementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avancementDTO);
    }

    /**
     * {@code DELETE  /avancements/:id} : delete the "id" avancement.
     *
     * @param id the id of the avancementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/avancements/{id}")
    public ResponseEntity<Void> deleteAvancement(@PathVariable Long id) {
        log.debug("REST request to delete Avancement : {}", id);
        avancementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
