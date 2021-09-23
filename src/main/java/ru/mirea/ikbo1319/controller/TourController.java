package ru.mirea.ikbo1319.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.TourModelAssembler;
import ru.mirea.ikbo1319.dto.TourDto;
import ru.mirea.ikbo1319.exception.CityNotFoundException;
import ru.mirea.ikbo1319.exception.CountryNotFoundException;
import ru.mirea.ikbo1319.exception.TourNotFoundException;
import ru.mirea.ikbo1319.model.Beach;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.model.Tour;
import ru.mirea.ikbo1319.service.CityService;
import ru.mirea.ikbo1319.service.CountryService;
import ru.mirea.ikbo1319.service.TourService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TourController {
    private final TourService tourService;
    private final CountryService countryService;
    private final CityService cityService;
    private final TourModelAssembler tourModelAssembler;

    public TourController(
            TourService tourService,
            CountryService countryService,
            CityService cityService,
            TourModelAssembler tourModelAssembler) {

        this.tourService = tourService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.tourModelAssembler = tourModelAssembler;
    }

    @GetMapping("/tours")
    public CollectionModel<EntityModel<Tour>> getAll() {
        List<EntityModel<Tour>> tours = tourService
                .findAll()
                .stream()
                .map(tourModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tours, linkTo(methodOn(TourController.class).getAll()).withSelfRel());
    }

    @GetMapping("/tours/{id}")
    public EntityModel<Tour> getById(@PathVariable Long id) {
        return tourModelAssembler
                .toModel(tourService
                        .findById(id)
                        .orElseThrow(() -> new TourNotFoundException(id)));
    }

    @PostMapping("/tours/countries/{country_id}")
    public ResponseEntity<?> addNewTour(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam short distance,
            @RequestParam boolean seaResort,
            @RequestParam Beach beach,
            @RequestParam boolean wirelessInternet,
            @RequestParam boolean meal,
            @PathVariable("country_id") Long countryId) {

        Country country = getCountryOrThrowNotFound(countryId);

        Tour newTour = Tour
                .builder()
                .name(name)
                .description(description)
                .price(price)
                .distance(distance)
                .seaResort(seaResort)
                .beach(beach)
                .wirelessInternet(wirelessInternet)
                .meal(meal)
                .country(country)
                .build();

        tourService.save(newTour);
        return ResponseEntity
                .created(linkTo(methodOn(TourController.class).getById(newTour.getId())).toUri())
                .body(null);
    }

    @GetMapping("/tours/{country_id}")
    public ResponseEntity<?> find(
            @RequestParam Long cityId,
            @PathVariable("country_id") Long countryId,
            @RequestParam int days,
            @RequestParam int people,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Instant date) {

        // Check if tour from City to Country available
        Country country = getCountryOrThrowNotFound(countryId);

        boolean available = cityService.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId))
                .getCountries()
                .contains(country);

        if (available) {
            List<Tour> tours = country.getTours();
            Map<Long, Long> prices = new HashMap<>();
            tours.forEach(t -> prices.put(t.getId(), t.getPrice() * people * days));

            CollectionModel<Tour> tourCollectionModel = CollectionModel.of(
                    tours,
                    linkTo(
                            methodOn(TourController.class)
                                    .getAll())
                            .withSelfRel());

            TourDto tourDto = new TourDto(tourCollectionModel, prices, days, people, date);
            return new ResponseEntity<>(
                    tourDto,
                    HttpStatus.OK);
        } else {
            City city = getCityOrThrowNotFound(cityId);
            return new ResponseEntity<>(
                    "Не удалось найти туров из " + city.getName() + " в " + country.getName(),
                    HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable Long id) {
        tourService.findById(id)
                .orElseThrow(() -> new TourNotFoundException(id));

        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Country getCountryOrThrowNotFound(Long countryId) {
        return countryService.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }

    private City getCityOrThrowNotFound(Long cityId) {
        return cityService.findById(cityId)
                .orElseThrow(() -> new CountryNotFoundException(cityId));
    }
}
