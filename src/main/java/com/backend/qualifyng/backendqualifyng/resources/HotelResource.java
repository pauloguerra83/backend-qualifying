package com.backend.qualifyng.backendqualifyng.resources;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.integration.HotelIntegration;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;
import com.backend.qualifyng.backendqualifyng.services.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    private HotelService hotelService;

    public HotelResource(HotelService hotelService){
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/{hotelID}")
    public ResponseEntity<List<HotelDTO>> getHotel( @PathVariable(name = "hotelID") Integer hotelID,
                                                    @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate,
                                                    @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
                                                    @RequestParam(required = true) @NotBlank String numberOfAdults,
                                                    @RequestParam(required = true) @NotBlank String numberOfChildren) {

        List<HotelDTO> hotels = hotelService.getHotel(hotelID.toString(),
                                                                     checkInDate,
                                                                     checkOutDate,
                                                                     numberOfChildren,
                                                                     numberOfChildren);

        return ResponseEntity.ok().body(hotels);

    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(@RequestParam(required = true) @NotNull List<String> cityId,
                                                 @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkInDate, 
                                                 @RequestParam(required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") @NotNull LocalDate checkOutDate,
                                                 @RequestParam(required = true) @NotBlank String numberOfAdults,
                                                 @RequestParam(required = true) @NotBlank String numberOfChildren) {

        List<HotelDTO> response = hotelService.getHotels(cityId,
                                                         checkInDate,
                                                         checkOutDate,
                                                         numberOfChildren,
                                                         numberOfChildren);

        return ResponseEntity.ok().body(response);

    }

}
