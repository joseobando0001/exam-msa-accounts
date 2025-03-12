package com.pichincha.exam.accounts.helper;

import com.pichincha.exam.accounts.domain.entity.Movement;
import com.pichincha.exam.models.MovementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovementMapper {

    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    @Mappings({
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "amount", source = "value"),
    })
    Movement movementDtoToEntity(MovementRequest movementRequest);


}


