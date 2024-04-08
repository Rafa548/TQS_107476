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




    public static void main(String[] args) {
        SpringApplication.run(BusApplication.class, args);
    }


}
