package ru.mirea.ikbo1319.service;

import org.springframework.stereotype.Service;
import ru.mirea.ikbo1319.model.City;
import ru.mirea.ikbo1319.repository.CityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    public void save(City newCity) {
        cityRepository.save(newCity);
    }

    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
