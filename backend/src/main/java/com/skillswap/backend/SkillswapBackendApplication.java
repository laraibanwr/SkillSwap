package com.skillswap.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SkillswapBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillswapBackendApplication.class, args);
	}

}
