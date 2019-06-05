package com.dropshipping.repository;

import com.dropshipping.domain.OrderHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    @Query("select orderHistory from OrderHistory orderHistory where orderHistory.createBy.login = ?#{principal.username}")
    List<OrderHistory> findByCreateByIsCurrentUser();

    @Query("select orderHistory from OrderHistory orderHistory where orderHistory.updateBy.login = ?#{principal.username}")
    List<OrderHistory> findByUpdateByIsCurrentUser();

}
