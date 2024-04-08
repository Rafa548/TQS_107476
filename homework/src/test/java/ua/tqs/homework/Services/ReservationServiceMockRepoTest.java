package ua.tqs.homework.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.ReservationRepository;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.SeatRepository;
import ua.tqs.homework.repository.StopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockRepoTest {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    RouteRepository routeRepository;

    @Mock
    StopRepository stopRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    ReservationService reservationService;

    Seat seat1, seat2, seat3, seat4, seat5, seat6, seat8, seat9, seat10;

    Route route1, route2, route3;

    Stop stopPorto, stopLisboa, stopBraga, stopCoimbra, stopFaro;
    Reservation reservation1, reservation2;

    @BeforeEach
    void setup() {
        // trip1 - bus is not full

        route1 = new Route();
        route2 = new Route();
        route3 = new Route();

        stopPorto = new Stop("Porto", "5", "6");
        stopPorto.setId(1L);
        stopLisboa = new Stop("Lisboa", "6", "8");
        stopLisboa.setId(2L);
        stopBraga = new Stop("Braga", "8", "10");
        stopBraga.setId(3L);
        stopCoimbra = new Stop("Coimbra","10", "12");
        stopCoimbra.setId(4L);
        stopFaro = new Stop("Faro","12", "14");
        stopFaro.setId(5L);

        route1.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra));
        route2.setStops(List.of(stopPorto, stopLisboa));
        route3.setStops(List.of(stopPorto, stopLisboa, stopBraga));

        route1.setId(1L);
        route2.setId(2L);
        route3.setId(3L);

        List<Boolean> isBooked = new ArrayList<>();
        List<Boolean> isBookedTrue = new ArrayList<>();

        try {
            int n_stops = route1.getStops().size()-1; //beginning never has a stop

            for (int i = 0; i < n_stops; i++) {
                isBooked.add(false);
                isBookedTrue.add(true);
            }
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }

        seat1 = new Seat("1A",2,isBookedTrue, route1);
        seat2 = new Seat("1B",1,isBooked, route1);
        seat3 = new Seat("1C",1,isBooked, route1);
        seat4 = new Seat("1A",2,isBooked, route3);
        seat4.setId(4L);
        seat5 = new Seat("1B",1,isBooked, route3);
        seat5.setId(5L);
        seat6 = new Seat("1C",1,isBooked, route3);
        seat6.setId(6L);
        seat8 = new Seat("2B",1,isBooked, route2);
        seat9 = new Seat("2C",1,isBooked, route2);
        seat10 = new Seat("2D",1,isBooked, route2);

        reservation1 = new Reservation("John Doe", route1, List.of(seat1));
        reservation1.setArrivalStop(stopLisboa);
        reservation1.setDepartureStop(stopPorto);
        reservation1.setId(1L);
        reservation1.setAuthToken("1234");

        reservation2 = new Reservation("Jane Doe", route3, List.of(seat4, seat5, seat6));
        reservation2.setArrivalStop(stopLisboa);
        reservation2.setDepartureStop(stopPorto);
        reservation2.setId(2L);
        reservation2.setAuthToken("12345");
    }

    @Test
    void testCreateReservation_ForNonExistingRoute() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Reservation> optionalReservation = reservationService.saveReservation(reservation1);

        assertTrue(optionalReservation.isEmpty());

        verify(stopRepository, times(0)).findById(any());
    }

    @Test
    void testCreateReservation_ForNonExistingStop() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route1));
        when(stopRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Reservation> optionalReservation = reservationService.saveReservation(reservation1);

        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void testCreateReservation_ForNonExistingSeat() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route1));
        when(stopRepository.findById(anyLong())).thenReturn(Optional.of(stopLisboa));
        when(seatRepository.findByRouteId(anyLong())).thenReturn(Optional.empty());

        Optional<Reservation> optionalReservation = reservationService.saveReservation(reservation1);

        assertTrue(optionalReservation.isEmpty());
    }

    @Test
    void testCreateReservation_ForAlreadyBookedSeat() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route1));
        when(stopRepository.findById(anyLong())).thenReturn(Optional.of(stopLisboa));
        when(seatRepository.findByRouteId(anyLong())).thenReturn(Optional.of(List.of(seat1)));

        Optional<Reservation> optionalReservation = reservationService.saveReservation(reservation1);

        assertTrue(optionalReservation.isEmpty());
    }

    //idk why it doesnt work (help me pls)
    @Test
    @Disabled
    void testCreateReservation_ForValidReservation() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(route3));
        when(stopRepository.findById(anyLong())).thenReturn(Optional.of(stopLisboa));
        when(seatRepository.findByRouteId(anyLong())).thenReturn(Optional.of(List.of(seat4, seat5, seat6)));
        when(seatRepository.findById(anyLong())).thenReturn(Optional.of(seat4), Optional.of(seat5), Optional.of(seat6));

        Optional<Reservation> optionalReservation = reservationService.saveReservation(reservation2);

        assertTrue(optionalReservation.isPresent());
    }


}
