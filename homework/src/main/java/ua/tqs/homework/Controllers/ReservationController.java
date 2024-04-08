package ua.tqs.homework.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.ReservationService;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    private SeatService seatService;

    private RouteService routeService;

    public ReservationController(ReservationService reservationService, SeatService seatService, RouteService routeService) {
        this.reservationService = reservationService;
        this.seatService = seatService;
        this.routeService = routeService;
    }


    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        String authToken = UUID.randomUUID().toString();
        Route route = reservation.getRoute();
        Route routeInDb = routeService.getRouteDetails(route.getId()).orElse(null);
        if (routeInDb == null) {
            return ResponseEntity.badRequest().build();
        }
        reservation.setRoute(routeInDb);
        reservation.setAuthToken(authToken);

        Optional<Reservation> res =  reservationService.saveReservation(reservation);
        if (res.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations() {
        HttpStatus status = HttpStatus.OK;
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, status);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        HttpStatus status = HttpStatus.OK;
        Reservation reservation = reservationService.getReservationDetails(id).orElse(null);
        if (reservation == null)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(reservation, status);
    }

}
