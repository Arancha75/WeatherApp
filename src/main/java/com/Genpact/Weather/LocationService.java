package com.Genpact.Weather;

import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class LocationService {

    public CityDetails fetchCityDetails(String cityName) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + cityName;

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "Weather-App)") // REQUIRED by Nominatim policy
                .build();

        CityDetails cityDetails = new CityDetails();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();
                JSONArray results = new JSONArray(jsonData);
                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0);
                    String lat = location.getString("lat");
                    String lon = location.getString("lon");
                    cityDetails = new CityDetails(lat, lon);
                    System.out.println("Latitude: " + lat + ", Longitude: " + lon);
                } else {
                    System.out.println("City not found.");

                }
                return cityDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityDetails;
    }
}
