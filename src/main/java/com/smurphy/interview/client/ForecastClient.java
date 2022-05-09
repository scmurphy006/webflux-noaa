package com.smurphy.interview.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smurphy.interview.excpetion.IllegalForecastException;
import com.smurphy.interview.model.Forecast;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * Service bean to consume a Forecast Object from the NOAA weather API
 */
@Service
public class ForecastClient {

    private final WebClient client;

    /**
     * Builds a WebClient for consuming Forecast Objects. The base url is of the
     * following format. this base url could be split up later so the office and
     * grid parameters are passed into the get methods.
     * 
     * api.weather.gov/gridpoints/{office}/{grid X},{grid Y}/forecast
     * 
     * @param builder An instance of Spring's WebClient Builder
     */
    public ForecastClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("https://api.weather.gov/gridpoints/MLB/33,70/forecast").build();
    }

    /**
     * Get today's forecast from NOAA's weather API
     * 
     * @return Mono<String>: A Mono emitting the JSON representation of today's
     *         forecast
     */
    public Mono<String> getTodaysForecast() {
        return getNDayForecast(1);
    }

    /**
     * Get the JSON representation of the forecast for the next N days from NOAA's
     * weather API
     * 
     * @param numDays The number of days to forecast
     * @return Mono<String>: A Mono emitting the JSON representation of the next N
     *         day's forecasts
     */
    public Mono<String> getNDayForecast(int numDays) {
        // Use the client already pointing to the NOAA weather endpoint
        return this.client.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // Annotations on our Forecast model handle the deserialization for us
                .bodyToMono(Forecast.class)
                // For the sake of this assignment, simply serialize and return a Forecast for
                // the desired number of days
                .map(f -> {
                    try {
                        return new ObjectMapper().writerWithDefaultPrettyPrinter()
                                .writeValueAsString(new Forecast(f, numDays));
                    } catch (JsonProcessingException ex) {
                        // This would be either presented to the user or retried depending on the
                        // project's requirements
                        throw new IllegalForecastException("Unable to serialize the passed in Forecast.", ex);
                    }
                });
    }
}
