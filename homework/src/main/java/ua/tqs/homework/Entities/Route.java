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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "route")
    @JsonIgnoreProperties("route")
    private List<Stop> stops;

    @OneToMany(mappedBy = "route")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "route")
    @JsonIgnoreProperties("route")
    private List<Seat> seats;

    public Route( List<Seat> seats) {
        this.seats = seats;
    }
}
