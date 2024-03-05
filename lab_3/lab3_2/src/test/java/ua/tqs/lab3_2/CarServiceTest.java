package ua.tqs.lab3_2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarManagerService carManagerService;

    @Mock
    private CarRepository carRepository;

    private Car car1, car2, car3, car4;

    @BeforeEach
    public void setUp() {
        car1 = new Car("maker1", "model1", 1L);
        car2 = new Car("maker2", "model2", 2L);
        car3 = new Car("maker3", "model3", 3L);
        car4 = new Car("maker2", "model4", 4L);
    }

    @Test
    public void testSaveCar() {
        when(carRepository.save(any())).thenReturn(car1);

        Car savedCar = carManagerService.saveCar(car1);

        assertEquals(car1, savedCar);
        verify(carRepository, times(1)).save(any());
    }


    @Test
    public void testGetAllCars() {

        when(carRepository.findAll()).thenReturn(List.of(car1,car2,car3));

        List<Car> c_list = carManagerService.getAllCars();

        assertEquals(List.of(car1,car2,car3),c_list);
        verify(carRepository, times(1)).findAll();
    }


    @Test
    public void testGetCarDetails() {
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car1));

        Optional<Car> expected = carManagerService.getCarDetails(1L);

        assertEquals(java.util.Optional.of(car1),expected);
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    public void givenCarMakerWith2Cars_thenReturn2Cars() {
        when(carRepository.findByMaker("maker2")).thenReturn(List.of(car2, car4));

        List<Car> returnedCars = carManagerService.getCarByMaker("maker2");

        assertThat(returnedCars).contains(car2, car4).hasSize(2);
        assertThat(returnedCars).extracting(Car::getMaker).allMatch(m -> m.equals("maker2"));

        verify(carRepository, times(1)).findByMaker("maker2");
    }

}
