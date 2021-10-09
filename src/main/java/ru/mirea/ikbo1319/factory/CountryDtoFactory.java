package ru.mirea.ikbo1319.factory;

import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.dto.CountryDto;
import ru.mirea.ikbo1319.model.Country;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryDtoFactory {
    public CountryDto createCountryDto(Country country){
        return new CountryDto(country.getId(), country.getName());
    }

    public List<CountryDto> createListOfCountryDto(List<Country> countries) {
        return countries
                .stream()
                .map(this::createCountryDto)
                .collect(Collectors.toList());
    }
}
