package com.Genpact.Weather;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;


@Service
public class WeatherService {
    private final String API_KEY = "65f410b9c657b95ffdf5af0d75902cf6";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    //private final RestTemplate restTemplate = new RestTemplate();
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherDetails.WeatherResponse fetchWeather(String lat, String lon) {
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", BASE_URL, lat, lon, API_KEY);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                WeatherDetails details = WeatherParser.parseWeather(response.getBody());
                return WeatherDetails.getTransformedResponse(details);
            } else {
                throw new RuntimeException("Failed to fetch weather data");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error fetching weather data", e);
        }
    }
}
