package ua.tqs.homework.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatIdentifier;

    private int priceMultiplier;

    @ElementCollection
    private List<Boolean> isBooked;

    public Seat(String seatIdentifier,int priceMultiplier, List<Boolean> isBooked, Route route) {
        this.seatIdentifier = seatIdentifier;
        this.isBooked = isBooked;
        this.route = route;
        this.priceMultiplier = priceMultiplier;
    }

    @ManyToMany(mappedBy = "seats")
    @JsonIgnoreProperties({"seats","route",""})
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @JsonIgnoreProperties({"seats","stops","reservations"})
    private Route route;

}
