package com.backend.qualifyng.backendqualifyng.mappers;

import java.util.List;

import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RooMapper.class})
public interface HotelMapper {

	@Mapping(source = "cityName", target = "city")
	HotelDTO toDTO(Hotel hotel);

	@InheritConfiguration
	Hotel toModel(HotelDTO hotelDTO);

	List<HotelDTO> toDtoList(List<Hotel> hotels);
    
}
