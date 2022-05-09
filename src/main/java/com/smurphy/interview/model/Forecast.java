package com.smurphy.interview.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.smurphy.interview.deserializer.ForecastDeserializer;

/**
 * Forecast Model to represent the results of our API consumption
 */
@JsonDeserialize(using = ForecastDeserializer.class)
public class Forecast {

    private final List<Period> periods;

    /**
     * Creates instance of Forecast
     * 
     * @param periods The period objects making up this forecast
     */
    public Forecast(List<Period> periods) {
        this.periods = periods;
    }

    /**
     * Copy constructor for Forecast that takes a forecast and a number of days and
     * returns a new forecast with only the number of days specified.
     * 
     * Throws an IllegalArgumentException if the number of days is less than 1
     * 
     * @param forecast The forecsat to copy
     * @param numDays  The number of days to include in the new forecast
     */
    public Forecast(Forecast forecast, int numDays) {
        if (numDays < 1) {
            throw new IllegalArgumentException("The number of days must be a positive integer.");
        }
        // Stream over the periods in the forecast to be copied
        this.periods = forecast.getPeriods().stream()
                // We always want the first entry as it represents today
                // Otherwise, we only want the daytime entries
                .filter(p -> forecast.getPeriods().indexOf(p) == 0 || p.getIsDaytime())
                .limit(numDays)
                .collect(Collectors.toList());
    }

    /**
     * Getter for the list of periods in this forecast
     * 
     * @return List<Period>: The forecast periods
     */
    @JsonProperty("daily")
    public List<Period> getPeriods() {
        return periods;
    }
}
