package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.exception.CountryNotFoundException;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.service.CityService;
import ru.mirea.ikbo1319.service.CountryService;

@Component
@AllArgsConstructor
public class ControllersUtils {
    private final CityService cityService;
    private final CountryService countryService;

    public City getCityOrThrowNotFound(Long cityId) {
        return cityService.findById(cityId)
                .orElseThrow(() -> new CountryNotFoundException(cityId));
    }

    public Country getCountryOrThrowNotFound(Long countryId) {
        return countryService.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }
}
