package com.example.sugarroad2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class SugarRoad2Application {

	public static void main(String[] args) {
		SpringApplication.run(SugarRoad2Application.class, args);
	}

}
