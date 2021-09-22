package ru.mirea.ikbo1319.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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
    
    public Country() {}
}
