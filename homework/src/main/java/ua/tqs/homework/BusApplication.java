package ua.tqs.homework;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.tqs.homework.Entities.Route;
import ua.tqs.homework.Entities.Seat;
import ua.tqs.homework.Entities.Stop;
import ua.tqs.homework.Services.ReservationService;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;
import ua.tqs.homework.Services.StopService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BusApplication {

    private ReservationService reservationService;
    private RouteService routeService;
    private SeatService seatService;

    private StopService stopService;



    public BusApplication(ReservationService reservationService, RouteService routeService, SeatService seatService, StopService stopService) {
        this.reservationService = reservationService;
        this.routeService = routeService;
        this.seatService = seatService;
        this.stopService = stopService;
    }

    @PostConstruct
    public void init() {

        if (!routeService.getAllRoutes().isEmpty()) {
            return;
        }

        Route route1 = new Route();
        routeService.saveRoute(route1);


        List<Seat> seats = new ArrayList<>();
        List<Boolean> isBooked = new ArrayList<>();

        try {
            int n_stops = route1.getStops().size();

            for (int i = 0; i < n_stops; i++) {
                isBooked.add(false);
            }
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }


        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat(i,1, isBooked, route1);
            seatService.saveSeat(seat);
            seats.add(seat);
        }

        for (int i = 1; i <= 4; i++) {
            Seat seat = new Seat(i,2, isBooked, route1);
            seatService.saveSeat(seat);
            seats.add(seat);
        }

        Stop stop1 = new Stop("Porto", "5", "6");
        Stop stop2 = new Stop("Lisboa", "7", "8");
        Stop stop3 = new Stop("Braga", "9", "10");
        Stop stop4 = new Stop("Coimbra","11", "12");
        Stop stop5 = new Stop("Faro","13", "14");

        route1.setStops(List.of(stop1, stop2, stop3, stop4, stop5));

        stopService.saveStop(stop1);
        stopService.saveStop(stop2);
        stopService.saveStop(stop3);
        stopService.saveStop(stop4);
        stopService.saveStop(stop5);

        routeService.saveRoute(route1);
    }


    public static void main(String[] args) {
        SpringApplication.run(BusApplication.class, args);
    }


}
