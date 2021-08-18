package ru.mirea.ikbo1319.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.CityModelAssembler;
import ru.mirea.ikbo1319.exception.CityNotFoundException;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.service.CityService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CityController {
    private final CityService cityService;
    private final CityModelAssembler cityModelAssembler;

    public CityController(CityService cityService, CityModelAssembler cityModelAssembler) {
        this.cityService = cityService;
        this.cityModelAssembler = cityModelAssembler;
    }

    @GetMapping("/cities")
    public CollectionModel<EntityModel<City>> getAll() {
        List<EntityModel<City>> cities = cityService
                .findAll()
                .stream()
                .map(cityModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cities, linkTo(methodOn(CityController.class).getAll()).withSelfRel());
    }

    @GetMapping("/cities/{id}")
    public EntityModel<City> getById(@PathVariable Long id) {
        return cityModelAssembler
                .toModel(cityService
                        .findById(id)
                        .orElseThrow(() -> new CityNotFoundException(id)));
    }

    @PostMapping("/cities")
    public ResponseEntity<?> addNewCity(@RequestParam String name) {
        City newCity = City.builder().name(name).build();

        cityService.save(newCity);
        return ResponseEntity
                .created(linkTo(methodOn(CityController.class).getById(newCity.getId())).toUri())
                .body(null);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        cityService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

