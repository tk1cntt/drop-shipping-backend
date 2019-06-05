package com.dropshipping.repository;

import com.dropshipping.domain.ShoppingCartItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ShoppingCartItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    @Query("select shoppingCartItem from ShoppingCartItem shoppingCartItem where shoppingCartItem.createBy.login = ?#{principal.username}")
    List<ShoppingCartItem> findByCreateByIsCurrentUser();

    @Query("select shoppingCartItem from ShoppingCartItem shoppingCartItem where shoppingCartItem.updateBy.login = ?#{principal.username}")
    List<ShoppingCartItem> findByUpdateByIsCurrentUser();

}
