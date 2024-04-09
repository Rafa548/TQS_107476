package ua.tqs.homework.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Services.RouteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
@Tag(name = "Route Management", description = "Endpoints for managing routes")
public class RouteController {

    private final RouteService routeService;
    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @Operation(summary = "Get all routes")
    @GetMapping()
    public ResponseEntity<List<Route>> getAllRoutes() {
        logger.info("Received request to get all routes");
        List<Route> routes = routeService.getAllRoutes();
        logger.info("Returned {} routes", routes.size());
        return ResponseEntity.ok(routes);
    }

    @Operation(summary = "Search routes by origin and destination")
    @GetMapping("/search/{origin}/{destination}")
    public ResponseEntity<List<Route>> searchRoutes(@PathVariable String origin, @PathVariable String destination) {
        logger.info("Received request to search routes by origin '{}' and destination '{}'", origin, destination);
        List<Route> routes = routeService.searchRoutes(origin, destination);
        logger.info("Returned {} routes for origin '{}' and destination '{}'", routes.size(), origin, destination);
        return ResponseEntity.ok(routes);
    }

    @Operation(summary = "Search routes by destination")
    @GetMapping("/searchArrival/{destination}")
    public ResponseEntity<List<Route>> searchRoutesDestination(@PathVariable String destination) {
        logger.info("Received request to search routes by destination '{}'", destination);
        List<Route> routes = routeService.searchRoutesDestination(destination);
        logger.info("Returned {} routes for destination '{}'", routes.size(), destination);
        return ResponseEntity.ok(routes);
    }

    @Operation(summary = "Search routes by origin")
    @GetMapping("/searchDeparture/{origin}")
    public ResponseEntity<List<Route>> searchRoutesOrigin(@PathVariable String origin) {
        logger.info("Received request to search routes by origin '{}'", origin);
        List<Route> routes = routeService.searchRoutesOrigin(origin);
        logger.info("Returned {} routes for origin '{}'", routes.size(), origin);
        return ResponseEntity.ok(routes);
    }

    @Operation(summary = "Get seats for a specific route")
    @GetMapping("/{id}/seats")
    public ResponseEntity<List<Seat>> getRouteSeats(@PathVariable Long id) {
        logger.info("Received request to get seats for route with ID '{}'", id);
        List<Seat> seats = routeService.getRouteSeats(id);
        logger.info("Returned {} seats for route with ID '{}'", seats.size(), id);
        return ResponseEntity.ok(seats);
    }

    @Operation(summary = "Get details of a specific route")
    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteDetails(@PathVariable Long id) {
        logger.info("Received request to get details for route with ID '{}'", id);
        Optional<Route> optionalRoute = routeService.getRouteDetails(id);

        if (optionalRoute.isEmpty()) {
            logger.error("Route with ID '{}' not found", id);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Returned details for route with ID '{}'", id);
        return ResponseEntity.ok(optionalRoute.get());
    }
}

