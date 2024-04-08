package ua.tqs.homework.Services;


import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.repository.SeatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void saveSeat(Seat seat) {
        System.out.println("Saving seat with id: " + seat.getId());
        System.out.println("Seat number: " + seat.getSeatIdentifier());
        System.out.println("Is booked: " + seat.getIsBooked());
        System.out.println("Route: " + seat.getRoute());
        System.out.println("Reservation: " + seat.getReservations());
        System.out.println("Seat id: " + seat.getId());
        seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatDetails(Long id) {
        return seatRepository.findById(id);
    }

}
