package ua.tqs.homework.Services;

import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Reservation;
import ua.tqs.homework.Repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationDetails(Long id) {
        return reservationRepository.findById(id);
    }

}
