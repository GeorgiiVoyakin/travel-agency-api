package ru.mirea.ikbo1319.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Tour {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Tour() {}
}
