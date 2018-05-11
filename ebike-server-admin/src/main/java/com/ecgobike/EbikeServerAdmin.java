package com.ecgobike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EbikeServerAdmin {

	public static void main(String[] args) {
		SpringApplication.run(EbikeServerAdmin.class, args);
	}
}
