package com.dropshipping.repository;

import com.dropshipping.domain.OrderPackageHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderPackageHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderPackageHistoryRepository extends JpaRepository<OrderPackageHistory, Long> {

    @Query("select orderPackageHistory from OrderPackageHistory orderPackageHistory where orderPackageHistory.createBy.login = ?#{principal.username}")
    List<OrderPackageHistory> findByCreateByIsCurrentUser();

    @Query("select orderPackageHistory from OrderPackageHistory orderPackageHistory where orderPackageHistory.updateBy.login = ?#{principal.username}")
    List<OrderPackageHistory> findByUpdateByIsCurrentUser();

}
