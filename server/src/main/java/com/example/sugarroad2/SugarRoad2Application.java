package com.example.sugarroad2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.sugarroad2"})
@EnableJpaRepositories(basePackages = {"com.example.sugarroad2.repository"})
@EntityScan(basePackages = {"com.example.sugarroad2.model.entity"})
public class SugarRoad2Application {

	public static void main(String[] args) {
		SpringApplication.run(SugarRoad2Application.class, args);
	}

}
