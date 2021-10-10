package ru.mirea.ikbo1319.service;

import org.springframework.stereotype.Service;
import ru.mirea.ikbo1319.model.Tour;
import ru.mirea.ikbo1319.repository.TourRepository;

import java.util.List;
import java.util.Optional;

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

    public void deleteById(Long id) {
        tourRepository.deleteById(id);
    }
}
