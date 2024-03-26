package ua.tqs.lab3_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CarController.class)
class CarControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private CarManagerService carManagerService;

	private Car car1, car2, car3;

	@BeforeEach
	public void setUp() {
		car1 = new Car("maker1", "model1", 1L);
		car2 = new Car("maker2", "model2", 2L);
		car3 = new Car("maker3", "model3", 3L);
	}


	@Test
	public void createCar() throws Exception {

		when(carManagerService.saveCar(Mockito.any())).thenReturn(car1);

		mockMvc.perform(post("/api/car")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtils.toJson(car1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.maker", is("maker1")));

		verify(carManagerService, times(1)).saveCar(Mockito.any());
	}

	@Test
	public void getAllCars() throws Exception {
		List<Car> cars = List.of(car1, car2);

		when(carManagerService.getAllCars()).thenReturn(cars);

		mockMvc.perform(get("/api/cars"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].maker", is("maker1")))
				.andExpect(jsonPath("$[1].maker", is("maker2")));

		verify(carManagerService, times(1)).getAllCars();
	}

	@Test
	public void getCarById() throws Exception {
		when(carManagerService.getCarDetails(1L)).thenReturn(java.util.Optional.of(car3));

		mockMvc.perform(get("/api/car/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker", is("maker3")));

		verify(carManagerService, times(1)).getCarDetails(1L);
	}

	@Test
	public void testGetInvalidIDCar() throws Exception {
		when(carManagerService.getCarDetails(-1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/car/-1")).andExpect(status().isNotFound()).andExpect(jsonPath("$").doesNotExist());
		verify(carManagerService, times(1)).getCarDetails(-1L);
	}

	@Test
	public void testPostNullCar() throws Exception {
		when(carManagerService.saveCar(null)).thenReturn(null);

		mockMvc.perform(post("/api/car")).andExpect(status().isBadRequest()).andExpect(jsonPath("$").doesNotExist());
	}

}
