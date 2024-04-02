package ua.tqs.homework.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seat")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int seatNumber;

    private int price;

    @ElementCollection
    private List<Boolean> isBooked;

    public Seat(int seatNumber, List<Boolean> isBooked, Route route) {
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
        this.route = route;
    }

    @ManyToMany(mappedBy = "seats")
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToMany(mappedBy = "leavingSeats")
    private List<Stop> stops;
}
