package ua.tqs.homework.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Services.RouteService;

@RestController
@RequestMapping ("/routes")
public class RouteController {

    private RouteService routeService;

    //fazer dps ao adicionar novo stop adicionar aos seats um false novo


    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }


    @GetMapping("/search/{origin}/{destination}")
    public ResponseEntity<?> searchRoutes(@PathVariable String origin, @PathVariable String destination) {
        return ResponseEntity.ok(routeService.searchRoutes(origin, destination));
    }

    @GetMapping("/searchArrival/{destination}")
    public ResponseEntity<?> searchRoutesDestination(@PathVariable String destination) {
        return ResponseEntity.ok(routeService.searchRoutesDestination(destination));
    }


    @GetMapping("/searchDeparture/{origin}")
    public ResponseEntity<?> searchRoutesOrigin(@PathVariable String origin) {
        return ResponseEntity.ok(routeService.searchRoutesOrigin(origin));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<?> getRouteSeats(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteSeats(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRouteDetails(@PathVariable Long id) {
        if (routeService.getRouteDetails(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(routeService.getRouteDetails(id).get());
    }



}
