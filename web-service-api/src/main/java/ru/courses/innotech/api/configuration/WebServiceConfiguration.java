package ru.courses.innotech.api.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ConfigurationPropertiesScan
public class WebServiceConfiguration {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

}
