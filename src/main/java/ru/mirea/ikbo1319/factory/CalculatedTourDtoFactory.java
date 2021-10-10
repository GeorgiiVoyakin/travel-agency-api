package ru.mirea.ikbo1319.factory;

import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.dto.CalculatedTourDto;
import ru.mirea.ikbo1319.dto.TourDto;
import ru.mirea.ikbo1319.model.Tour;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculatedTourDtoFactory {
    public CalculatedTourDto createCalculatedTourDto(Tour tour, int people, int days){
        return CalculatedTourDto.builder()
                .name(tour.getName())
                .description(tour.getDescription())
                .calculatedPrice(tour.getPrice() * people * days)
                .distance(tour.getDistance())
                .seaResort(tour.getSeaResort())
                .beach(tour.getBeach())
                .wirelessInternet(tour.getWirelessInternet())
                .meal(tour.getMeal())
                .build();
    }

    public List<CalculatedTourDto> createListOfCalculatedTourDto(List<Tour> tours, int people, int days) {
        return tours
                .stream()
                .map(t -> this.createCalculatedTourDto(t, people, days))
                .collect(Collectors.toList());
    }
}
