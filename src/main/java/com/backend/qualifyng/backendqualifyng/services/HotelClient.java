package com.backend.qualifyng.backendqualifyng.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HotelClient {

    private RestTemplate restTemplate;

    @Autowired
    public HotelClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<Hotel>> getHotel(String hotelId) {

        return restTemplate.exchange("https://cvcbackendhotel.herokuapp.com/hotels/{hotelId}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, hotelId);

    }

    @Async
    public CompletableFuture<List<Hotel>> getHotelsByCity(String idCity) {

        System.out.println(LocalDateTime.now() + " execucao chamada cidade " + idCity);

        ResponseEntity<List<Hotel>> response = restTemplate.exchange(
                "https://cvcbackendhotel.herokuapp.com/hotels/avail/{idCity}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Hotel>>() {
                }, idCity);

        return CompletableFuture.completedFuture(response.getBody());

    }

    public List<Hotel> getHotels(List<String> idCity) {

        CompletableFuture<List<Hotel>> hotelsByRJ = getHotelsByCity(idCity.get(0));
        CompletableFuture<List<Hotel>> hotelsByBA = getHotelsByCity(idCity.get(1));
        CompletableFuture<List<Hotel>> hotelsBySP = getHotelsByCity(idCity.get(2));
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

}
