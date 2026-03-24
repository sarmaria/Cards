package com.banking.cards.config;

import com.banking.cards.audit.AuditorAwareImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class AuditConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void auditorProvider() {
        contextRunner
                .withUserConfiguration(AuditConfig.class)
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .hasSingleBean(AuditorAwareImpl.class));
    }
}