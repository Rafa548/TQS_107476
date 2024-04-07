package ua.tqs.homework.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "route")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "stop_route",
            joinColumns = @JoinColumn(name = "stop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "route_id",
                    referencedColumnName = "id"))
    @JsonIgnoreProperties({"routes","leavingSeats","departingReservations","arrivingReservations"})
    private List<Stop> stops;

    @OneToMany(mappedBy = "route")
    @JsonIgnoreProperties({"route",})
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "route")
    @JsonIgnoreProperties({"route","stops"})
    private List<Seat> seats;

    public Route( List<Seat> seats) {
        this.seats = seats;
    }
}
