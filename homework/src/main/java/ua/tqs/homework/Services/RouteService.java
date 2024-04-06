package ua.tqs.homework.Services;

import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Repositories.RouteRepository;
import ua.tqs.homework.Repositories.StopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private RouteRepository routeRepository;
    private StopRepository stopRepository;

    public RouteService(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
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

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public void deleteAllRoutes() {
        routeRepository.deleteAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    public List<Route> searchRoutes(String origin, String destination) {
        //get the origin stop
        Stop originStop = stopRepository.findByCityName(origin);
        Stop destinationStop = stopRepository.findByCityName(destination);
        //get all routes
        List<Route> routes = routeRepository.findAll();
        //create a list to store the routes that have the origin and destination
        List<Route> routesFound = new ArrayList<>();
        //iterate over all routes
        for (Route route : routes) {
            //get the stops of the route
            List<Stop> stops = route.getStops();
            //iterate over all stops
            for (int i = 0; i < stops.size(); i++) {
                //if the stop is the origin
                if (stops.get(i).equals(originStop)) {
                    //iterate over all stops
                    for (int j = i + 1; j < stops.size(); j++) {
                        //if the stop is the destination
                        if (stops.get(j).equals(destinationStop)) {
                            //add the route to the list
                            routesFound.add(route);
                        }
                    }
                }
            }
        }
        return routesFound;
    }

    public Object searchRoutesDestination(String destination) {
        Stop destinationStop = stopRepository.findByCityName(destination);
        List<Route> routes = routeRepository.findAll();
        List<Route> routesFound = new ArrayList<>();
        for (Route route : routes) {
            List<Stop> stops = route.getStops();
            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).equals(destinationStop) && i!=0) {
                    routesFound.add(route);
                }
            }
        }
        return routesFound;
    }

    public Object searchRoutesOrigin(String origin) {
        Stop originStop = stopRepository.findByCityName(origin);
        List<Route> routes = routeRepository.findAll();
        List<Route> routesFound = new ArrayList<>();
        for (Route route : routes) {
            List<Stop> stops = route.getStops();
            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).equals(originStop) && i!=stops.size()-1) {
                    routesFound.add(route);
                }
            }
        }
        return routesFound;
    }

    public Object getRouteSeats(Long id) {
        Route route = routeRepository.findById(id).orElse(null);
        if (route == null) {
            return "Route not found";
        }
        return route.getSeats();
    }
}
