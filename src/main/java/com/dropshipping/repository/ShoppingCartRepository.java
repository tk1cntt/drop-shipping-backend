package com.dropshipping.repository;

import com.dropshipping.domain.ShoppingCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("select shoppingCart from ShoppingCart shoppingCart where shoppingCart.createBy.login = ?#{principal.username}")
    List<ShoppingCart> findByCreateByIsCurrentUser();

    @Query("select shoppingCart from ShoppingCart shoppingCart where shoppingCart.updateBy.login = ?#{principal.username}")
    List<ShoppingCart> findByUpdateByIsCurrentUser();

    ShoppingCart findFirstByShopId(String shopId);

}
