package com.Genpact.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final LocationService locationService;

    private final WeatherService weatherService;

    public WeatherController(LocationService locationService, WeatherService weatherService) {
        this.locationService = locationService;
        this.weatherService = weatherService;
    }

    @GetMapping()
    public ResponseEntity<WeatherDetails.WeatherResponse> getCurrentWeather(@RequestParam String cityName) {
        // 1. Fetch lat long for the given city
        CityDetails cityDetails = locationService.fetchCityDetails(cityName);
        String lat = cityDetails.getLatitude();
        String lon = cityDetails.getLongitude();
        // 2. Fetch the weather details using lat long
        WeatherDetails.WeatherResponse weatherData = weatherService.fetchWeather(lat, lon);

        weatherData.name = cityName;
        return ResponseEntity.ok(weatherData);
    }
}
