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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class SeatRepositoryTest {
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
        route1.setSeats(List.of(seat1));
        reservation1 = new Reservation("John Doe", route1, List.of(seat1));

        entityManager.persist(seat1);
        entityManager.persist(route1);
    }

    @Test
    void whenFindById1_thenReturnId1() {
        Optional<Seat> fromDb = seatRepository.findById(seat1.getId());
        assertTrue(fromDb.isPresent());
        assertEquals(seat1.getId(), fromDb.get().getId());
        assertEquals("1A", fromDb.get().getSeatIdentifier());
        //System.out.println(fromDb.get().getRoute().getSeats());
        // Verify relationships
        assertEquals(1, fromDb.get().getRoute().getSeats().size());
    }

    @Test
    void whenFindInvalidId_thenReturnEmptyOptional() {
        Optional<Seat> fromDb = seatRepository.findById(-1L);
        assertTrue(fromDb.isEmpty());
    }

    @Test
    void whenFindByRouteId_thenReturnSeats() {
        List<Seat> fromDb = seatRepository.findByRouteId(route1.getId()).get();
        assertEquals(1, fromDb.size());
        assertEquals("1A", fromDb.get(0).getSeatIdentifier());
    }

    @Test
    void whenFindByInvalidRouteId_thenReturnEmptyOptional() {
        Optional<List<Seat>> expected = Optional.of(Collections.emptyList());
        Optional<List<Seat>> fromDb = seatRepository.findByRouteId(-1L);
        assertEquals(expected, fromDb);
    }

    @Test
    void whenSaveSeat_thenSavesCorrectly() {
        Seat newSeat = new Seat("2A", 2, List.of(false), route1);
        seatRepository.save(newSeat);

        Optional<Seat> savedSeat = seatRepository.findById(newSeat.getId());
        assertTrue(savedSeat.isPresent());
        assertEquals("2A", savedSeat.get().getSeatIdentifier());
    }
}
