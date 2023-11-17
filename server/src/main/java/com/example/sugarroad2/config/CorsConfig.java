package com.example.sugarroad2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {

      CorsConfiguration config = new CorsConfiguration();

      config.setAllowCredentials(true);
      config.setAllowedOrigins(List.of("http://localhost:5173")); //외부 접근이 허용된 URL
      config.setAllowedHeaders(List.of("*"));
      config.setAllowedMethods(List.of("*")); //CRUD 전부 허용
      config.setExposedHeaders(List.of("*"));

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);
      return source;
   }

}
