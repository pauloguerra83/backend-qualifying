package com.backend.qualifyng.backendqualifyng.helpers;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.dtos.PriceDetailDTO;
import com.backend.qualifyng.backendqualifyng.dtos.RoomDTO;
import com.backend.qualifyng.backendqualifyng.responses.Hotel;
import com.backend.qualifyng.backendqualifyng.responses.PriceDetail;
import com.backend.qualifyng.backendqualifyng.responses.Room;

import org.springframework.stereotype.Component;

@Component
public class DailyCalculationHelper {

     
    public void calculateDays(List<Hotel> hotels, LocalDate checkInDate, LocalDate checkOutDate) {

        int calcDays = Period.between(checkInDate, checkOutDate).getDays();

        System.out.println("Total dias " + calcDays);

        hotels.stream().forEach(h-> calculateRooms(h.getRooms(), calcDays));
    }


    public void calculateTotalPrice(List<HotelDTO> hotels) {
        hotels.stream()
            .forEach(h-> h.getRooms()
            .forEach(r -> totalPrice(r, r.getPriceDetail()))
         );
    }


    private void calculateRooms(List<Room> rooms, Integer qdtDays){
         rooms.stream().map(p -> multiplyDays(p.getPrice(), qdtDays) ).forEach(System.out::println);
    }

    private PriceDetail multiplyDays(PriceDetail p, Integer qdtDays){

        int adultPrice = (p.getAdult() * qdtDays);
        int childPrice = (p.getChild() * qdtDays);

        p.setAdult(adultPrice + Double.valueOf((adultPrice) * 0.07).intValue());
        p.setChild(childPrice + Double.valueOf((childPrice) * 0.07).intValue());
        
        return p;
   }

   private void totalPrice(RoomDTO roomDTO, PriceDetailDTO priceDetailDTO){
        roomDTO.setTotalPrice(priceDetailDTO.getPricePerDayAdult() + priceDetailDTO.getPricePerDayChild());
   }
    
}
