package ua.tqs.homework.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"stops","reservations","seats"})
    private List<Route> routes;


    @OneToMany(mappedBy = "departureStop")
    @JsonIgnoreProperties({"departureStop","arrivalStop","seats:id","route"})
    private List<Reservation> departingReservations;

    @OneToMany(mappedBy = "arrivalStop")
    @JsonIgnoreProperties({"departureStop","arrivalStop","seats","route"})
    private List<Reservation> arrivingReservations;

    public Stop(String cityName, String arrivalTime, String departureTime) {
        this.cityName = cityName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
