package com.backend.qualifyng.backendqualifyng.resources;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.qualifyng.backendqualifyng.responses.Hotel;
import com.backend.qualifyng.backendqualifyng.services.HotelClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated 
public class HotelResource {

    private HotelClient hotelClient;

    @Autowired
    public HotelResource(HotelClient hotelClient){
        this.hotelClient = hotelClient;
    }

    @GetMapping("/hotels/{hotelID}")
    public ResponseEntity<List<Hotel>> getHotel(@PathVariable(name = "hotelID") Integer hotelID,
                                                @RequestParam(required = true) @NotNull List<String> cityId,
                                                @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate, 
                                                @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
                                                @RequestParam(required = true) @NotBlank String numberOfAdults,
                                                @RequestParam(required = true) @NotBlank String numberOfChildren) {

        ResponseEntity<List<Hotel>> response = hotelClient.getHotel(hotelID.toString());

        return ResponseEntity.ok().body(response.getBody());

    }

    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getHotels(@RequestParam(required = true) @NotNull List<String> cityId,
                                                 @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate, 
                                                 @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
                                                 @RequestParam(required = true) @NotBlank String numberOfAdults,
                                                 @RequestParam(required = true) @NotBlank String numberOfChildren) {

        List<Hotel> response = hotelClient.getHotels(cityId);

        return ResponseEntity.ok().body(response);

    }

}
