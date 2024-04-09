package ua.tqs.homework.Controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.ReservationService;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
@Tag(name = "Reservation Management", description = "Endpoints for managing reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final SeatService seatService;
    private final RouteService routeService;
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ReservationController(ReservationService reservationService, SeatService seatService, RouteService routeService) {
        this.reservationService = reservationService;
        this.seatService = seatService;
        this.routeService = routeService;
    }

    @Operation(summary = "Create a new reservation")
    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        logger.info("Received request to create reservation");

        String authToken = UUID.randomUUID().toString();
        Route route = reservation.getRoute();
        List<Seat> seats = reservation.getSeats();

        Route routeInDb = routeService.getRouteDetails(route.getId()).orElse(null);
        if (routeInDb == null) {
            logger.error("Route not found for reservation");
            return ResponseEntity.badRequest().build();
        }

        for (Seat seat : seats) {
            Seat seatInDb = seatService.getSeatDetails(seat.getId()).orElse(null);
            if (seatInDb == null) {
                logger.error("Seat not found for reservation");
                return ResponseEntity.badRequest().build();
            }
            seatInDb.setIsBooked(seat.getIsBooked());
            seatService.saveSeat(seatInDb);
        }

        reservation.setRoute(routeInDb);
        reservation.setAuthToken(authToken);

        Optional<Reservation> res = reservationService.saveReservation(reservation);
        if (res.isEmpty()) {
            logger.error("Failed to save reservation");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Reservation created successfully");
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all reservations")
    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations() {
        logger.info("Received request to get all reservations");
        HttpStatus status = HttpStatus.OK;
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, status);
    }

    @Operation(summary = "Get reservation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        logger.info("Received request to get reservation by ID");
        HttpStatus status = HttpStatus.OK;
        Reservation reservation = reservationService.getReservationDetails(id).orElse(null);
        if (reservation == null) {
            logger.error("Reservation not found");
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(reservation, status);
    }

    @Operation(summary = "Get reservation by ID and authentication token")
    @GetMapping("/{id}/{authToken}")
    public ResponseEntity<Reservation> getReservationByIdAndAuthToken(@PathVariable Long id, @PathVariable String authToken) {
        logger.info("Received request to get reservation by ID and authentication token");
        HttpStatus status = HttpStatus.OK;
        Reservation reservation = reservationService.getReservationDetails(id).orElse(null);
        if (reservation == null || !reservation.getAuthToken().equals(authToken)) {
            logger.error("Reservation not found or invalid authentication token");
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(reservation, status);
    }

    @Operation(summary = "Cancel a reservation by ID")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        logger.info("Received request to cancel reservation");
        HttpStatus status = HttpStatus.OK;
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(status);
    }
}
