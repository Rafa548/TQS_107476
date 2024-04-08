package ua.tqs.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class StopRepositoryTest {

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
        stop1.setRoutes(List.of(route1));
        stop2.setRoutes(List.of(route1));

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
        route1.setSeats(List.of(seat1));
        reservation1 = new Reservation("John Doe", route1, List.of(seat1));

        entityManager.persist(seat1);
        entityManager.persist(route1);
    }

    @Test
    void whenFindById1_thenReturnId1() {
        Optional<Stop> fromDb = stopRepository.findById(stop1.getId());
        assertTrue(fromDb.isPresent());
        assertEquals(stop1.getId(), fromDb.get().getId());
        //verify relationships
        assertEquals(1, fromDb.get().getRoutes().size());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Optional<Stop> fromDb = stopRepository.findById(-99L);
        assertTrue(fromDb.isEmpty());
    }

    @Test
    void whenFindByName_thenReturnStop() {
        Stop fromDb = stopRepository.findByCityName(stop1.getCityName());
        assertEquals(stop1, fromDb);
        assertEquals(stop1.getCityName(), fromDb.getCityName());
    }

    @Test
    void whenFindByNameInvalid_thenReturnNull() {
        Stop fromDb = stopRepository.findByCityName("Invalid");
        assertEquals(null, fromDb);
    }

}
