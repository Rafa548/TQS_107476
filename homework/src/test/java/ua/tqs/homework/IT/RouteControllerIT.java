package ua.tqs.homework.IT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ua.tqs.homework.Entities.Reservation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RouteControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void testGetAllRoutes() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(3, locationResponse.size());
    }

    @Test
    void testGetRouteById() {
        ResponseEntity<Route> response = testRestTemplate.exchange(
                "/routes/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Route>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Route locationResponse = response.getBody();
        assertEquals(1, locationResponse.getId());
    }

    @Test
    void testGetRouteByIdNotFound() {
        ResponseEntity<Route> response = testRestTemplate.exchange(
                "/routes/10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Route>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Route locationResponse = response.getBody();
        assertNull(locationResponse);
    }

    @Test
    void testGetRouteFromLocationToLocation() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/search/Braga/Coimbra",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(2, locationResponse.size());
    }

    @Test
    void testGetRouteFromLocationToLocationNotFound() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/search/Faro/Braga",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(0, locationResponse.size());
    }

    @Test
    void testGetRouteFromLocation() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/searchDeparture/Porto",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(3, locationResponse.size());
    }

    @Test
    void testGetRouteFromLocationNotFound() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/searchDeparture/Faro",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(0, locationResponse.size());
    }

    @Test
    void testGetRouteToLocation() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/searchArrival/Coimbra",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(2, locationResponse.size());
    }

    @Test
    void testGetRouteToLocationNotFound() {
        ResponseEntity<List<Route>> response = testRestTemplate.exchange(
                "/routes/searchArrival/Porto",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Route>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Route> locationResponse = response.getBody();
        assertEquals(0, locationResponse.size());
    }


}
