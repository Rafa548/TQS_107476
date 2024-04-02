package ua.tqs.homework.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
