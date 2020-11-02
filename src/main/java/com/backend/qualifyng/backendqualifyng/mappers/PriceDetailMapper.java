package com.backend.qualifyng.backendqualifyng.mappers;

import com.backend.qualifyng.backendqualifyng.dtos.PriceDetailDTO;
import com.backend.qualifyng.backendqualifyng.responses.PriceDetail;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceDetailMapper {

    
    @Mapping(source = "adult", target = "pricePerDayAdult")
    @Mapping(source = "child", target = "pricePerDayChild")
    PriceDetailDTO toDTO(PriceDetail room);

    @InheritConfiguration
    PriceDetail toModel(PriceDetailDTO roomDTO);
    
}
