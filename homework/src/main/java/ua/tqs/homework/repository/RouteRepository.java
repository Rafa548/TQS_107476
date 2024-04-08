package ua.tqs.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tqs.homework.Entities.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
