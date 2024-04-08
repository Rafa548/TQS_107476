package ua.tqs.homework;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Autowired
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

        Stop stop1 = new Stop("Porto", "5", "6");
        Stop stop2 = new Stop("Lisboa", "6", "8");
        Stop stop3 = new Stop("Braga", "8", "10");
        Stop stop4 = new Stop("Coimbra","10", "12");
        Stop stop5 = new Stop("Faro","12", "14");

        route1.setStops(List.of(stop1, stop2, stop3, stop4, stop5));

        stopService.saveStop(stop1);
        stopService.saveStop(stop2);
        stopService.saveStop(stop3);
        stopService.saveStop(stop4);
        stopService.saveStop(stop5);
        routeService.saveRoute(route1);


        List<Boolean> isBooked = new ArrayList<>();
        List<Boolean> isBookedTrue = new ArrayList<>();

        try {
            int n_stops = route1.getStops().size()-1; //beginning never has a stop

            for (int i = 0; i < n_stops; i++) {
                isBooked.add(false);
                isBookedTrue.add(true);
            }
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }


        //generate seats with letter and number
        String[] letters = {"A", "B", "C", "D"};
        for (int i = 1; i <= 4; i++) {
            for (String letter : letters) {
                if (letter.equals("A")) {
                    Seat seat = new Seat(i + letter, 2, isBookedTrue, route1);
                    seatService.saveSeat(seat);
                }
                else {
                    Seat seat = new Seat(i + letter, 1, isBooked, route1);
                    seatService.saveSeat(seat);
                }
            }
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(BusApplication.class, args);
    }


}
