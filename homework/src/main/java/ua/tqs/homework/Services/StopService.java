package ua.tqs.homework.Services;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Repositories.StopRepository;

@Service
public class StopService {

    private StopRepository stopRepository;

    public StopService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    public void saveStop(Stop stop) {
        stopRepository.save(stop);
    }

    public Iterable<Stop> getAllStops() {
        return stopRepository.findAll();
    }

    public Stop getStopDetails(Long id) {
        return stopRepository.findById(id).orElse(null);
    }


}
