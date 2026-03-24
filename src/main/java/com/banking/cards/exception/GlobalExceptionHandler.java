package com.banking.cards.exception;

import com.banking.cards.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> resourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest request) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                request.getDescription(false),
                e.getMessage(),
                HttpStatus.CONFLICT,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                request.getDescription(false),
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
       Map<String, String> errors = e.getBindingResult().getFieldErrors()
               .stream()
               .collect(Collectors.toMap(
                       FieldError::getField,
                       fieldError -> fieldError.getDefaultMessage()!=null?fieldError.getDefaultMessage():"Invalid value"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodValidation(ConstraintViolationException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                Instant.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
}
