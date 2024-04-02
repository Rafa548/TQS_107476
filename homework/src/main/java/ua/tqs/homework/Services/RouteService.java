package ua.tqs.homework.Services;

import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Repositories.RouteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void saveRoute(Route route) {
        routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteDetails(Long id) {
        return routeRepository.findById(id);
    }
}
