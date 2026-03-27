package com.banking.cards.config;

import com.banking.cards.dto.CardsContactInfoDto;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CardsContactInfoDto.class)
public class EnablePropertyConfig {
}
