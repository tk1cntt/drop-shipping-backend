package com.dropshipping.repository;

import com.dropshipping.domain.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Region entity.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {

    @Query(value = "select distinct region from Region region left join fetch region.users",
        countQuery = "select count(distinct region) from Region region")
    Page<Region> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct region from Region region left join fetch region.users")
    List<Region> findAllWithEagerRelationships();

    @Query("select region from Region region left join fetch region.users where region.id =:id")
    Optional<Region> findOneWithEagerRelationships(@Param("id") Long id);

}
