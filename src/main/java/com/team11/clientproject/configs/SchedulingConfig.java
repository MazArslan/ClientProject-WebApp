package com.team11.clientproject.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// reference: https://stackoverflow.com/questions/29014496/disable-enablescheduling-on-spring-tests
// accessed 24/11/2018
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
@Profile("!test")
@EnableAsync
@EnableScheduling
public class SchedulingConfig {
}
