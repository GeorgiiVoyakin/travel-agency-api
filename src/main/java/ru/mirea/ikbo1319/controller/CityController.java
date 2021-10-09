package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.CityModelAssembler;
import ru.mirea.ikbo1319.dto.CityDto;
import ru.mirea.ikbo1319.exception.CityNotFoundException;
import ru.mirea.ikbo1319.factory.CityDtoFactory;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.service.CityService;
import ru.mirea.ikbo1319.service.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class CityController {
    private final CityService cityService;
    private final CountryService countryService;
    private final CityModelAssembler cityModelAssembler;
    private final ControllersUtils controllersUtils;
    private final CityDtoFactory cityDtoFactory;

    @GetMapping("/cities")
    public List<CityDto> getAll() {
        List<City> cities = cityService.findAll();

        return cityDtoFactory.createListOfCityDto(cities);
    }

    @GetMapping("/cities/{id}")
    public CityDto getById(@PathVariable Long id) {
        return cityDtoFactory
                .createCityDto(cityService
                        .findById(id)
                        .orElseThrow(() -> new CityNotFoundException(id)));
    }

    @PostMapping("/cities")
    public ResponseEntity<?> addNewCity(@RequestParam String name) {
        City newCity = City.builder().name(name).build();

        cityService.save(newCity);
        return ResponseEntity
                .created(linkTo(methodOn(CityController.class).getById(newCity.getId())).toUri())
                .body(cityDtoFactory.createCityDto(newCity));
    }

    @PatchMapping("/cities/{id}")
    public ResponseEntity<?> updateCity(
            @PathVariable Long id,
            @RequestParam Optional<String> optionalName,
            @RequestParam Optional<Long> optionalCountryId) {

        City city = controllersUtils.getCityOrThrowNotFound(id);

        optionalCountryId.ifPresent(
                countryId -> city.getCountries().add(controllersUtils.getCountryOrThrowNotFound(countryId))
        );

        optionalName.ifPresent(city::setName);
        cityService.save(city);

        return ResponseEntity.ok().body(cityDtoFactory.createCityDto(city));
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        cityService.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        cityService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}

