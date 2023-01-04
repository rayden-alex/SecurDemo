package com.example.securdemo.mapper;

import com.example.securdemo.dto.VisitDto;
import com.example.securdemo.model.Visit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisitMapper {
    Visit toEntity(final VisitDto visitDto);

    VisitDto toDto(final Visit visit);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Visit partialUpdate(final VisitDto visitDto, @MappingTarget final Visit visit);
}