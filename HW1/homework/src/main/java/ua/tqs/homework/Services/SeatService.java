package ua.tqs.homework.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.repository.SeatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void saveSeat(Seat seat) {
        logger.info("Saving seat: {}", seat);
        seatRepository.save(seat);
        logger.info("Seat saved successfully");
    }

    public List<Seat> getAllSeats() {
        logger.debug("Fetching all seats");
        List<Seat> seats = seatRepository.findAll();
        logger.info("Fetched {} seats", seats.size());
        return seats;
    }

    public Optional<Seat> getSeatDetails(Long id) {
        logger.debug("Fetching seat details for id: {}", id);
        Optional<Seat> seat = seatRepository.findById(id);
        if (seat.isPresent()) {
            logger.info("Seat details found: {}", seat.get());
        } else {
            logger.warn("Seat details not found for id: {}", id);
        }
        return seat;
    }
}


