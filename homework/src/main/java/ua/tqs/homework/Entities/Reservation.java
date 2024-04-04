package ua.tqs.homework.Entities;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;

    private int price;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "dep_stop_id")
    private Stop departureStop;

    @ManyToOne
    @JoinColumn(name = "arr_stop_id")
    private Stop arrivalStop;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "seat_reservation",
            joinColumns = @JoinColumn(name = "seat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id",
                    referencedColumnName = "id"))
    private List<Seat> seats;

    private int confirmationCode;

}
