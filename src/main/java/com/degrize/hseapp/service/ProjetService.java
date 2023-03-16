package com.degrize.hseapp.service;

import com.degrize.hseapp.domain.Projet;
import com.degrize.hseapp.service.dto.ProjetDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.degrize.hseapp.domain.Projet}.
 */
public interface ProjetService {
    /**
     * Save a projet.
     *
     * @param projetDTO the entity to save.
     * @return the persisted entity.
     */
    ProjetDTO save(ProjetDTO projetDTO);

    /**
     * Updates a projet.
     *
     * @param projetDTO the entity to update.
     * @return the persisted entity.
     */
    ProjetDTO update(ProjetDTO projetDTO);

    /**
     * Partially updates a projet.
     *
     * @param projetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjetDTO> partialUpdate(ProjetDTO projetDTO);

    /**
     * Get all the projets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjetDTO> findOne(Long id);

    /**
     * Delete the "id" projet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Projet> findAllNoPageble();

    List<Projet> findAllByProjetTitre(String projetTitre);
}
