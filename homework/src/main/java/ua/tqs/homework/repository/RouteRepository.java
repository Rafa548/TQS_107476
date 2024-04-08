package ua.tqs.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Stop;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
