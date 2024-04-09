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
class StopServiceMockRepoTest {

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
        stopCoimbra = new Stop("Coimbra", "10", "12");
        stopFaro = new Stop("Faro", "12", "14");

        route1.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra));
        route2.setStops(List.of(stopBraga, stopPorto));
        route3.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra, stopFaro));


        stopRepository.save(stopPorto);
        stopRepository.save(stopLisboa);
        stopRepository.save(stopBraga);
        stopRepository.save(stopCoimbra);
        stopRepository.save(stopFaro);

    }

    @Test
    void testGetAllStops() {
        List<Stop> stops = new ArrayList<>();
        stops.add(stopPorto);
        stops.add(stopLisboa);
        stops.add(stopBraga);
        stops.add(stopCoimbra);
        stops.add(stopFaro);

        when(stopRepository.findAll()).thenReturn(stops);

        List<Stop> allStops = stopService.getAllStops();

        assertEquals(5,allStops.size());
        assertEquals(allStops.get(0), stopPorto);
        assertEquals(allStops.get(1), stopLisboa);
        assertEquals(allStops.get(2), stopBraga);
    }


    @Test
    void testGetStopDetails() {
        when(stopRepository.findById(stopPorto.getId())).thenReturn(java.util.Optional.of(stopPorto));

        Stop stop = stopService.getStopDetails(stopPorto.getId()).orElse(null);

        assertEquals(stopPorto.getId(), stop.getId());
        assertEquals(stopPorto.getCityName(), stop.getCityName());
    }

}
