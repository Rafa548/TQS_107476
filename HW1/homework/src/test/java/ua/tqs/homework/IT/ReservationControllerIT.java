package ua.tqs.homework.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ua.tqs.homework.Entities.Reservation;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ReservationControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    // i having trouble doing the reservation by hand
    void testGetAllLocations() {
        ResponseEntity<List<Reservation>> response = testRestTemplate.exchange(
                "/reservation",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Reservation>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Reservation> locationResponse = response.getBody();
        assertEquals(0, locationResponse.size());
    }


}
