package com.backend.qualifyng.backendqualifyng.services;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.helpers.DailyCalculationHelper;
import com.backend.qualifyng.backendqualifyng.integration.HotelIntegration;
import com.backend.qualifyng.backendqualifyng.mappers.HotelMapper;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelService {

    private final HotelIntegration hotelIntegration;

    private final HotelMapper hotelMapper;

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

        return calculateAndConvertObject(checkInDate, checkOutDate, hotels);

    }

    private List<HotelDTO> calculateAndConvertObject(LocalDate checkInDate, LocalDate checkOutDate, List<Hotel> hotels) {
        dailyCalculationHelper.calculateDays(hotels, checkInDate, checkOutDate);

        List<HotelDTO> hotelDTOs = hotelMapper.toDtoList(hotels);

        dailyCalculationHelper.calculateTotalPrice(hotelDTOs);
        return hotelDTOs;
    }


    public List<HotelDTO> getHotels(List<String> cityList, LocalDate checkInDate, LocalDate checkOutDate,
            String numberOfAdults, String numberOfChildren) throws InterruptedException, ExecutionException {

        return calculateAndConvertObject(checkInDate, checkOutDate, getHotelsCacheable(cityList));

    }

    @Cacheable("hotels")
    @CachePut(value = "hotels", condition = "#noCache", key = "'ALL'")
    private List<Hotel> getHotelsCacheable(List<String> cityList) throws InterruptedException, ExecutionException {
       log.info("chamando metodo do cache");
        return hotelIntegration.getHotels(cityList);
    }


}
