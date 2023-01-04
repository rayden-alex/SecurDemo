package com.example.securdemo.mapper;

import com.example.securdemo.dto.PetTypeDto;
import com.example.securdemo.model.PetType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetTypeMapper {
    PetType toEntity(final PetTypeDto petTypeDto);

    PetTypeDto toDto(final PetType petType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PetType partialUpdate(final PetTypeDto petTypeDto, @MappingTarget final PetType petType);
}