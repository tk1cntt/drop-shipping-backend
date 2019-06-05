package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.DeliveryPackageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryPackage} and its DTO {@link DeliveryPackageDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeliveryMapper.class, UserMapper.class})
public interface DeliveryPackageMapper extends EntityMapper<DeliveryPackageDTO, DeliveryPackage> {

    @Mapping(source = "delivery.id", target = "deliveryId")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    DeliveryPackageDTO toDto(DeliveryPackage deliveryPackage);

    @Mapping(source = "deliveryId", target = "delivery")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    DeliveryPackage toEntity(DeliveryPackageDTO deliveryPackageDTO);

    default DeliveryPackage fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryPackage deliveryPackage = new DeliveryPackage();
        deliveryPackage.setId(id);
        return deliveryPackage;
    }
}
