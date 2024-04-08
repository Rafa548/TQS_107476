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

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    private SeatRepository seatRepository;

    private StopRepository stopRepository;

    private RouteRepository routeRepository;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().getClass());


    public ReservationService(ReservationRepository reservationRepository, SeatRepository seatRepository, StopRepository stopRepository, RouteRepository routeRepository) {
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.stopRepository = stopRepository;
        this.routeRepository = routeRepository;
    }

    public Optional<Reservation> saveReservation(Reservation reservation) {
        logger.debug("Saving reservation with {} seats", reservation);
        //System.out.println("Reservation: " + reservation.getId());
        //System.out.println("Route: " + reservation.getRoute().getId());
        //System.out.println("Arrival stop: " + reservation.getArrivalStop().getId());
        //System.out.println("Seats: " + reservation.getSeats());
        //System.out.println("Departure stop: " + reservation.getDepartureStop().getId());
        //System.out.println("AuthToken: " + reservation.getAuthToken());


        Optional<Route> route = routeRepository.findById(reservation.getRoute().getId());

        //r3

        if (route.isEmpty()) {
            logger.error("Route not found {}", reservation.getRoute().getId());
            return Optional.empty();
        }


        //get the arrival stop
        Optional<Stop> arrivalStop = stopRepository.findById(reservation.getArrivalStop().getId());
        //StopLisboa

        if (arrivalStop.isEmpty()) {
            logger.error("Arrival stop not found {}", reservation.getArrivalStop().getId());
            return Optional.empty();
        }

        if (!route.get().getStops().contains(arrivalStop.get())) {
            logger.error("Arrival stop not in route {}", arrivalStop.get());
            return Optional.empty();
        }
        
        Optional<List<Seat>> seats = seatRepository.findByRouteId(reservation.getRoute().getId());
        logger.info("Seats found for route {} {}", reservation.getRoute().getId(), seats.get().size());
        //SEATS ALL GOOD

        if (seats.isEmpty()) {
            logger.error("Seats not found for route {}", reservation.getRoute().getId());
            return Optional.empty();
        }

        //get the reservation seats
        //System.out.println("Seats: " + reservation.getSeats());
        for (Seat seat : reservation.getSeats()) {
            Optional<Seat> seatOptional = seatRepository.findById(seat.getId());
            if (seatOptional.isEmpty()) {
                logger.error("Seat not found {}", seat.getId());
                return Optional.empty();
            }
        }

        //check if the seats are available
        //System.out.println("Seats: " + (route.get().getStops().indexOf(arrivalStop.get())-1));
        for (Seat routeSeat : seats.get()) {
            logger.info("Checking seat {}", routeSeat.getId());
            if (reservation.getSeats().contains(routeSeat)) {
                logger.info("Seat found {}", routeSeat.getIsBooked());
                int stopIndex = route.get().getStops().indexOf(arrivalStop.get())-1;
                if (stopIndex >= 0 && stopIndex < routeSeat.getIsBooked().size()) {
                    boolean alreadyBooked = routeSeat.getIsBooked().get(stopIndex);
                    if (alreadyBooked) {
                        logger.error("Seat already booked at stop {}", stopIndex);
                        return Optional.empty(); // Booking failed if already booked
                    } else {
                        routeSeat.getIsBooked().set(stopIndex, true);
                        logger.info("Booking seat at stop {}", stopIndex);
                    }
                    seatRepository.save(routeSeat);
                    logger.info("Seat booked {}", routeSeat.getIsBooked());
                } else {
                    logger.warn("Invalid stop index: {}", stopIndex);
                }
            }
        }


        System.out.println("Seats: " + seats.get());

        Reservation res = reservationRepository.save(reservation);
        System.out.println("Reservation: " + res);
        logger.info("Reservation saved with id {}", res.getId());
        return Optional.of(res);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationDetails(Long id) {
        return reservationRepository.findById(id);
    }

}
