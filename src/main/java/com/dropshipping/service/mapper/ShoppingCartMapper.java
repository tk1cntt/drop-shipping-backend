package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.ShoppingCartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCart} and its DTO {@link ShoppingCartDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class})
public interface ShoppingCartMapper extends EntityMapper<ShoppingCartDTO, ShoppingCart> {

    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    ShoppingCartDTO toDto(ShoppingCart shoppingCart);

    @Mapping(target = "items", ignore = true)
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);

    default ShoppingCart fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
    }
}
