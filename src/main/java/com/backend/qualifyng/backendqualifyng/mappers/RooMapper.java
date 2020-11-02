package com.backend.qualifyng.backendqualifyng.mappers;

import com.backend.qualifyng.backendqualifyng.dtos.RoomDTO;
import com.backend.qualifyng.backendqualifyng.responses.Room;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PriceDetailMapper.class})
public interface RooMapper {

    @Mapping(source = "roomID", target = "id")
    @Mapping(source = "categoryName", target = "category.name")
    @Mapping(source = "price", target = "priceDetail")
    RoomDTO toDTO(Room room);

    @InheritConfiguration
    Room toModel(RoomDTO roomDTO);


    
}
