package com.gaurav.restaurantsearchservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Restaurant {
    String name;
    String location;
    double distanceKm;
    double budget;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Restaurant(String name, String location, double distanceKm, double budget) {
        this.name = name;
        this.location = location;
        this.distanceKm = distanceKm;
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", distance_km=" + distanceKm +
                ", budget=" + budget +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
