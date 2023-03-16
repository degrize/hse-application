package com.degrize.hseapp.repository;

import com.degrize.hseapp.domain.Regle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regle entity.
 */
@Repository
public interface RegleRepository extends JpaRepository<Regle, Long> {
    default Optional<Regle> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Regle> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Regle> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct regle from Regle regle left join fetch regle.projet",
        countQuery = "select count(distinct regle) from Regle regle"
    )
    Page<Regle> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct regle from Regle regle left join fetch regle.projet")
    List<Regle> findAllWithToOneRelationships();

    @Query("select regle from Regle regle left join fetch regle.projet where regle.id =:id")
    Optional<Regle> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select regle from Regle regle left join fetch regle.projet where regle.projet.id =:id")
    List<Regle> findAllByProjetId(@Param("id") Long id);
}
