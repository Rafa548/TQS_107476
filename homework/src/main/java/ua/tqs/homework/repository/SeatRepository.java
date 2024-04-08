package ua.tqs.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.homework.Entities.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<List<Seat>> findByRouteId(Long id);
}
