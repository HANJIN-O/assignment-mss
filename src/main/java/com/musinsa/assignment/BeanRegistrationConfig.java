package com.musinsa.assignment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

@Configuration
public class BeanRegistrationConfig {
  private String activeProfile;

  BeanRegistrationConfig(Environment env){
    activeProfile = env.getRequiredProperty("spring.profiles.active");
  }

  @Bean
  public Function<Exception, ResponseEntity> exceptionHandlerFunction() throws Exception {
    return switch (activeProfile) {
      case "prod" -> (e) -> ResponseEntity.ok(GeneralResponse.error());
      case "dev", "stage" -> (e) -> ResponseEntity.ok(GeneralResponse.error(e));
      default -> throw new Exception("Unknown value for spring.profiles.active");
    };
  }
}
