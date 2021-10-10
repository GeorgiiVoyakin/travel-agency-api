package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.TourModelAssembler;
import ru.mirea.ikbo1319.dto.TourDto;
import ru.mirea.ikbo1319.exception.TourNotFoundException;
import ru.mirea.ikbo1319.factory.TourDtoFactory;
import ru.mirea.ikbo1319.model.Beach;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.model.Tour;
import ru.mirea.ikbo1319.service.TourService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class TourController {
    private final TourService tourService;
    private final TourModelAssembler tourModelAssembler;
    private final TourDtoFactory tourDtoFactory;
    private final ControllersUtils controllersUtils;

    @GetMapping("/tours")
    public List<TourDto> getAll() {
        List<Tour> tours = tourService.findAll();

        return tourDtoFactory.createListOfTourDto(tours);
    }

    @GetMapping("/tours/{id}")
    public TourDto getById(@PathVariable Long id) {
        return tourDtoFactory
                .createTourDto(tourService
                        .findById(id)
                        .orElseThrow(() -> new TourNotFoundException(id)));
    }

    @PostMapping("/tours/countries/{country_id}")
    public ResponseEntity<?> addNewTour(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long price,
            @RequestParam Optional<Short> optionalDistance,
            @RequestParam boolean seaResort,
            @RequestParam Optional<Beach> optionalBeach,
            @RequestParam boolean wirelessInternet,
            @RequestParam boolean meal,
            @PathVariable("country_id") Long countryId) {

        Country country = controllersUtils.getCountryOrThrowNotFound(countryId);

        if (seaResort && (optionalBeach.isEmpty() || optionalDistance.isEmpty())) {
            return ResponseEntity.badRequest().build();
        }

        Tour newTour = Tour
                .builder()
                .name(name)
                .description(description)
                .price(price)
                .seaResort(seaResort)
                .wirelessInternet(wirelessInternet)
                .meal(meal)
                .country(country)
                .build();

        if (seaResort) {
            optionalBeach.ifPresent(newTour::setBeach);
            optionalDistance.ifPresent(newTour::setDistance);
        }

        tourService.save(newTour);
        return ResponseEntity
                .created(linkTo(methodOn(TourController.class).getById(newTour.getId())).toUri())
                .body(tourDtoFactory.createTourDto(newTour));
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable Long id) {
        tourService.findById(id)
                .orElseThrow(() -> new TourNotFoundException(id));

        tourService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
