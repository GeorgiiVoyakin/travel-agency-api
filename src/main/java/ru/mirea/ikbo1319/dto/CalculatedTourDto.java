package ru.mirea.ikbo1319.dto;

import lombok.Builder;
import lombok.Data;
import ru.mirea.ikbo1319.model.Beach;

@Data
@Builder
public class CalculatedTourDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Long calculatedPrice;
    private final Short distance;
    private final Boolean seaResort;
    private final Beach beach;
    private final Boolean wirelessInternet;
    private final Boolean meal;
}
