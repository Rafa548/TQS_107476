package ua.tqs.homework.Services;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.SeatRepository;
import ua.tqs.homework.repository.StopRepository;

import java.util.List;

public class SeatServiceMockRepoTest {

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
        // route 1: Porto -> Lisboa -> Braga -> Coimbra
        // route 2: Braga -> Porto

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


    }


}
