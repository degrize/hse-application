package com.degrize.hseapp.repository;

import com.degrize.hseapp.domain.Projet;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    @Query("select projet FROM Projet projet WHERE lower(projet.titre) Like concat('%', lower(:projetTitre), '%')")
    List<Projet> findProjetByLikeTitre(@Param("projetTitre") String projetTitre);
}
