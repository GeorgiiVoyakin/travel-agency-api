package ru.mirea.ikbo1319.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Country {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Tour> tours;

    public Country() {}
}
