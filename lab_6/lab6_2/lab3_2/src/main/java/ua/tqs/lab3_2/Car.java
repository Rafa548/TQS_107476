package ua.tqs.lab3_2;


import jakarta.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    private String maker;

    private String model;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Car(String maker, String model, Long id) {
        this.maker = maker;
        this.model = model;
        this.id = id;
    }

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }


    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
