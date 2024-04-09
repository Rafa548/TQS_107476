package ua.tqs.homework.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.SeatRepository;
import ua.tqs.homework.repository.StopRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceMockRepoTest {

    @Mock
    RouteRepository routeRepository;

    @Mock
    StopRepository stopRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    RouteService routeService;

    @InjectMocks
    StopService stopService;

    @InjectMocks
    SeatService seatService;

    Route route1, route2, route3;
    Stop stopPorto, stopLisboa, stopBraga, stopCoimbra, stopFaro;
    Seat seat1, seat2, seat3, seat4, seat5, seat6, seat8, seat9, seat10;

    @BeforeEach
    void setup() {

        route1 = new Route();
        route2 = new Route();
        route3 = new Route();

        stopPorto = new Stop("Porto", "5", "6");
        stopLisboa = new Stop("Lisboa", "6", "8");
        stopBraga = new Stop("Braga", "8", "10");
        stopCoimbra = new Stop("Coimbra","10", "12");
        stopFaro = new Stop("Faro","12", "14");

        route1.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra));
        route2.setStops(List.of(stopBraga, stopPorto));
        route3.setStops(List.of(stopPorto, stopFaro));

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

        seat1 = new Seat("1A",2,isBooked, route1);
        seat2 = new Seat("1B",1,isBooked, route1);
        seat3 = new Seat("1C",1,isBooked, route1);
        seat4 = new Seat("1A",2,isBooked, route3);
        seat5 = new Seat("1B",1,isBooked, route3);
        seat6 = new Seat("1C",1,isBooked, route3);
        seat8 = new Seat("2B",1,isBooked, route2);
        seat9 = new Seat("2C",1,isBooked, route2);
        seat10 = new Seat("2D",1,isBooked, route2);

        seatRepository.save(seat1);
        seatRepository.save(seat2);
        seatRepository.save(seat3);
        seatRepository.save(seat4);
        seatRepository.save(seat5);
        seatRepository.save(seat6);
        seatRepository.save(seat8);
        seatRepository.save(seat9);
        seatRepository.save(seat10);
    }

    @Test
    void testGetAllSeats() {
        when(seatRepository.findAll()).thenReturn(List.of(seat1, seat2, seat3, seat4, seat5, seat6, seat8, seat9, seat10));
        List<Seat> allSeats = seatService.getAllSeats();
        assertEquals(9,allSeats.size());
        assertEquals(allSeats.get(0), seat1);
        assertEquals(allSeats.get(1), seat2);
    }

    @Test
    void testGetSeatDetails() {

        when(seatRepository.findById(1L)).thenReturn(java.util.Optional.of(seat1));
        assertEquals( "1A",seatService.getSeatDetails(1L).get().getSeatIdentifier());
        assertEquals(seatService.getSeatDetails(1L).get().getRoute(), route1);
    }

    @Test
    void testSaveSeat() {
        Seat seat = new Seat("1D", 1, List.of(false, false, false, false), route1);
        seatService.saveSeat(seat);
        when(seatRepository.findById(11L)).thenReturn(java.util.Optional.of(seat));
        assertEquals( "1D",seatService.getSeatDetails(11L).get().getSeatIdentifier());
        assertEquals(seatService.getSeatDetails(11L).get().getRoute(), route1);
    }





}
