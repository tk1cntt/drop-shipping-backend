package com.dropshipping.repository;

import com.dropshipping.domain.OrderPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderPackageRepository extends JpaRepository<OrderPackage, Long> {

    @Query("select orderPackage from OrderPackage orderPackage where orderPackage.createBy.login = ?#{principal.username}")
    List<OrderPackage> findByCreateByIsCurrentUser();

    @Query("select orderPackage from OrderPackage orderPackage where orderPackage.updateBy.login = ?#{principal.username}")
    List<OrderPackage> findByUpdateByIsCurrentUser();

}
