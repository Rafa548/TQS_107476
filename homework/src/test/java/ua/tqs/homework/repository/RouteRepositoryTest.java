package ua.tqs.homework.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;

@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    Stop stop1, stop2;
    Route route1;
    Seat seat1;
    Reservation reservation1;

    @BeforeEach
    void setup() {
        stop1 = new Stop("Porto", "5", "6");
        stop2 = new Stop("Lisboa", "6", "8");
        List<Stop> stops = new ArrayList<>();
        stops.add(stop1);
        stops.add(stop2);
        route1 = new Route(stops);

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
        reservation1 = new Reservation("John Doe", route1, List.of(seat1));

        // Cascade persistence for related entities
        entityManager.persist(route1);
    }

    @Test
    void whenFindById1_thenReturnId1() {
        Optional<Route> fromDb = routeRepository.findById(route1.getId());
        assertTrue(fromDb.isPresent());
        assertEquals(route1.getId(), fromDb.get().getId());
        assertEquals(2, fromDb.get().getStops().size()); // Verify relationships
    }

    @Test
    void whenFindInvalidId_thenReturnEmptyOptional() {
        Optional<Route> fromDb = routeRepository.findById(-1L);
        assertTrue(fromDb.isEmpty());
    }

    @Test
    void whenSaveRoute_thenSavesCorrectly() {
        Route newRoute = new Route(List.of(stop1));
        routeRepository.save(newRoute);

        Optional<Route> savedRoute = routeRepository.findById(newRoute.getId());
        assertTrue(savedRoute.isPresent());
        assertEquals(1, savedRoute.get().getStops().size());
    }

}
