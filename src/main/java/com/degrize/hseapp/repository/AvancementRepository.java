package com.degrize.hseapp.repository;

import com.degrize.hseapp.domain.Avancement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Avancement entity.
 */
@Repository
public interface AvancementRepository extends JpaRepository<Avancement, Long> {
    default Optional<Avancement> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Avancement> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Avancement> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct avancement from Avancement avancement left join fetch avancement.projet",
        countQuery = "select count(distinct avancement) from Avancement avancement"
    )
    Page<Avancement> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct avancement from Avancement avancement left join fetch avancement.projet")
    List<Avancement> findAllWithToOneRelationships();

    @Query("select avancement from Avancement avancement left join fetch avancement.projet where avancement.id =:id")
    Optional<Avancement> findOneWithToOneRelationships(@Param("id") Long id);
}
