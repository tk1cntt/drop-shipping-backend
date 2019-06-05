package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.OrderHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderHistory} and its DTO {@link OrderHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderCartMapper.class, UserMapper.class})
public interface OrderHistoryMapper extends EntityMapper<OrderHistoryDTO, OrderHistory> {

    @Mapping(source = "orderCart.id", target = "orderCartId")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    OrderHistoryDTO toDto(OrderHistory orderHistory);

    @Mapping(source = "orderCartId", target = "orderCart")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    OrderHistory toEntity(OrderHistoryDTO orderHistoryDTO);

    default OrderHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(id);
        return orderHistory;
    }
}
