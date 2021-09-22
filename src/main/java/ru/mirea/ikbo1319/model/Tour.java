package ru.mirea.ikbo1319.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Tour {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String description;
    private Long price;
    private Short distance;
    private Boolean seaResort;
    private Beach beach;
    private Boolean wirelessInternet;
    private Boolean meal;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    public Tour() {}
}
