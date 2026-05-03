package com.aantoniow.model;

public class WeatherRequest {
    String city;

    public WeatherRequest(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

}