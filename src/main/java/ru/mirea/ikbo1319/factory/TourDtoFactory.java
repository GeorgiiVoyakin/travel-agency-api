package ru.mirea.ikbo1319.factory;

import org.springframework.stereotype.Component;
import ru.mirea.ikbo1319.dto.TourDto;
import ru.mirea.ikbo1319.model.Tour;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourDtoFactory {
    public TourDto createTourDto(Tour tour){
        return TourDto.builder()
                .name(tour.getName())
                .description(tour.getDescription())
                .price(tour.getPrice())
                .distance(tour.getDistance())
                .seaResort(tour.getSeaResort())
                .beach(tour.getBeach())
                .wirelessInternet(tour.getWirelessInternet())
                .meal(tour.getMeal())
                .build();
    }

    public List<TourDto> createListOfTourDto(List<Tour> tours) {
        return tours
                .stream()
                .map(this::createTourDto)
                .collect(Collectors.toList());
    }
}
