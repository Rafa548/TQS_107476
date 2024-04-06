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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String cityName;

    private String arrivalTime;

    private String departureTime;


    //probably desnecessario
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "seat_stop",
            joinColumns = @JoinColumn(name = "seat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id",
                    referencedColumnName = "id"))
    private List<Seat> leavingSeats;

    @ManyToMany(mappedBy = "stops")
    private List<Route> routes;

    public Stop(String cityName, String arrivalTime, String departureTime) {
        this.cityName = cityName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
