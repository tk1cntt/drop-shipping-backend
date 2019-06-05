package com.dropshipping.repository;

import com.dropshipping.domain.OrderCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the OrderCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {

    @Query("select orderCart from OrderCart orderCart where orderCart.buyer.login = ?#{principal.username}")
    List<OrderCart> findByBuyerIsCurrentUser();

    @Query("select orderCart from OrderCart orderCart where orderCart.chinaStocker.login = ?#{principal.username}")
    List<OrderCart> findByChinaStockerIsCurrentUser();

    @Query("select orderCart from OrderCart orderCart where orderCart.vietnamStocker.login = ?#{principal.username}")
    List<OrderCart> findByVietnamStockerIsCurrentUser();

    @Query("select orderCart from OrderCart orderCart where orderCart.exporter.login = ?#{principal.username}")
    List<OrderCart> findByExporterIsCurrentUser();

    @Query("select orderCart from OrderCart orderCart where orderCart.createBy.login = ?#{principal.username}")
    List<OrderCart> findByCreateByIsCurrentUser();

    @Query("select orderCart from OrderCart orderCart where orderCart.updateBy.login = ?#{principal.username}")
    List<OrderCart> findByUpdateByIsCurrentUser();

}
