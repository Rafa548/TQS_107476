package ua.tqs.homework.Controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;
import java.util.Optional;


@WebMvcTest(RouteController.class)
class RouteControllerMockServiceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RouteService routeService;

    @MockBean
    SeatService seatService;

    Route route1, route2, route3;

    Seat seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;

    @BeforeEach
    void setup() {
        route1 = new Route();
        route2 = new Route();
        route3 = new Route();
        route1.setId(1L);
        route2.setId(2L);
        route3.setId(3L);

        routeService.saveRoute(route1);
        routeService.saveRoute(route2);
        routeService.saveRoute(route3);

        seat1 = new Seat();
        seat2 = new Seat();
        seat3 = new Seat();
        seat4 = new Seat();
        seat5 = new Seat();
        seat6 = new Seat();

        seat1.setId(1L);
        seat2.setId(2L);
        seat3.setId(3L);
        seat4.setId(4L);
        seat5.setId(5L);
        seat6.setId(6L);
    }

    @Test
    void testGetAllRoutes() throws Exception {
        when(routeService.getAllRoutes()).thenReturn(List.of(route1, route2, route3));

        mockMvc.perform(get("/routes")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(route1.getId()),
                jsonPath("$[1].id").value(route2.getId()),
                jsonPath("$[2].id").value(route3.getId()));


        verify(routeService, times(1)).getAllRoutes();
    }

    @Test
    void testGetRouteById() throws Exception {
        when(routeService.getRouteDetails(1L)).thenReturn(java.util.Optional.of(route1));

        mockMvc.perform(get("/routes/1")).andExpectAll(status().isOk(),
                jsonPath("$.id").value(route1.getId()));

        verify(routeService, times(1)).getRouteDetails(1L);
    }

    @Test
    void testGetRouteByIdNotFound() throws Exception {
        when(routeService.getRouteDetails(100L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/routes/100")).andExpectAll(status().isBadRequest());

        verify(routeService, times(1)).getRouteDetails(100L);
    }


    @Test
    void testGetRouteFromLocationToLocation() throws Exception {
        when(routeService.searchRoutes("Porto", "Braga")).thenReturn(List.of(route1, route2, route3));

        mockMvc.perform(get("/routes/search/Porto/Braga")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(route1.getId()),
                jsonPath("$[1].id").value(route2.getId()),
                jsonPath("$[2].id").value(route3.getId()));

        verify(routeService, times(1)).searchRoutes("Porto", "Braga");
    }

    @Test
    void testGetRouteFromLocationToLocationNotFound() throws Exception {
        when(routeService.searchRoutes("Porto", "Faro")).thenReturn(List.of());

        mockMvc.perform(get("/routes/search/Porto/Faro")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$").isEmpty());

        verify(routeService, times(1)).searchRoutes("Porto", "Faro");
    }

    @Test
    void testGetRouteFromLocation() throws Exception {
        when(routeService.searchRoutesOrigin("Porto")).thenReturn(List.of(route1, route2, route3));

        mockMvc.perform(get("/routes/searchDeparture/Porto")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(route1.getId()),
                jsonPath("$[1].id").value(route2.getId()),
                jsonPath("$[2].id").value(route3.getId()));

        verify(routeService, times(1)).searchRoutesOrigin("Porto");
    }

    @Test
    void testGetRouteFromLocationNotFound() throws Exception {
        when(routeService.searchRoutesOrigin("Faro")).thenReturn(List.of());

        mockMvc.perform(get("/routes/searchDeparture/Faro")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$").isEmpty());

        verify(routeService, times(1)).searchRoutesOrigin("Faro");
    }

    @Test
    void testGetRouteToLocation() throws Exception {
        when(routeService.searchRoutesDestination("Porto")).thenReturn(List.of(route1, route2, route3));

        mockMvc.perform(get("/routes/searchArrival/Porto")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(route1.getId()),
                jsonPath("$[1].id").value(route2.getId()),
                jsonPath("$[2].id").value(route3.getId()));

        verify(routeService, times(1)).searchRoutesDestination("Porto");
    }

    @Test
    void testGetRouteToLocationNotFound() throws Exception {
        when(routeService.searchRoutesDestination("Faro")).thenReturn(List.of());

        mockMvc.perform(get("/routes/searchArrival/Faro")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$").isEmpty());

        verify(routeService, times(1)).searchRoutesDestination("Faro");
    }


    @Test
    void testGetSeatsAvailable() throws Exception {
        when(routeService.getRouteSeats(1L)).thenReturn(List.of(seat1, seat2, seat3, seat4, seat5, seat6));

        mockMvc.perform(get("/routes/1/seats")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").exists(),
                jsonPath("$[1].id").exists(),
                jsonPath("$[2].id").exists(),
                jsonPath("$[3].id").exists(),
                jsonPath("$[4].id").exists(),
                jsonPath("$[5].id").exists());

        verify(routeService, times(1)).getRouteSeats(1L);
    }



}
