package ua.tqs.lab3_2;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties")
public class CarControllerIT {

    @LocalServerPort
    int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    void cleanUp() {
        carRepository.deleteAll();
    }

    @Test
    public void ValidCar_thenCreateCar() {
        Car car = new Car("maker1", "model1");
        ResponseEntity<Car> postCar = restTemplate.postForEntity("/api/cars", car, Car.class);

        Optional<Car> returnedCar = carRepository.findById(postCar.getBody().getId());

        assertThat(postCar.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnedCar).isNotEmpty();
        assertThat(returnedCar.get()).isEqualTo(postCar.getBody());
    }

    @Test
    public void InvalidCar_DontCreateCar() {
        Car inv_Car = null;
        restTemplate.postForEntity("/api/cars", inv_Car, Car.class);

        assertThat(carRepository.findAll()).isEmpty();
    }

    @Test
    public void T2Given_2Found() {
        Car car1 = new Car("maker1", "model1");
        Car car2 = new Car("maker1", "model2");
        Car car3 = new Car("maker2", "model3");
        car1 = restTemplate.postForEntity("/api/cars", car1, Car.class).getBody();
        car2 = restTemplate.postForEntity("/api/cars", car2, Car.class).getBody();
        car3 = restTemplate.postForEntity("/api/cars", car3, Car.class).getBody();

        // assert that 3 cars are in database
        assertThat(carRepository.findAll()).hasSize(3).containsOnly(car1, car2, car3);
        // assert that only 2 of them are renaults
        assertThat(carRepository.findByMaker("Renault")).hasSize(2).contains(car1, car2);
    }

}
