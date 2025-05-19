package com.Genpact.Weather;

import com.fasterxml.jackson.databind.ObjectMapper;


public class WeatherParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static WeatherDetails parseWeather(String json) {
        try {
            return mapper.readValue(json, WeatherDetails.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse weather JSON", e);
        }
    }
}