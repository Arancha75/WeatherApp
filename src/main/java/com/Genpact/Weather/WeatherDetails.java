package com.Genpact.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetails {

    @JsonProperty("name")
    public String cityName;

    @JsonProperty("main")
    public MainData main;

    @JsonProperty("wind")
    public WindData wind;

    @JsonProperty("weather")
    public WeatherData[] weather;

    @JsonProperty("sys")
    public SysData sys;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainData {
        @JsonProperty("temp")
        public String temp;
        @JsonProperty("feels_like")
        public String feelsLike;
        @JsonProperty("temp_min")
        public String tempMin;
        @JsonProperty("temp_max")
        public String tempMax;
        @JsonProperty("pressure")
        public int pressure;
        @JsonProperty("humidity")
        public int humidity;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindData {
        @JsonProperty("speed")
        public String speed;
        @JsonProperty("deg")
        public int deg;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherData {
        @JsonProperty("id")
        public int id;
        @JsonProperty("main")
        public String main;
        @JsonProperty("description")
        public String description;
        @JsonProperty("icon")
        public String icon;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SysData {
        @JsonProperty("type")
        public int type;
        @JsonProperty("id")
        public int id;
        @JsonProperty("country")
        public String country;
        @JsonProperty("sunrise")
        public long sunrise;
        @JsonProperty("sunset")
        public long sunset;
    }

    public String getDescription() {
        return weather != null && weather.length > 0 ? weather[0].description : "";
    }

    public String getTemp() {
        return main.temp;
    }

    public String getFeelsLike() {
        return main.feelsLike;
    }

    public String getTempMin() {
        return main.tempMin;
    }

    public String getTempMax() {
        return main.tempMax;
    }

    public String getWindSpeed() {
        return wind.speed;
    }

    public String getSunriseDate() {
        return convertTimestampToISO8601(sys.sunrise);
    }

    public String getSunsetDate() {
        return convertTimestampToISO8601(sys.sunset);
    }

    private String convertTimestampToISO8601(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(timestamp * 1000));
    }

    // Method to get the transformed response
    public static WeatherResponse getTransformedResponse(WeatherDetails  details) {
        WeatherResponse response = new WeatherResponse();
        response.sunriseDate = details.getSunriseDate();
        response.sunsetDate = details.getSunsetDate();
        response.temp = details.getTemp();
        response.feelsLike = details.getFeelsLike();
        response.tempMin = details.getTempMin();
        response.tempMax = details.getTempMax();
        response.windSpeed = details.getWindSpeed();
        response.description = details.getDescription();

        return response;
    }

    // Class to represent the transformed response
    public static class WeatherResponse {
        public String sunriseDate;
        public String sunsetDate;
        public String temp;
        public String feelsLike;
        public String tempMin;
        public String tempMax;
        public String windSpeed;
        public String description;
        public String name;
    }
}
