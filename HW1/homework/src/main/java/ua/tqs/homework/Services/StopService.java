package ua.tqs.homework.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.repository.StopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StopService {

    private final StopRepository stopRepository;
    private static final Logger logger = LoggerFactory.getLogger(StopService.class);

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public void saveStop(Stop stop) {
        logger.info("Saving stop: {}", stop);
        stopRepository.save(stop);
        logger.info("Stop saved successfully");
    }

    public List<Stop> getAllStops() {
        logger.debug("Fetching all stops");
        List<Stop> stops = stopRepository.findAll();
        logger.info("Fetched {} stops", stops.size());
        return stops;
    }

    public Optional<Stop> getStopDetails(Long id) {
        logger.debug("Fetching stop details for id: {}", id);
        Optional<Stop> stop = stopRepository.findById(id);
        if (stop.isPresent()) {
            logger.info("Stop details found: {}", stop.get());
        } else {
            logger.warn("Stop details not found for id: {}", id);
        }
        return stop;
    }
}

