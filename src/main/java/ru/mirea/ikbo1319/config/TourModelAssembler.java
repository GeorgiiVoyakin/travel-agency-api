package ru.mirea.ikbo1319.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.controller.TourController;
import ru.mirea.ikbo1319.model.Tour;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TourModelAssembler implements RepresentationModelAssembler<Tour, EntityModel<Tour>> {
    @Override
    public EntityModel<Tour> toModel(Tour tour) {
        return EntityModel.of(tour,
                linkTo(methodOn(TourController.class).getById(tour.getId())).withSelfRel(),
                linkTo(methodOn(TourController.class).getAll()).withRel("tours"));
    }
}
