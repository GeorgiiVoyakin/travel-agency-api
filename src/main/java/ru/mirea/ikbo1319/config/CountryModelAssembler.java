package ru.mirea.ikbo1319.config;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.controller.CountryController;
import ru.mirea.ikbo1319.model.Country;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CountryModelAssembler implements RepresentationModelAssembler<Country, EntityModel<Country>> {
    @Override
    public EntityModel<Country> toModel(Country country) {
        return EntityModel.of(country,
                linkTo(methodOn(CountryController.class).getById(country.getId())).withSelfRel(),
                linkTo(methodOn(CountryController.class).getAll()).withRel("countries"));
    }
}
