package com.backend.qualifyng.backendqualifyng.integration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.backend.qualifyng.backendqualifyng.configurations.RestConfiguration;
import com.backend.qualifyng.backendqualifyng.exceptions.ResourceNotFoundException;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HotelIntegration {

    private RestTemplate restTemplate;

    private RestConfiguration restConfiguration;

    @Autowired
    public HotelIntegration(RestTemplate restTemplate, RestConfiguration restConfiguration) {
        this.restTemplate = restTemplate;
        this.restConfiguration = restConfiguration;
    }

    public ResponseEntity<List<Hotel>> getHotel(String hotelId) {

        ResponseEntity<List<Hotel>> hotels =  restTemplate.exchange(mountUrlHotelsById(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, hotelId);

        if (hotels.getBody().isEmpty()){
            throw new ResourceNotFoundException();
        }
        
        return hotels;
    }

    @Async
    private CompletableFuture<List<Hotel>> getHotelsByCity(String idCity) {

        log.info(" execucao chamada cidade {} ", LocalDateTime.now());

        ResponseEntity<List<Hotel>> response = restTemplate.exchange(mountUrlHotelsByCity(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, idCity);

        return CompletableFuture.completedFuture(response.getBody());

    }

    public List<Hotel> getHotels(List<String> cityList) throws InterruptedException, ExecutionException {

        List<Hotel> hotelsByCity = new ArrayList<>();

        for (String cityId : cityList) {
            CompletableFuture<List<Hotel>> hotels = getHotelsByCity(cityId);
            hotelsByCity.addAll(hotels.get().stream().collect(Collectors.toList()));
        }

        return hotelsByCity;

    }

    private String mountUrlHotelsByCity() {
        String urlHotels = new StringBuilder().append(restConfiguration.getUrlHotels())
                .append(restConfiguration.getPathCity()).append("{idCity}").toString();
        return urlHotels;
    }

    private String mountUrlHotelsById() {
        String urlHotels = new StringBuilder().append(restConfiguration.getUrlHotels()).append("{hotelId}").toString();
        return urlHotels;
    }

}
