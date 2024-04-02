package ua.tqs.homework.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Services.StopService;

@RestController
@RequestMapping("/stops")
public class StopController {

    private StopService stopService;

    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllStops() {
        return ResponseEntity.ok(stopService.getAllStops());
    }


}
