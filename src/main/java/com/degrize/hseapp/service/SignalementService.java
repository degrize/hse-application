package com.degrize.hseapp.service;

import com.degrize.hseapp.domain.Signalement;
import com.degrize.hseapp.service.dto.SignalementDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.degrize.hseapp.domain.Signalement}.
 */
public interface SignalementService {
    /**
     * Save a signalement.
     *
     * @param signalementDTO the entity to save.
     * @return the persisted entity.
     */
    SignalementDTO save(SignalementDTO signalementDTO);

    /**
     * Updates a signalement.
     *
     * @param signalementDTO the entity to update.
     * @return the persisted entity.
     */
    SignalementDTO update(SignalementDTO signalementDTO);

    /**
     * Partially updates a signalement.
     *
     * @param signalementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SignalementDTO> partialUpdate(SignalementDTO signalementDTO);

    /**
     * Get all the signalements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignalementDTO> findAll(Pageable pageable);

    /**
     * Get all the signalements with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SignalementDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" signalement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SignalementDTO> findOne(Long id);

    /**
     * Delete the "id" signalement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<Signalement> findAllByProjetId(Long id);
}
