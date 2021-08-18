package ru.mirea.ikbo1319.service;

import org.springframework.stereotype.Service;
import ru.mirea.ikbo1319.model.Tour;
import ru.mirea.ikbo1319.repository.TourRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public Optional<Tour> findById(Long id) {
        return tourRepository.findById(id);
    }

    public void save(Tour newTour) {
        tourRepository.save(newTour);
    }

    public List<Tour> find(Long country_id) {
        return tourRepository
                .findAll()
                .stream()
                .filter(tour -> Objects.equals(tour.getCountry().getId(), country_id))
                .collect(Collectors.toList());
    }
}
