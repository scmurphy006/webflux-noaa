# webflux-noaa
Project using Spring's WebFlux framework to consume weather forecasts from NOAA's weather API.

To build, open your terminal of choice at root project folder and use the command "mvn clean install".

To demonstrate the consumption of NOAA weather API data there are two unit tests included. The first on will get the forecast data for the current data and print it to the console(for purposes of this demonstration only). The second unit test gets a five day forecast by hitting the same endpoint and streaming over the collected periods.

The data for a single forecast object is serialized as follows.
{
  "daily" : [ {
    "day_name" : "Overnight",
    "temp_high_celsius" : "19.4",
    "forecast_blurp" : "Mostly Cloudy"
  } ]
}