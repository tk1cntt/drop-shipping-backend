package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.DeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Delivery} and its DTO {@link DeliveryDTO}.
 */
@Mapper(componentModel = "spring", uses = {WarehouseMapper.class, UserMapper.class})
public interface DeliveryMapper extends EntityMapper<DeliveryDTO, Delivery> {

    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    DeliveryDTO toDto(Delivery delivery);

    @Mapping(target = "packages", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    Delivery toEntity(DeliveryDTO deliveryDTO);

    default Delivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delivery delivery = new Delivery();
        delivery.setId(id);
        return delivery;
    }
}
