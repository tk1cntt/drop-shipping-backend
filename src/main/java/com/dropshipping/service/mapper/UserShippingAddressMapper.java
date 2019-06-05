package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.UserShippingAddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserShippingAddress} and its DTO {@link UserShippingAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, UserMapper.class, CityMapper.class, DistrictMapper.class})
public interface UserShippingAddressMapper extends EntityMapper<UserShippingAddressDTO, UserShippingAddress> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    @Mapping(source = "createBy.id", target = "createById")
    @Mapping(source = "createBy.login", target = "createByLogin")
    @Mapping(source = "updateBy.id", target = "updateById")
    @Mapping(source = "updateBy.login", target = "updateByLogin")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "district.id", target = "districtId")
    UserShippingAddressDTO toDto(UserShippingAddress userShippingAddress);

    @Mapping(source = "userProfileId", target = "userProfile")
    @Mapping(source = "createById", target = "createBy")
    @Mapping(source = "updateById", target = "updateBy")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "districtId", target = "district")
    UserShippingAddress toEntity(UserShippingAddressDTO userShippingAddressDTO);

    default UserShippingAddress fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserShippingAddress userShippingAddress = new UserShippingAddress();
        userShippingAddress.setId(id);
        return userShippingAddress;
    }
}
