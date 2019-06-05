package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.ShoppingCartItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCartItem} and its DTO {@link ShoppingCartItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ShoppingCartMapper.class, UserMapper.class})
public interface ShoppingCartItemMapper extends EntityMapper<ShoppingCartItemDTO, ShoppingCartItem> {

    @Mapping(source = "shoppingCart.id", target = "shoppingCartId")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    ShoppingCartItemDTO toDto(ShoppingCartItem shoppingCartItem);

    @Mapping(source = "shoppingCartId", target = "shoppingCart")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    ShoppingCartItem toEntity(ShoppingCartItemDTO shoppingCartItemDTO);

    default ShoppingCartItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(id);
        return shoppingCartItem;
    }
}
