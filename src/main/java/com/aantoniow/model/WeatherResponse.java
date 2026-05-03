package com.aantoniow.model;

public class WeatherResponse {
    double temperature;
    String category;

    public WeatherResponse(double temperature, String category) {
        this.temperature = temperature;
        this.category = category;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCategory() {
        return category;
    }

}