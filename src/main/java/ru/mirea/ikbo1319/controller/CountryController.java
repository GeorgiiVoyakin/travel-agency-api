package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.dto.CountryDto;
import ru.mirea.ikbo1319.exception.CountryNotFoundException;
import ru.mirea.ikbo1319.factory.CountryDtoFactory;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.service.CountryService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class CountryController {
    private final CountryService countryService;
    private final ControllersUtils controllersUtils;
    private final CountryDtoFactory countryDtoFactory;

    @GetMapping("/countries")
    public List<CountryDto> getAll() {
        List<Country> countries = countryService.findAll();

        return countryDtoFactory.createListOfCountryDto(countries);
    }

    @GetMapping("/countries/{id}")
    public CountryDto getById(@PathVariable Long id) {
        return countryDtoFactory
                .createCountryDto(countryService
                        .findById(id)
                        .orElseThrow(() -> new CountryNotFoundException(id)));
    }

    @PostMapping("/countries")
    public ResponseEntity<?> addNewCountry(@RequestParam String name) {
        Country newCountry = Country.builder().name(name).build();

        countryService.save(newCountry);
        return ResponseEntity
                .created(linkTo(methodOn(CountryController.class).getById(newCountry.getId())).toUri())
                .body(countryDtoFactory.createCountryDto(newCountry));
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

        return ResponseEntity.ok(countryDtoFactory.createCountryDto(country));
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        countryService.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));

        countryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
