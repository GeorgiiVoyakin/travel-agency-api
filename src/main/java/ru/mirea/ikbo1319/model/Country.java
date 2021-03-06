package ru.mirea.ikbo1319.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Country {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    @ManyToMany(mappedBy = "countries")
    private Set<City> cities = new HashSet<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Tour> tours = new ArrayList<>();

    public Country() {}
}
