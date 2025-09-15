package com.dbiServer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbiServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner cmdLineRunner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				System.out.println("DBI Server is running...");
			}
		};
	}

}
