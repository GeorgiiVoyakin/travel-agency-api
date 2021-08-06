package ru.mirea.ikbo1319.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long id) {
        super("Could not find country with id: " + id);
    }
}
