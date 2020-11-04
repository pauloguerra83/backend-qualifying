package com.backend.qualifyng.backendqualifyng.resources;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.backend.qualifyng.backendqualifyng.BackendQualifyngApplication;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendQualifyngApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelResourceIntegrationTest {

	@Autowired
    Environment env;

    @LocalServerPort
    private int localServerPort;

    @Value("${HotelResourceIntegrationTest.protocol}")
    private String protocol;

    @Value("${HotelResourceIntegrationTest.host}")
    private String host;

    @Value("${HotelResourceIntegrationTest.port}")
    private int port;

    @BeforeAll
    public static void setup() {

        log.info("\n\n Setup HotelResourceIntegrationTest \n");
        RestAssured.basePath = "/api/v1";
    }



	@Test
	public void expectHotels200() {

		RestAssured.baseURI = protocol + host;
		if (env.acceptsProfiles(Profiles.of("hml"))) {
			RestAssured.port = port;
		} else {
			RestAssured.port = localServerPort;
		}

		log.info("RestAssured.baseURI: {}",RestAssured.baseURI);
		log.info("RestAssured.basePath: {}",RestAssured.basePath);
		log.info("RestAssured.port: {}",RestAssured.port);

		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", ContentType.APPLICATION_JSON.toString());

		String path = "/hotels";
		given()
				.when()
				.headers(headers)
				.queryParam("cityId", "1032,7110,9626")
				.queryParam("checkInDate", "10/10/2020")
				.queryParam("checkOutDate", "15/10/2020")
				.queryParam("numberOfAdults", 2)
				.queryParam("numberOfChildren", 1)
				.get(path)
				.then()
				.log().all()
				.assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void expectHotelsById200() {

		RestAssured.baseURI = protocol + host;
		if (env.acceptsProfiles(Profiles.of("hml"))) {
			RestAssured.port = port;
		} else {
			RestAssured.port = localServerPort;
		}

		log.info("RestAssured.baseURI: {}",RestAssured.baseURI);
		log.info("RestAssured.basePath: {}",RestAssured.basePath);
		log.info("RestAssured.port: {}",RestAssured.port);

		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", ContentType.APPLICATION_JSON.toString());

		String path = "/hotels/{hotelID}";
		given()
				.when()
				.headers(headers)
				.pathParam("hotelID", "200")
				.queryParam("checkInDate", "10/10/2020")
				.queryParam("checkOutDate", "15/10/2020")
				.queryParam("numberOfAdults", 2)
				.queryParam("numberOfChildren", 1)
				.get(path)
				.then()
				.log().all()
				.assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void expectHotelsById404() {

		RestAssured.baseURI = protocol + host;
		if (env.acceptsProfiles(Profiles.of("hml"))) {
			RestAssured.port = port;
		} else {
			RestAssured.port = localServerPort;
		}

		log.info("RestAssured.baseURI: {}",RestAssured.baseURI);
		log.info("RestAssured.basePath: {}",RestAssured.basePath);
		log.info("RestAssured.port: {}",RestAssured.port);

		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", ContentType.APPLICATION_JSON.toString());

		String path = "/hotels/{hotelID}";
		given()
				.when()
				.headers(headers)
				.pathParam("hotelID", "200")
				.queryParam("checkInDate", "10/10/2020")
				.queryParam("checkOutDate", "15/10/2020")
				.queryParam("numberOfAdults", 2)
				.queryParam("numberOfChildren", 1)
				.get(path)
				.then()
				.log().all()
				.assertThat().statusCode(HttpStatus.OK.value());
	}

}
