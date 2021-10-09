package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.CountryModelAssembler;
import ru.mirea.ikbo1319.exception.CountryNotFoundException;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.service.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final CountryModelAssembler countryModelAssembler;
    private final ControllersUtils controllersUtils;

    @GetMapping("/countries")
    public CollectionModel<EntityModel<Country>> getAll() {
        List<EntityModel<Country>> countries = countryService
                .findAll()
                .stream()
                .map(countryModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(countries, linkTo(methodOn(CountryController.class).getAll()).withSelfRel());
    }

    @GetMapping("/countries/{id}")
    public EntityModel<Country> getById(@PathVariable Long id) {
        return countryModelAssembler
                .toModel(countryService
                        .findById(id)
                        .orElseThrow(() -> new CountryNotFoundException(id)));
    }

    @PostMapping("/countries")
    public ResponseEntity<?> addNewCountry(@RequestParam String name) {
        Country newCountry = Country.builder().name(name).build();

        countryService.save(newCountry);
        return ResponseEntity
                .created(linkTo(methodOn(CountryController.class).getById(newCountry.getId())).toUri())
                .body(null);
    }

    @PatchMapping("/countries/{id}")
    public ResponseEntity<?> updateCountry(
            @PathVariable Long id,
            @RequestParam Optional<String> optionalName,
            @RequestParam Optional<Long> optionalCityId,
            @RequestParam Optional<Long> optionalTourId) {

        Country country = controllersUtils.getCountryOrThrowNotFound(id);

        optionalCityId.ifPresent(cityId -> country.getCities().add(controllersUtils.getCityOrThrowNotFound(cityId)));
        optionalTourId.ifPresent(tourId -> country.getTours().add(controllersUtils.getTourOrThrowNotFound(tourId)));

        optionalName.ifPresent(country::setName);
        countryService.save(country);

        return ResponseEntity.ok().body(country);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        countryService.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));

        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
