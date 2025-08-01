package com.kingsware.kdev;

import com.kingsware.kdev.core.context.SpringContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
@Import(SpringContext.class)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
