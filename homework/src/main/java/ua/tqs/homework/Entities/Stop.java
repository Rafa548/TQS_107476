package ua.tqs.homework.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "stop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cityName;

    private String arrivalTime;

    private String departureTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "seat_stop",
            joinColumns = @JoinColumn(name = "seat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id",
                    referencedColumnName = "id"))
    private List<Seat> leavingSeats;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Stop(String cityName, String arrivalTime, String departureTime, Route route) {
        this.cityName = cityName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.route = route;
    }
}
