package com.dropshipping.repository;

import com.dropshipping.domain.DeliveryPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DeliveryPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Long> {

    @Query("select deliveryPackage from DeliveryPackage deliveryPackage where deliveryPackage.createBy.login = ?#{principal.username}")
    List<DeliveryPackage> findByCreateByIsCurrentUser();

    @Query("select deliveryPackage from DeliveryPackage deliveryPackage where deliveryPackage.updateBy.login = ?#{principal.username}")
    List<DeliveryPackage> findByUpdateByIsCurrentUser();

}
