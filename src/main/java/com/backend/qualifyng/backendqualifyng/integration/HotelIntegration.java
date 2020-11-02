package com.backend.qualifyng.backendqualifyng.integration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.backend.qualifyng.backendqualifyng.configurations.RestConfiguration;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

        return restTemplate.exchange(mountUrlHotelsById(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, hotelId);

    }

    @Async
    private CompletableFuture<List<Hotel>> getHotelsByCity(String idCity) {

        System.out.println(LocalDateTime.now() + " execucao chamada cidade " + idCity);

        ResponseEntity<List<Hotel>> response = restTemplate.exchange(mountUrlHotelsByCity(), 
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, idCity);

        return CompletableFuture.completedFuture(response.getBody());

    }
  

    public List<Hotel> getHotels(List<String> cityList) {

        CompletableFuture<List<Hotel>> hotelsByRJ = getHotelsByCity(cityList.get(0));
        CompletableFuture<List<Hotel>> hotelsByBA = getHotelsByCity(cityList.get(1));
        CompletableFuture<List<Hotel>> hotelsBySP = getHotelsByCity(cityList.get(2));
        List<Hotel> hotelsByCity = null;
        try {
            
            hotelsByCity = hotelsByRJ.get().stream().collect(Collectors.toList());

            hotelsByCity.addAll(hotelsByBA.get().stream().collect(Collectors.toList()));

            hotelsByCity.addAll(hotelsBySP.get().stream().collect(Collectors.toList()));


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return hotelsByCity;

    }

    private String mountUrlHotelsByCity() {
        String urlHotels = new StringBuilder().append(restConfiguration.getUrlHotels())
                            .append(restConfiguration.getPathCity())
                            .append("{idCity}").toString();
        return urlHotels;
    }

    private String mountUrlHotelsById() {
        String urlHotels = new StringBuilder().append(restConfiguration.getUrlHotels())
                            .append("{hotelId}").toString();
        return urlHotels;
    }

}
