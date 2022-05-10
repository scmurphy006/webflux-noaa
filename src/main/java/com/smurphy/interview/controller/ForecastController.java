package com.smurphy.interview.controller;

import com.smurphy.interview.client.ForecastClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Simple controller to provide access to Forecast objects. In a real
 * application we would probably get the forecast data from a database, but for
 * the sake of this assignment we are getting it straight from the
 * ForecastClient
 */
@RestController
public class ForecastController {

    @Autowired
    private ForecastClient client;

    /**
     * Get today's forecast from NOAA's weather API
     * 
     * @return Mono<String>: A Mono emitting the JSON representation of today's
     *         forecast
     */
    @GetMapping(value = "/forecast/today")
    public Mono<String> getTodaysForecast() {
        return client.getTodaysForecast();
    }

    /**
     * Get the JSON representation of the forecast for the next N days from NOAA's
     * weather API
     * 
     * @param numDays The number of days to forecast
     * @return Mono<String>: A Mono emitting the JSON representation of the next N
     *         day's forecasts
     */
    @RequestMapping(value = "/forecast/{numDays}")
    public Mono<String> getNDayForecast(@PathVariable("numDays") int numDays) {
        return client.getNDayForecast(numDays);
    }
}
