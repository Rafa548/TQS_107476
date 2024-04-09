package ua.tqs.homework.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.RouteRepository;
import ua.tqs.homework.repository.StopRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;
    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    public RouteService(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    public void saveRoute(Route route) {
        //logger.debug("Saving route with {} stops", route.getStops().size());
        routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        logger.debug("Fetching all routes");
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteDetails(Long id) {
        logger.debug("Fetching route details for id: {}", id);
        return routeRepository.findById(id);
    }

    public void deleteRoute(Long id) {
        logger.debug("Deleting route with id: {}", id);
        routeRepository.deleteById(id);
    }

    public void deleteAllRoutes() {
        logger.debug("Deleting all routes");
        routeRepository.deleteAll();
    }

    public Route getRouteById(Long id) {
        logger.debug("Fetching route by id: {}", id);
        return routeRepository.findById(id).orElse(null);
    }

    public List<Route> searchRoutes(String origin, String destination) {
        logger.debug("Searching routes from {} to {}", origin, destination);
        Stop originStop = stopRepository.findByCityName(origin);
        Stop destinationStop = stopRepository.findByCityName(destination);
        List<Route> routes = routeRepository.findAll();
        List<Route> routesFound = new ArrayList<>();
        for (Route route : routes) {
            List<Stop> stops = route.getStops();
            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).equals(originStop)) {
                    for (int j = i + 1; j < stops.size(); j++) {
                        if (stops.get(j).equals(destinationStop)) {
                            routesFound.add(route);
                        }
                    }
                }
            }
        }
        logger.info("Found {} routes", routesFound.size());
        return routesFound;
    }

    public List<Route> searchRoutesDestination(String destination) {
        logger.debug("Searching routes to {}", destination);
        Stop destinationStop = stopRepository.findByCityName(destination);
        List<Route> routes = routeRepository.findAll();
        List<Route> routesFound = new ArrayList<>();
        for (Route route : routes) {
            List<Stop> stops = route.getStops();
            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).equals(destinationStop) && i != 0) {
                    routesFound.add(route);
                }
            }
        }
        logger.info("Found {} routes", routesFound.size());
        return routesFound;
    }

    public List<Route> searchRoutesOrigin(String origin) {
        logger.debug("Searching routes from {}", origin);
        Stop originStop = stopRepository.findByCityName(origin);
        List<Route> routes = routeRepository.findAll();
        List<Route> routesFound = new ArrayList<>();
        for (Route route : routes) {
            List<Stop> stops = route.getStops();
            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).equals(originStop) && i != stops.size() - 1) {
                    routesFound.add(route);
                }
            }
        }
        logger.info("Found {} routes", routesFound.size());
        return routesFound;
    }

    public List<Seat> getRouteSeats(Long id) {
        logger.debug("Fetching seats for route with id: {}", id);
        Route route = routeRepository.findById(id).orElse(null);
        if (route == null) {
            logger.warn("Route not found for id: {}", id);
            return Collections.emptyList();
        }
        logger.info("Found {} seats", route.getSeats().size());
        return route.getSeats();
    }
}

