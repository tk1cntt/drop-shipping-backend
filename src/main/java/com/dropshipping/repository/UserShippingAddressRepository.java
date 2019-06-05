package com.dropshipping.repository;

import com.dropshipping.domain.UserShippingAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserShippingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserShippingAddressRepository extends JpaRepository<UserShippingAddress, Long> {

    @Query("select userShippingAddress from UserShippingAddress userShippingAddress where userShippingAddress.createBy.login = ?#{principal.username}")
    List<UserShippingAddress> findByCreateByIsCurrentUser();

    @Query("select userShippingAddress from UserShippingAddress userShippingAddress where userShippingAddress.updateBy.login = ?#{principal.username}")
    List<UserShippingAddress> findByUpdateByIsCurrentUser();

}
