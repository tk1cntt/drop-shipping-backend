package com.dropshipping.repository;

import com.dropshipping.domain.Delivery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Delivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("select delivery from Delivery delivery where delivery.createBy.login = ?#{principal.username}")
    List<Delivery> findByCreateByIsCurrentUser();

    @Query("select delivery from Delivery delivery where delivery.updateBy.login = ?#{principal.username}")
    List<Delivery> findByUpdateByIsCurrentUser();

}
