package ru.mirea.ikbo1319.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.TourModelAssembler;
import ru.mirea.ikbo1319.exception.TourNotFoundException;
import ru.mirea.ikbo1319.model.Tour;
import ru.mirea.ikbo1319.service.TourService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TourController {
    private final TourService tourService;
    private final TourModelAssembler tourModelAssembler;

    public TourController(TourService tourService, TourModelAssembler tourModelAssembler) {
        this.tourService = tourService;
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

    @PostMapping("/tours")
    public ResponseEntity<?> addNewTour(@RequestParam String name, @RequestParam String description) {
        Tour newTour = Tour
                .builder()
                .name(name)
                .description(description)
                .build();

        tourService.save(newTour);
        return ResponseEntity
                .created(linkTo(methodOn(TourController.class).getById(newTour.getId())).toUri())
                .body(null);
    }

    @GetMapping("/tours/find/{country_id}")
    public List<Tour> find(@PathVariable("country_id") Long countryId) {
        return tourService.find(countryId);
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable Long id) {
        tourService.findById(id)
                .orElseThrow(() -> new TourNotFoundException(id));

        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
