package com.backend.qualifyng.backendqualifyng.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.helpers.DailyCalculationHelper;
import com.backend.qualifyng.backendqualifyng.integration.HotelIntegration;
import com.backend.qualifyng.backendqualifyng.mappers.HotelMapper;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private HotelIntegration hotelIntegration;

    private HotelMapper hotelMapper;

    private final DailyCalculationHelper dailyCalculationHelper;

    public HotelService(HotelIntegration hotelIntegration, HotelMapper hotelMapper,
            DailyCalculationHelper dailyCalculationHelper) {
        this.hotelIntegration = hotelIntegration;
        this.hotelMapper = hotelMapper;
        this.dailyCalculationHelper = dailyCalculationHelper;
    }

    public List<HotelDTO> getHotel(String hotelID, LocalDate checkInDate, LocalDate checkOutDate, String numberOfAdults,
            String numberOfChildren) {

        ResponseEntity<List<Hotel>> response = hotelIntegration.getHotel(hotelID);

        List<Hotel> hotels = response.getBody();

        dailyCalculationHelper.calculateDays(hotels, checkInDate, checkOutDate);

        List<HotelDTO> hotelDTOs = convertList(hotels);

        dailyCalculationHelper.calculateTotalPrice(hotelDTOs);

        return hotelDTOs;

    }

    private List<HotelDTO> convertList(List<Hotel> hotels) {

        List<Hotel> hotelList = Optional.ofNullable(hotels).orElseThrow(NullPointerException::new);

        return hotelMapper.toDtoList(hotelList);

    }

    public List<HotelDTO> getHotels(List<String> cityList, LocalDate checkInDate, LocalDate checkOutDate,
            String numberOfAdults, String numberOfChildren) {

        List<Hotel> hotels = hotelIntegration.getHotels(cityList);

        return calcAndConvertObject(checkInDate, checkOutDate, hotels);

    }

    private List<HotelDTO> calcAndConvertObject(LocalDate checkInDate, LocalDate checkOutDate, List<Hotel> hotels) {
        dailyCalculationHelper.calculateDays(hotels, checkInDate, checkOutDate);

        List<HotelDTO> hotelDTOs = convertList(hotels);

        dailyCalculationHelper.calculateTotalPrice(hotelDTOs);
        return hotelDTOs;
    }

}