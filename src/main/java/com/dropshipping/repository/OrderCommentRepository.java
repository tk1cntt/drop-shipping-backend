package com.dropshipping.repository;

import com.dropshipping.domain.OrderComment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderCommentRepository extends JpaRepository<OrderComment, Long> {

}
