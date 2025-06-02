package cz.jurina.tennisclub.tennisclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for REST controllers.
 * Handles:
 * <ul>
 *   <li>{@link IllegalArgumentException} → HTTP 400 Bad Request</li>
 * </ul>
 *
 * @author Lukas Jurina
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
