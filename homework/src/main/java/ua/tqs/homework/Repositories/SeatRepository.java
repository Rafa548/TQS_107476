package ua.tqs.homework.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.homework.Entities.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}
