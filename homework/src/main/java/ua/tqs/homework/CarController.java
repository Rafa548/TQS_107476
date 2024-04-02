package ua.tqs.homework;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

	private CarManagerService carManagerService;

	public CarController(CarManagerService carManagerService) {
		this.carManagerService = carManagerService;
	}


	@PostMapping("/car")
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		HttpStatus status = HttpStatus.CREATED;
		Car saved_car = carManagerService.saveCar(car);
		if (car == null) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(saved_car, status);
	}

	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getAllCars() {
		HttpStatus status = HttpStatus.OK;
		List<Car> cars = carManagerService.getAllCars();
		return new ResponseEntity<>(cars, status);
	}

	@GetMapping("/car/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		HttpStatus status = HttpStatus.OK;
		Car car = carManagerService.getCarDetails(id).orElse(null);
		if (car == null)
			return ResponseEntity.notFound().build();
		return new ResponseEntity<>(car, status);
	}


}
