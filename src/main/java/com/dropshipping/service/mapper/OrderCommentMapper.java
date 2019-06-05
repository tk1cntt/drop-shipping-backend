package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.OrderCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderComment} and its DTO {@link OrderCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderCartMapper.class})
public interface OrderCommentMapper extends EntityMapper<OrderCommentDTO, OrderComment> {

    @Mapping(source = "orderCart.id", target = "orderCartId")
    OrderCommentDTO toDto(OrderComment orderComment);

    @Mapping(source = "orderCartId", target = "orderCart")
    OrderComment toEntity(OrderCommentDTO orderCommentDTO);

    default OrderComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderComment orderComment = new OrderComment();
        orderComment.setId(id);
        return orderComment;
    }
}
