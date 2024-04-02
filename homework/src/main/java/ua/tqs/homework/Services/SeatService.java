package ua.tqs.homework.Services;


import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Repositories.SeatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void saveSeat(Seat seat) {
        seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

    public Optional<Seat> getSeatDetails(Long id) {
        return seatRepository.findById(id);
    }

}
