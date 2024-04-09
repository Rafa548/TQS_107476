package ua.tqs.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.homework.Entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


}
