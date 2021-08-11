package ru.mirea.ikbo1319.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Country {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;

    public Country() {}
}
