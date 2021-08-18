package ru.mirea.ikbo1319.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TourNotFoundException extends RuntimeException {
    public TourNotFoundException(Long id) {
        super("Could not find tour with id: " + id);
    }
}
