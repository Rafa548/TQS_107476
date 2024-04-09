package ua.tqs.homework.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.RouteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/routes")
public class RouteController {

    private final RouteService routeService;


    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping()
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }


    @GetMapping("/search/{origin}/{destination}")
    public ResponseEntity<List<Route>> searchRoutes(@PathVariable String origin, @PathVariable String destination) {
        return ResponseEntity.ok(routeService.searchRoutes(origin, destination));
    }

    @GetMapping("/searchArrival/{destination}")
    public ResponseEntity<List<Route>> searchRoutesDestination(@PathVariable String destination) {
        return ResponseEntity.ok(routeService.searchRoutesDestination(destination));
    }


    @GetMapping("/searchDeparture/{origin}")
    public ResponseEntity<List<Route>> searchRoutesOrigin(@PathVariable String origin) {
        return ResponseEntity.ok(routeService.searchRoutesOrigin(origin));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<Seat>> getRouteSeats(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteSeats(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteDetails(@PathVariable Long id) {
        Optional<Route> optionalRoute = routeService.getRouteDetails(id);

        if (optionalRoute.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(optionalRoute.get());
    }



}
