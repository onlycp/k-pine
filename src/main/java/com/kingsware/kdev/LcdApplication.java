package com.kingsware.kdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LcdApplication {

	public static void main(String[] args) {
		SpringApplication.run(LcdApplication.class, args);
	}

}
