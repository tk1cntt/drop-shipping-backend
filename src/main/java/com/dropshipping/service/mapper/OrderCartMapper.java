package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.OrderCartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderCart} and its DTO {@link OrderCartDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrderCartMapper extends EntityMapper<OrderCartDTO, OrderCart> {

    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "buyer.login", target = "buyerLogin")
    @Mapping(source = "chinaStocker.id", target = "chinaStockerId")
    @Mapping(source = "chinaStocker.login", target = "chinaStockerLogin")
    @Mapping(source = "vietnamStocker.id", target = "vietnamStockerId")
    @Mapping(source = "vietnamStocker.login", target = "vietnamStockerLogin")
    @Mapping(source = "exporter.id", target = "exporterId")
    @Mapping(source = "exporter.login", target = "exporterLogin")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    OrderCartDTO toDto(OrderCart orderCart);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "packages", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(source = "buyerId", target = "buyer")
    @Mapping(source = "chinaStockerId", target = "chinaStocker")
    @Mapping(source = "vietnamStockerId", target = "vietnamStocker")
    @Mapping(source = "exporterId", target = "exporter")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    OrderCart toEntity(OrderCartDTO orderCartDTO);

    default OrderCart fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderCart orderCart = new OrderCart();
        orderCart.setId(id);
        return orderCart;
    }
}
