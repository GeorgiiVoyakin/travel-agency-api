package ru.mirea.ikbo1319.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ikbo1319.config.TourModelAssembler;
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
        return tourModelAssembler.toModel(tourService.findById(id));
    }

    @PostMapping("/tours")
    public ResponseEntity<?> addNewTour(@RequestBody Tour newTour) {
        tourService.save(newTour);
        return ResponseEntity
                .created(linkTo(methodOn(TourController.class).getById(newTour.getId())).toUri())
                .body(null);
    }

    @GetMapping("/tours/find/{country_id}")
    public List<Tour> find(@PathVariable("country_id") Long countryId) {
        return tourService.find(countryId);
    }
}
