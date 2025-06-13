package com.danielsilva.imcApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class ImcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImcApplication.class, args);
	}

}
