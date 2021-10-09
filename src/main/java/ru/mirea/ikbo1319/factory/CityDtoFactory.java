package ru.mirea.ikbo1319.factory;

import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.dto.CityDto;
import ru.mirea.ikbo1319.model.City;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityDtoFactory {
    public CityDto createCityDto(City city){
        return new CityDto(city.getId(), city.getName());
    }

    public List<CityDto> createListOfCityDto(List<City> cities) {
        return cities
                .stream()
                .map(this::createCityDto)
                .collect(Collectors.toList());
    }
}
