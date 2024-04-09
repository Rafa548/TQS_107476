package ua.tqs.homework.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Services.StopService;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/stops")
@Tag(name = "Stop Management", description = "Endpoints for managing stops")
public class StopController {

    private final StopService stopService;
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @Operation(summary = "Get all stops")
    @GetMapping()
    public ResponseEntity<List<Stop>> getAllStops() {
        logger.info("Received request to get all stops");
        List<Stop> stops = stopService.getAllStops();
        logger.info("Returning {} stops", stops.size());
        return ResponseEntity.ok(stops);
    }
}

