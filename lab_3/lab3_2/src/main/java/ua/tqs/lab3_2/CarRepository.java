package ua.tqs.lab3_2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public Car save(Car car);
    public List<Car> findAll();
    public List<Car> findByMaker(String maker);
}
