package ua.tqs.lab3_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    private CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        Car s_car = carRepository.save(car);
        return s_car;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getCarByMaker(String maker) {
        return carRepository.findByMaker(maker);
    }
}
