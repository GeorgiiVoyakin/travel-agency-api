package ru.mirea.ikbo1319.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(Long id) {
        super("Could not find city with id: " + id);
    }
}
