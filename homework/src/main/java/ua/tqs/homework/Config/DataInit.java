package ua.tqs.homework.Config;

import ua.tqs.homework.Entities.*;
import ua.tqs.homework.Services.ReservationService;
import ua.tqs.homework.Services.RouteService;
import ua.tqs.homework.Services.SeatService;
import ua.tqs.homework.Services.StopService;
import ua.tqs.homework.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInit {

    private ReservationService reservationService;
    private RouteService routeService;
    private SeatService seatService;
    private StopService stopService;

    @Autowired
    public DataInit(ReservationService reservationService, RouteService routeService, SeatService seatService, StopService stopService) {
        this.reservationService = reservationService;
        this.routeService = routeService;
        this.seatService = seatService;
        this.stopService = stopService;
    }

    @PostConstruct
    public void initialize() throws Exception {

        if (!routeService.getAllRoutes().isEmpty()) {
            return;
        }

        Route route1 = new Route();
        routeService.saveRoute(route1);
        Route route2 = new Route();
        routeService.saveRoute(route2);
        Route route3 = new Route();
        routeService.saveRoute(route3);

        Stop stop1 = new Stop("Porto", "5", "6");
        Stop stop2 = new Stop("Lisboa", "6", "8");
        Stop stop3 = new Stop("Braga", "8", "10");
        Stop stop4 = new Stop("Coimbra","10", "12");
        Stop stop5 = new Stop("Faro","12", "14");

        route1.setStops(List.of(stop1, stop2, stop3, stop4, stop5));
        route2.setStops(List.of(stop1, stop2, stop3));
        route3.setStops(List.of(stop1, stop2, stop3, stop4));

        stopService.saveStop(stop1);
        stopService.saveStop(stop2);
        stopService.saveStop(stop3);
        stopService.saveStop(stop4);
        stopService.saveStop(stop5);
        routeService.saveRoute(route1);
        routeService.saveRoute(route2);
        routeService.saveRoute(route3);


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
                    Seat seat2 = new Seat(i + letter, 2, isBookedTrue, route2);
                    Seat seat3 = new Seat(i + letter, 2, isBookedTrue, route3);
                    seatService.saveSeat(seat);
                    seatService.saveSeat(seat2);
                    seatService.saveSeat(seat3);
                }
                else {
                    Seat seat = new Seat(i + letter, 1, isBooked, route1);
                    Seat seat2 = new Seat(i + letter, 2, isBookedTrue, route2);
                    Seat seat3 = new Seat(i + letter, 2, isBookedTrue, route3);
                    seatService.saveSeat(seat);
                    seatService.saveSeat(seat2);
                    seatService.saveSeat(seat3);
                }
            }
        }

        Reservation reservation1 = new Reservation("John Doe", route1, List.of(seatService.getAllSeats().get(0)));
        String authToken = UUID.randomUUID().toString();
        reservation1.setAuthToken(authToken);
        reservation1.setDepartureStop(stop1);
        reservation1.setArrivalStop(stop3);
        reservationService.saveReservation(reservation1);
        Reservation reservation2 = new Reservation("Jane Doe", route1, List.of(seatService.getSeatDetails(2L).get()));
        reservationService.saveReservation(reservation2);
    }


}

