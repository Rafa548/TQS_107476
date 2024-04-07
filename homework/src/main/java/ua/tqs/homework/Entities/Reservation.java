package ua.tqs.homework.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnoreProperties({"reservations","stops","seats"})
    private Route route;

    @ManyToOne
    @JoinColumn(name = "dep_stop_id")
    @JsonIgnoreProperties({"departingReservations","arrivingReservations","leavingSeats","routes","arrivalTime"})
    private Stop departureStop;

    @ManyToOne
    @JoinColumn(name = "arr_stop_id")
    @JsonIgnoreProperties({"departingReservations","arrivingReservations","leavingSeats","routes","departureTime"})
    private Stop arrivalStop;

    @ManyToMany
    @JoinTable(name = "seat_reservation",
            joinColumns = @JoinColumn(name = "seat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id",
                    referencedColumnName = "id"))
    @JsonIgnoreProperties({"reservations","route","stops"})
    private List<Seat> seats;

    @Column(unique = true)
    private String authToken;

}
