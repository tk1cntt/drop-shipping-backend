package com.dropshipping.repository;

import com.dropshipping.domain.OrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select orderItem from OrderItem orderItem where orderItem.createBy.login = ?#{principal.username}")
    List<OrderItem> findByCreateByIsCurrentUser();

    @Query("select orderItem from OrderItem orderItem where orderItem.updateBy.login = ?#{principal.username}")
    List<OrderItem> findByUpdateByIsCurrentUser();

}
