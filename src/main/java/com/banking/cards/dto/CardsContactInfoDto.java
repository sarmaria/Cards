package com.banking.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = CardsContactInfoDto.PREFIX)
public record CardsContactInfoDto(String message, Map<String, String> contactDetails, String onCallSupport) {
    static final String PREFIX = "cards";
}
