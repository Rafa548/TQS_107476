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
import ua.tqs.homework.Entities.Stop;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class StopControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testGetAllStops() {
        ResponseEntity<List<Stop>> response = testRestTemplate.exchange(
                "/stops",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stop>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Stop> locationResponse = response.getBody();
        assertEquals(5, locationResponse.size());
    }

}
