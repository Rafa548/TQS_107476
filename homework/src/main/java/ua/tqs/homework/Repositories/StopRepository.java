package ua.tqs.homework.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.homework.Entities.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {
}
