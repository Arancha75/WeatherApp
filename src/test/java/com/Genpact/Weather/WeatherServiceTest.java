package com.Genpact.Weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class WeatherServiceTest {
    private RestTemplate mockRestTemplate;
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        mockRestTemplate = mock(RestTemplate.class);
        weatherService = new WeatherService(mockRestTemplate);
    }

    @Test
    public void testFetchWeather_restClientException(){
        String  lat = "12.11";
        String  lon = "77.59";

        when(mockRestTemplate.getForEntity(anyString(),eq(String.class))).thenThrow(new RuntimeException("Error fetching weather data"));
        RuntimeException ex = assertThrows(RuntimeException.class,()->weatherService.fetchWeather(lat, lon));
        assertTrue(ex.getMessage().contains("Error fetching weather data"));
    }


    @Test
    public void testFetchWeather_downstreamDown(){
        String  lat = "12.11";
        String  lon = "77.59";
        when(mockRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(new ResponseEntity<>(null,HttpStatus.BAD_REQUEST));


       RuntimeException ex = assertThrows(RuntimeException.class,()->weatherService.fetchWeather(lat,lon));
       assertNotNull(ex.getMessage());
       //assertEquals("Failed to fetch weather data", ex.getMessage());
    }

    @Test
    public void  testFetchWeather_nullBody(){
        String lat = "12.54";
        String lon = "11.68";
        when(mockRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(new ResponseEntity<>(null,HttpStatus.OK));
        RuntimeException ex = assertThrows(RuntimeException.class,()->weatherService.fetchWeather(lat,lon));
        assertEquals("Failed to fetch weather data",ex.getMessage());
    }

    @Test
    public void FetchWeather_statusNotOk(){
        String lat ="12.97";
        String lon = "77.59";
        String fakeJson = "{\"weather\":[{\"description\":\"clear sky\"}],\"main\":{\"temp\":\"280.32\",\"feels_like\":\"281.15\",\"temp_min\":\"279.15\",\"temp_max\":\"282.15\",\"pressure\":1012,\"humidity\":60},\"wind\":{\"speed\":\"4.1\",\"deg\":80},\"sys\":{\"sunrise\":1633065600,\"sunset\":1633108800}}";
        ResponseEntity<String> fakeResponse = new ResponseEntity<>(fakeJson, HttpStatus.INTERNAL_SERVER_ERROR);
        when(mockRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(fakeResponse);
        RuntimeException ex = assertThrows(RuntimeException.class,()->weatherService.fetchWeather(lat,lon));
        assertEquals("Failed to fetch weather data",ex.getMessage());
    }



    @Test
    public void testFetchWeather_Success() {
        String fakeJson = """
                {
                  "weather": [{"description": "clear sky"}],
                  "main": {
                    "temp": 280.32,
                    "feels_like": 278.5,
                    "temp_min": 279.15,
                    "temp_max": 281.15,
                    "pressure": 1012,
                    "humidity": 56
                  },
                  "wind": {
                    "speed": "3.5",
                    "deg": 180
                  },
                  "sys": {
                    "sunrise": 1620450000,
                    "sunset": 1620496800
                  }
                }
                """;

        ResponseEntity<String> fakeResponse = new ResponseEntity<>(fakeJson, HttpStatus.OK);
        when(mockRestTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(fakeResponse);

        WeatherDetails.WeatherResponse result = weatherService.fetchWeather("12.34", "56.78");

        assertNotNull(result);

        assertEquals("clear sky", result.description);
        assertEquals("3.5", result.windSpeed);
        assertEquals("7.17", result.temp);


    }

    @Test
    void testFetchWeather_RestClientException() {
        String lat = "12.97";
        String lon = "77.59";

        when(mockRestTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RestClientException("Simulated API failure"));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                weatherService.fetchWeather(lat, lon));
        assertTrue(ex.getMessage().contains("Error fetching weather data"));
    }






}
