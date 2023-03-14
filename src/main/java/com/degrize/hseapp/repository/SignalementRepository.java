package com.degrize.hseapp.repository;

import com.degrize.hseapp.domain.Signalement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Signalement entity.
 */
@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long> {
    default Optional<Signalement> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Signalement> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Signalement> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct signalement from Signalement signalement left join fetch signalement.projet",
        countQuery = "select count(distinct signalement) from Signalement signalement"
    )
    Page<Signalement> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct signalement from Signalement signalement left join fetch signalement.projet")
    List<Signalement> findAllWithToOneRelationships();

    @Query("select signalement from Signalement signalement left join fetch signalement.projet where signalement.id =:id")
    Optional<Signalement> findOneWithToOneRelationships(@Param("id") Long id);
}
