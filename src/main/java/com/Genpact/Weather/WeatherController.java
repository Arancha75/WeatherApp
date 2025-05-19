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

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/current")
    public ResponseEntity<String> getCurrentWeather(@RequestParam double lat, @RequestParam double lon) {
        String weatherData = weatherService.fetchWeather(lat, lon);
            return ResponseEntity.ok(weatherData);
    }
}
