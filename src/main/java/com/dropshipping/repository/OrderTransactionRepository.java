package com.dropshipping.repository;

import com.dropshipping.domain.OrderTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {

    @Query("select orderTransaction from OrderTransaction orderTransaction where orderTransaction.approver.login = ?#{principal.username}")
    List<OrderTransaction> findByApproverIsCurrentUser();

    @Query("select orderTransaction from OrderTransaction orderTransaction where orderTransaction.createBy.login = ?#{principal.username}")
    List<OrderTransaction> findByCreateByIsCurrentUser();

    @Query("select orderTransaction from OrderTransaction orderTransaction where orderTransaction.updateBy.login = ?#{principal.username}")
    List<OrderTransaction> findByUpdateByIsCurrentUser();

}
