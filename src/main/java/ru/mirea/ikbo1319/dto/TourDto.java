package ru.mirea.ikbo1319.dto;

import lombok.Data;
import org.springframework.hateoas.CollectionModel;
import ru.mirea.ikbo1319.model.Tour;

import java.time.Instant;
import java.util.Map;

@Data
public class TourDto {
    private final CollectionModel<Tour> tours;
    private final Map<Long, Long> prices;
    private final Integer days;
    private final Integer people;
    private final Instant date;
}
