package ua.tqs.homework.Controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.ReservationService;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ua.tqs.homework.JsonUtils;



@WebMvcTest(ReservationController.class)
class ReservationControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @MockBean
    SeatService seatService;

    @MockBean
    RouteService routeService;

    Reservation res1, res2, res3, res4;

    Route route;
    Seat seat;

    @BeforeEach
    void setup() {
        res1 = new Reservation();
        res2 = new Reservation();
        res3 = new Reservation();
        res4 = new Reservation();
        res1.setId(1L);
        res1.setAuthToken("1234");
        res2.setId(2L);
        res3.setId(3L);
        res4.setId(4L);

        route = new Route();
        route.setId(1L);
        seat = new Seat();
        seat.setId(1L);

        res1.setRoute(route);
        res1.setSeats(List.of(seat));
        res2.setRoute(route);
        res2.setSeats(List.of(seat));
        res3.setRoute(route);
        res3.setSeats(List.of(seat));
        res4.setRoute(route);
        res4.setSeats(List.of(seat));

        routeService.saveRoute(route);
        seatService.saveSeat(seat);

        reservationService.saveReservation(res1);
        reservationService.saveReservation(res2);
        reservationService.saveReservation(res3);
        reservationService.saveReservation(res4);
    }

    @Test
    void testGetAllLocations() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(List.of(res1, res2, res3, res4));

        mockMvc.perform(get("/reservation")).andExpectAll(status().isOk(),
                jsonPath("$").isArray(),
                jsonPath("$[0].id").value(res1.getId()),
                jsonPath("$[1].id").value(res2.getId()),
                jsonPath("$[2].id").value(res3.getId()),
                jsonPath("$[3].id").value(res4.getId()));

        verify(reservationService, times(1)).getAllReservations();

    }

    @Test
    void testGetReservationById() throws Exception {
        when(reservationService.getReservationDetails(1L)).thenReturn(java.util.Optional.of(res1));

        mockMvc.perform(get("/reservation/1")).andExpectAll(status().isOk(),
                jsonPath("$.id").value(res1.getId()));

        verify(reservationService, times(1)).getReservationDetails(1L);
    }

    @Test
    void testGetReservationByIdNotFound() throws Exception {
        when(reservationService.getReservationDetails(100L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/reservation/100")).andExpectAll(status().isNotFound());

        verify(reservationService, times(1)).getReservationDetails(100L);
    }

    @Test
    void testCreateReservation() throws Exception {
        when(reservationService.saveReservation(any())).thenReturn(java.util.Optional.of(res1));
        when(routeService.getRouteDetails(any())).thenReturn(java.util.Optional.of(route));
        when(seatService.getSeatDetails(any())).thenReturn(java.util.Optional.of(seat));

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(res1))).andExpectAll(
                status().isCreated(),
                jsonPath("$.id").value(res1.getId())
        );
    }

    @Test
    void testCreateReservationBadRequest() throws Exception {
        when(reservationService.saveReservation(any())).thenReturn(java.util.Optional.empty());
        when(routeService.getRouteDetails(any())).thenReturn(java.util.Optional.of(route));
        when(seatService.getSeatDetails(any())).thenReturn(java.util.Optional.of(seat));

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(res1))).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void testCreateReservationRouteNotFound() throws Exception {
        when(reservationService.saveReservation(any())).thenReturn(java.util.Optional.of(res1));
        when(routeService.getRouteDetails(any())).thenReturn(java.util.Optional.empty());
        when(seatService.getSeatDetails(any())).thenReturn(java.util.Optional.of(seat));

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(res1))).andExpectAll(
                status().isBadRequest()
        );
    }

    @Test
    void testCreateReservationSeatNotFound() throws Exception {
        when(reservationService.saveReservation(any())).thenReturn(java.util.Optional.of(res1));
        when(routeService.getRouteDetails(any())).thenReturn(java.util.Optional.of(route));
        when(seatService.getSeatDetails(any())).thenReturn(java.util.Optional.empty());

        mockMvc.perform(post("/reservation").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(res1))).andExpectAll(
                status().isBadRequest()
        );
    }


    @Test
    void testGetReservationByIdAndAuthToken() throws Exception {
        when(reservationService.getReservationDetails(1L)).thenReturn(java.util.Optional.of(res1));

        mockMvc.perform(get("/reservation/1/1234")).andExpectAll(status().isOk(),
                jsonPath("$.id").value(res1.getId()));

        verify(reservationService, times(1)).getReservationDetails(1L);
    }

    @Test
    void testGetReservationByIdAndAuthTokenNotFound() throws Exception {
        when(reservationService.getReservationDetails(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/reservation/1/1234")).andExpectAll(status().isNotFound());

        verify(reservationService, times(1)).getReservationDetails(1L);
    }

    @Test
    void testDeleteReservation() throws Exception {
        doNothing().when(reservationService).deleteReservation(1L);

        mockMvc.perform(delete("/reservation/cancel/1")).andExpectAll(status().isOk());

        verify(reservationService, times(1)).deleteReservation(1L);
        verify(seatService, times(1)).saveSeat(seat);
    }

}
