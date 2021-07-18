package io.asiam.tansiq.aspects;

import io.asiam.tansiq.exceptions.StorageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class FileErrorHandler {
    @ExceptionHandler(value = {StorageException.class})
    public ResponseEntity<Map<String, Object>> handleStorageError(StorageException ex) {
        return new ResponseEntity<>(
                Map.of("success", false, "message", ex.getLocalizedMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }
}
