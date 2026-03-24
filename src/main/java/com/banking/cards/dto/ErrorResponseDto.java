package com.banking.cards.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponseDto(String path, String errorMessage, HttpStatus httpStatus, Instant timestamp) {
}
