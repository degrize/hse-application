package com.degrize.hseapp.service;

import com.degrize.hseapp.domain.Regle;
import com.degrize.hseapp.service.dto.RegleDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.degrize.hseapp.domain.Regle}.
 */
public interface RegleService {
    /**
     * Save a regle.
     *
     * @param regleDTO the entity to save.
     * @return the persisted entity.
     */
    RegleDTO save(RegleDTO regleDTO);

    /**
     * Updates a regle.
     *
     * @param regleDTO the entity to update.
     * @return the persisted entity.
     */
    RegleDTO update(RegleDTO regleDTO);

    /**
     * Partially updates a regle.
     *
     * @param regleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegleDTO> partialUpdate(RegleDTO regleDTO);

    /**
     * Get all the regles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegleDTO> findAll(Pageable pageable);

    /**
     * Get all the regles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" regle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegleDTO> findOne(Long id);

    /**
     * Delete the "id" regle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<Regle> findAllByProjetId(Long id);
}
