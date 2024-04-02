package ua.tqs.homework.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping()
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        //vai ser preciso verificar se a reserva é válida e se há lugares disponíveis
        HttpStatus status = HttpStatus.CREATED;
        reservationService.saveReservation(reservation);
        return new ResponseEntity<>(reservation, status);
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
