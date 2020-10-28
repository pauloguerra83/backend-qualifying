package com.backend.qualifyng.backendqualifyng.resources;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.qualifyng.backendqualifyng.responses.Hotel;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated 
public class HotelResource {

    @GetMapping("/hotels")
    public ResponseEntity<Hotel> getHotels( @RequestParam(required = true) @NotBlank String cityId,
                                            @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate, 
                                            @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
                                            @RequestParam(required = true) @NotBlank String numberOfAdults,
                                            @RequestParam(required = true) @NotBlank String numberOfChildren) {

        Hotel hotel = Hotel.builder().city(cityId).id(3432424).name("hotel cvc name").build();
        return ResponseEntity.ok().body(hotel);
    }

}
