package com.dev.accessrefreshtoken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AccessRefreshTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessRefreshTokenApplication.class, args);
	}

}
