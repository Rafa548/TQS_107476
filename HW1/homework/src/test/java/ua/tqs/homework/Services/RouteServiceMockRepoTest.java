package ua.tqs.homework.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.ActiveProfiles;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Services.SeatService;
import ua.tqs.homework.Services.StopService;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.SeatRepository;
import ua.tqs.homework.repository.StopRepository;
import ua.tqs.homework.Services.RouteService;

@ExtendWith(MockitoExtension.class)
class RouteServiceMockRepoTest {
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
        route2.setStops(List.of(stopPorto, stopLisboa));
        route3.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra, stopFaro));

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


        route1.setSeats(List.of(seat1, seat2, seat3));
        route2.setSeats(List.of());
        route3.setSeats(List.of(seat4, seat5, seat6));

        routeRepository.save(route1);
        routeRepository.save(route2);
        routeRepository.save(route3);
    }

    @Test
    void testFindRoutes_FromPorto_ToCoimbra() {
        when(stopRepository.findByCityName(stopPorto.getCityName())).thenReturn(stopPorto);
        when(stopRepository.findByCityName(stopCoimbra.getCityName())).thenReturn(stopCoimbra);
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        List<Route> routesFromPortoToCoimbra = routeService.searchRoutes(stopPorto.getCityName(), stopCoimbra.getCityName());

        assertEquals(2, routesFromPortoToCoimbra.size());
        assertTrue(routesFromPortoToCoimbra.contains(route1));

        verify(routeRepository, times(1)).findAll();
        //two times because the method is called twice in the test
        verify(stopRepository, times(1)).findByCityName(stopPorto.getCityName());
        verify(stopRepository, times(1)).findByCityName(stopCoimbra.getCityName());
    }


    @Test
    void testFindRoutes_FromFaro_ToBraga() {
        when(stopRepository.findByCityName(stopFaro.getCityName())).thenReturn(stopFaro);
        when(stopRepository.findByCityName(stopBraga.getCityName())).thenReturn(stopBraga);

        List<Route> routesFromFaroToBraga = routeService.searchRoutes(stopFaro.getCityName(),
                stopBraga.getCityName());

        assertTrue(routesFromFaroToBraga.isEmpty());

        verify(routeRepository, times(1)).findAll();
        verify(stopRepository, times(1)).findByCityName(stopFaro.getCityName());
        verify(stopRepository, times(1)).findByCityName(stopBraga.getCityName());
    }

    @Test
    void testFindAllRoutes() {
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        List<Route> allRoutes = routeService.getAllRoutes();

        assertEquals(3, allRoutes.size());
        assertEquals(route1, allRoutes.get(0));
        assertEquals(route2, allRoutes.get(1));
        assertEquals(route3, allRoutes.get(2));

        verify(routeRepository, times(1)).findAll();
    }

    @Test
    void testFindValidRouteById() {
        when(routeRepository.findById(3L)).thenReturn(Optional.of(route3));

        Optional<Route> foundRoute = routeService.getRouteDetails(3L);

        assertTrue(foundRoute.isPresent());
        assertEquals(route3, foundRoute.get());

        verify(routeRepository, times(1)).findById(3L);
    }

    @Test
    void testFindInvalidRouteById() {
        when(routeRepository.findById(1000L)).thenReturn(Optional.empty());

        Optional<Route> foundRoute = routeService.getRouteDetails(1000L);

        assertTrue(foundRoute.isEmpty());

        verify(routeRepository, times(1)).findById(1000L);
    }

    @Test
    void testDeleteRouteById() {
        routeService.deleteRoute(1L);

        verify(routeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAllRoutes() {
        routeService.deleteAllRoutes();

        verify(routeRepository, times(1)).deleteAll();
    }

    @Test
    void testGetRouteById() {
        when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

        Route foundRoute = routeService.getRouteById(1L);

        assertEquals(route1, foundRoute);

        verify(routeRepository, times(1)).findById(1L);
    }

    @Test
    void testSearchRoutesDestination() {
        when(stopRepository.findByCityName(stopCoimbra.getCityName())).thenReturn(stopCoimbra);
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        List<Route> routesFromPorto = routeService.searchRoutesDestination(stopCoimbra.getCityName());

        assertEquals(2, routesFromPorto.size());
        assertTrue(routesFromPorto.contains(route1));
        assertTrue(routesFromPorto.contains(route3));

        verify(routeRepository, times(1)).findAll();
        verify(stopRepository, times(1)).findByCityName(stopCoimbra.getCityName());
    }

    @Test
    void testSearchRoutesOrigin() {
        when(stopRepository.findByCityName(stopPorto.getCityName())).thenReturn(stopPorto);
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route1, route2, route3));

        List<Route> routesFromPorto = routeService.searchRoutesOrigin(stopPorto.getCityName());

        assertEquals(3, routesFromPorto.size());
        assertTrue(routesFromPorto.contains(route1));
        assertTrue(routesFromPorto.contains(route3));

        verify(routeRepository, times(1)).findAll();
        verify(stopRepository, times(1)).findByCityName(stopPorto.getCityName());
    }

    @Test
    void testGetRouteSeats() {
        when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

        List<Seat> routeSeats = routeService.getRouteSeats(1L);

        assertEquals(3, routeSeats.size());
        assertTrue(routeSeats.contains(seat1));
        assertTrue(routeSeats.contains(seat2));
        assertTrue(routeSeats.contains(seat3));

        verify(routeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRouteSeatsInvalidRoute() {
        when(routeRepository.findById(1000L)).thenReturn(Optional.empty());

        List<Seat> routeSeats = routeService.getRouteSeats(1000L);

        assertTrue(routeSeats.isEmpty());

        verify(routeRepository, times(1)).findById(1000L);
    }

    @Test
    void testGetRouteSeatsEmptyRoute() {
        when(routeRepository.findById(2L)).thenReturn(Optional.of(route2));

        List<Seat> routeSeats = routeService.getRouteSeats(2L);

        assertTrue(routeSeats.isEmpty());

        verify(routeRepository, times(1)).findById(2L);
    }

    @Test
    void testSaveRoute() {
        Route route4 = new Route();
        route4.setStops(List.of(stopPorto, stopLisboa, stopBraga, stopCoimbra, stopFaro));

        routeService.saveRoute(route4);

        verify(routeRepository, times(1)).save(route4);
    }


}