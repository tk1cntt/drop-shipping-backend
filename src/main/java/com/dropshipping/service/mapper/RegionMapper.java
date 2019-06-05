package com.dropshipping.service.mapper;

import com.dropshipping.domain.*;
import com.dropshipping.service.dto.RegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {


    @Mapping(target = "countries", ignore = true)
    Region toEntity(RegionDTO regionDTO);

    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}
