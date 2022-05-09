package com.smurphy.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Spring Boot Application to consume data from the NOAA weather API
 */
@SpringBootApplication
public class InterviewApplication {

	/**
	 * The main function is the entry point of the application. It uses Spring
	 * Boot's
	 * SpringApplication.run() method to launch the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}
}
