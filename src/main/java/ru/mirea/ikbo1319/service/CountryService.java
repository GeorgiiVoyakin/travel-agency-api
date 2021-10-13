package ru.mirea.ikbo1319.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.repository.CityRepository;
import ru.mirea.ikbo1319.repository.CountryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    public void save(Country newCountry) {
        countryRepository.save(newCountry);
    }

    public void deleteById(Long id) {
        Set<City> cities = countryRepository.getById(id).getCities();
        cities.forEach(city -> {
            city.getCountries().remove(countryRepository.getById(id));
            cityRepository.save(city);
        });
        countryRepository.deleteById(id);
    }
}
