package ru.mirea.ikbo1319.service;

import org.springframework.stereotype.Service;
import ru.mirea.ikbo1319.exception.CountryNotFoundException;
import ru.mirea.ikbo1319.model.Country;
import ru.mirea.ikbo1319.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    public void save(Country newCountry) {
        countryRepository.save(newCountry);
    }
}
