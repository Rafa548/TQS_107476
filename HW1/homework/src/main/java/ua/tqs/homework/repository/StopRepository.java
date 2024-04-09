package ua.tqs.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.tqs.homework.Entities.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Stop findByCityName(String cityName);
}
