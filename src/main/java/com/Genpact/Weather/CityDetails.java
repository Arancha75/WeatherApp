package com.Genpact.Weather;



public class CityDetails {

    public String latitude;

    public String longitude;

    public CityDetails() {

    }

    public CityDetails(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


}
