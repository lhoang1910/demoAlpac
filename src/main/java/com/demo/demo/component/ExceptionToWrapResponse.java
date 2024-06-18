package com.demo.demo.component;

import com.demo.demo.component.constant.HttpStatusCodes;
import com.demo.demo.dto.response.WrapResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class ExceptionToWrapResponse {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static ResponseEntity<WrapResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        return buildResponseEntity(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public static ResponseEntity<WrapResponse<?>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<String> errors = new HashSet<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
        return buildResponseEntity(errors);
    }

    private static ResponseEntity<WrapResponse<?>> buildResponseEntity(Set<String> errors) {
        WrapResponse<?> response = WrapResponse.builder()
                .isSuccess(false)
                .status(HttpStatusCodes.BAD_REQUEST)
                .message(String.join(", ", errors))
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
