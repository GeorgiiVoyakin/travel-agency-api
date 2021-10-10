package ru.mirea.ikbo1319.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.ikbo1319.factory.CalculatedTourDtoFactory;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.model.Tour;

import java.util.List;

@RestController
@AllArgsConstructor
public class FindController {
    private final CalculatedTourDtoFactory calculatedTourDtoFactory;
    private final ControllersUtils controllersUtils;

    @GetMapping("/tours/find/{country_id}")
    public ResponseEntity<?> find(
            @RequestParam Long cityId,
            @RequestParam int days,
            @RequestParam int people,
            @PathVariable("country_id") Long countryId) {

        // Check if tour from City to Country available
        Country country = controllersUtils.getCountryOrThrowNotFound(countryId);

        boolean available = controllersUtils.getCityOrThrowNotFound(cityId).getCountries().contains(country);

        if (available) {
            List<Tour> tours = country.getTours();

            return ResponseEntity.ok(calculatedTourDtoFactory.createListOfCalculatedTourDto(tours, people, days));
        } else {
            City city = controllersUtils.getCityOrThrowNotFound(cityId);
            return ResponseEntity.ok("Не удалось найти туров из " + city.getName() + " в " + country.getName());
        }
    }
}
