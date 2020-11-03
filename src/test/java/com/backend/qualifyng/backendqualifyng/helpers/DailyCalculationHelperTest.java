package com.backend.qualifyng.backendqualifyng.helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.backend.qualifyng.backendqualifyng.BackendQualifyngApplication;
import com.backend.qualifyng.backendqualifyng.dtos.CategoryDTO;
import com.backend.qualifyng.backendqualifyng.dtos.HotelDTO;
import com.backend.qualifyng.backendqualifyng.dtos.PriceDetailDTO;
import com.backend.qualifyng.backendqualifyng.dtos.RoomDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendQualifyngApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DailyCalculationHelperTest {

	@Autowired
	DailyCalculationHelper dailyCalculationHelper;

    @Order(1)
	@Test
	public void calculateTotalPriceTest() {

		final Integer pricePerDayAdult = 1000;
		final Integer pricePerDayChild = 2000;
		final Integer totalPriceExpected = 3000;

		List<HotelDTO> hotels = List.of(HotelDTO.builder()
										.id(1).city("Porto Seguro").name("Hotel Teste 1")
										.rooms(List.of(RoomDTO.builder()
															.id(0)
															.category(CategoryDTO.builder()
																	.name("Standard")
																	.build())
															.priceDetail(PriceDetailDTO.builder()
																		.pricePerDayAdult(pricePerDayAdult)
																		.pricePerDayChild(pricePerDayChild)
																	.build())
														.build())
												)
										.build());


		dailyCalculationHelper.calculateTotalPrice(hotels);

		Optional<HotelDTO> hotelOpt = hotels.stream().findFirst();

		assertTrue(hotelOpt.isPresent());

		if ( hotelOpt.isPresent() ) {

			Optional<RoomDTO> roomOpt = hotelOpt.get().getRooms().stream().findFirst();

			assertTrue(roomOpt.isPresent());

			if (roomOpt.isPresent()) {

				log.info("\n\n TotalPriceExpected: {} \n TotalPrice: {} \n",totalPriceExpected,roomOpt.get().getTotalPrice());

				assertEquals(roomOpt.get().getTotalPrice(), totalPriceExpected);

			}
		}
	}

}
