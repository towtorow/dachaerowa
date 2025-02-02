package com.dachaerowa.dachaerowa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories
public class DachaerowaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DachaerowaApplication.class, args);
	}

}
