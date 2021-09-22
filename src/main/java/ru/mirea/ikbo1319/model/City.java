package ru.mirea.ikbo1319.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class City {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "city_countries",
            joinColumns = { @JoinColumn(name = "city_id") },
            inverseJoinColumns = { @JoinColumn(name = "country_id") }
    )
    private Set<Country> countries = new HashSet<>();

    public City() {}
}
