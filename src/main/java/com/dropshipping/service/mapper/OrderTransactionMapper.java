package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.OrderTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderTransaction} and its DTO {@link OrderTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderCartMapper.class, UserMapper.class})
public interface OrderTransactionMapper extends EntityMapper<OrderTransactionDTO, OrderTransaction> {

    @Mapping(source = "orderCart.id", target = "orderCartId")
    @Mapping(source = "approver.id", target = "approverId")
    @Mapping(source = "approver.login", target = "approverLogin")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    OrderTransactionDTO toDto(OrderTransaction orderTransaction);

    @Mapping(source = "orderCartId", target = "orderCart")
    @Mapping(source = "approverId", target = "approver")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    OrderTransaction toEntity(OrderTransactionDTO orderTransactionDTO);

    default OrderTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderTransaction orderTransaction = new OrderTransaction();
        orderTransaction.setId(id);
        return orderTransaction;
    }
}
