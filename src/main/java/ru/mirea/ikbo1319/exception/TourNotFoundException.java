package ru.mirea.ikbo1319.exception;

public class TourNotFoundException extends RuntimeException {
    public TourNotFoundException(Long id) {
        super("Could not find tour with id: " + id);
    }
}
