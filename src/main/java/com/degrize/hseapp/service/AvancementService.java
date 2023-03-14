package com.degrize.hseapp.service;

import com.degrize.hseapp.service.dto.AvancementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.degrize.hseapp.domain.Avancement}.
 */
public interface AvancementService {
    /**
     * Save a avancement.
     *
     * @param avancementDTO the entity to save.
     * @return the persisted entity.
     */
    AvancementDTO save(AvancementDTO avancementDTO);

    /**
     * Updates a avancement.
     *
     * @param avancementDTO the entity to update.
     * @return the persisted entity.
     */
    AvancementDTO update(AvancementDTO avancementDTO);

    /**
     * Partially updates a avancement.
     *
     * @param avancementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvancementDTO> partialUpdate(AvancementDTO avancementDTO);

    /**
     * Get all the avancements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvancementDTO> findAll(Pageable pageable);

    /**
     * Get all the avancements with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvancementDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" avancement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvancementDTO> findOne(Long id);

    /**
     * Delete the "id" avancement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
