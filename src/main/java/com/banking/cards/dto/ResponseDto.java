package com.banking.cards.dto;


import org.springframework.http.HttpStatus;

public record ResponseDto(String message, HttpStatus status) {
}
