package com.smurphy.interview.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Period Model to represent the individual day segments of our API consumption
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Period {

    private final String name;

    private final boolean isDaytime;

    private final double temperature;

    private final String shortForecast;

    /**
     * Creates an instance of Period. Uses JsonCreator annotation to remove the need
     * for a custom deserializer
     * 
     * @param name            The name of the current period
     * @param isDaytime       If this period is from the daytime
     * @param temperature     The current temperature
     * @param temperatureUnit The unit the current temperature is in
     * @param shortForecast   A short blurp on the forecast for this period
     */
    @JsonCreator
    public Period(
            @JsonProperty("name") String name,
            @JsonProperty("isDaytime") boolean isDaytime,
            @JsonProperty("temperature") double temperature,
            @JsonProperty("temperatureUnit") String temperatureUnit,
            @JsonProperty("shortForecast") String shortForecast) {
        this.name = name;
        this.isDaytime = isDaytime;
        this.shortForecast = shortForecast;

        // If the current tamperature is in fahrenheit, convert it to celsius using the
        // convert method in our Unit enum
        TempUnit currentUnit = TempUnit.valueOf(temperatureUnit);
        if (currentUnit.equals(TempUnit.F)) {
            this.temperature = TempUnit.convertTemp(currentUnit, temperature);
        } else {
            this.temperature = temperature;
        }
    }

    /**
     * Name getter. Is serialized as day_name
     * 
     * @return String: The name of this period
     */
    @JsonProperty("day_name")
    public String getName() {
        return this.name;
    }

    /**
     * IsDaytime getter. This field is not serialized
     * 
     * @return boolean: If this represents a daytime period
     */
    @JsonIgnore
    public boolean getIsDaytime() {
        return this.isDaytime;
    }

    /**
     * Tempurature getter (in celsius). Is serialized as temp_high_celsius
     * 
     * @return String: The tempurature of this period
     */
    @JsonProperty("temp_high_celsius")
    public String getTemperature() {
        return String.format("%.1f", this.temperature);
    }

    /**
     * ShortForecast getter. Is serialized as forecast_blurp
     * 
     * @return String: The forecast blurp of this period
     */
    @JsonProperty("forecast_blurp")
    public String getShortForecast() {
        return this.shortForecast;
    }
}
