package ua.tqs.homework.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.ReservationRepository;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.SeatRepository;
import ua.tqs.homework.repository.StopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final StopRepository stopRepository;
    private final RouteRepository routeRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService(ReservationRepository reservationRepository, SeatRepository seatRepository, StopRepository stopRepository, RouteRepository routeRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.stopRepository = stopRepository;
        this.routeRepository = routeRepository;
    }

    public Optional<Reservation> saveReservation(Reservation reservation) {
        logger.debug("Saving reservation: {}", reservation);
        Optional<Route> route = routeRepository.findById(reservation.getRoute().getId());

        if (route.isEmpty()) {
            logger.error("Route not found for reservation: {}", reservation.getRoute().getId());
            return Optional.empty();
        }

        Optional<Stop> arrivalStop = stopRepository.findById(reservation.getArrivalStop().getId());

        if (arrivalStop.isEmpty()) {
            logger.error("Arrival stop not found for reservation: {}", reservation.getArrivalStop().getId());
            return Optional.empty();
        }

        if (!route.get().getStops().contains(arrivalStop.get())) {
            logger.error("Arrival stop not in route for reservation: {}", arrivalStop.get());
            return Optional.empty();
        }

        Optional<List<Seat>> seats = seatRepository.findByRouteId(reservation.getRoute().getId());

        if (seats.isEmpty()) {
            logger.error("Seats not found for route in reservation: {}", reservation.getRoute().getId());
            return Optional.empty();
        }

        for (Seat seat : reservation.getSeats()) {
            Optional<Seat> seatOptional = seatRepository.findById(seat.getId());
            if (seatOptional.isEmpty()) {
                logger.error("Seat not found for reservation: {}", seat.getId());
                return Optional.empty();
            }
        }

        Reservation res = reservationRepository.save(reservation);
        logger.info("Reservation saved with id: {}", res.getId());
        return Optional.of(res);
    }

    public List<Reservation> getAllReservations() {
        logger.info("Fetching all reservations");
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationDetails(Long id) {
        logger.info("Fetching reservation details for id: {}", id);
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation == null) {
            logger.error("Reservation not found for deletion: {}", id);
            return;
        }
        List<Seat> seats = reservation.getSeats();
        for (Seat seat : seats) {
            Stop arrivalStop = reservation.getArrivalStop();
            Route route = reservation.getRoute();
            Seat seatInDb = seatRepository.findById(seat.getId()).orElse(null);
            if (seatInDb != null) {
                List<Boolean> isBooked = seatInDb.getIsBooked();
                isBooked.set(route.getStops().indexOf(arrivalStop) - 1, false);
                seatRepository.save(seatInDb);
            }
        }
        reservationRepository.deleteById(id);
        logger.info("Reservation deleted: {}", id);
    }
}

