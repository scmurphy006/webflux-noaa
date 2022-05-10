package com.smurphy.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Test class to demonstrate the results of this assignment. Uses WebTestClient
 * to call the endpoint in our app where this data is stored
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterviewApplicationTests {

	// The fields we need to test for
	private final static String DAY_NAME = "day_name";

	private final static String TEMP_HIGH_CELSIUS = "temp_high_celsius";

	private final static String FORECAST_BLURP = "forecast_blurp";

	@Autowired
	private WebTestClient webTestClient;

	/**
	 * Test that gets the forecast for today from the NOAA weather API.
	 * Asserts that the correct tags were populated from the API call.
	 */
	@Test
	void testGetTodayForecast() {
		webTestClient
				.get().uri("/forecast/today")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).value(result -> {
					// Print resulting json to the console for the sake of this demo only
					// I would not do this in an actual project
					System.out.println(result);

					assertEquals(1, StringUtils.countMatches(result, DAY_NAME));
					assertEquals(1, StringUtils.countMatches(result, TEMP_HIGH_CELSIUS));
					assertEquals(1, StringUtils.countMatches(result, FORECAST_BLURP));
				});
	}

	/**
	 * Test that gets the forecast for the next 5 days from the NOAA weather API.
	 * Asserts that the number of forecast periods recieved match the number of
	 * days.
	 */
	@Test
	void testFiveDayForecast() {
		int numDays = 5;
		webTestClient
				.get().uri("/forecast/" + numDays)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).value(result -> {
					// Print resulting json to the console for the sake of this demo only
					// I would not do this in an actual project
					System.out.println(result);

					assertEquals(numDays, StringUtils.countMatches(result, DAY_NAME));
					assertEquals(numDays, StringUtils.countMatches(result, TEMP_HIGH_CELSIUS));
					assertEquals(numDays, StringUtils.countMatches(result, FORECAST_BLURP));
				});
	}
}
