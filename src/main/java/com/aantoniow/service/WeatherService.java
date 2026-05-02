package com.aantoniow.service;

import com.aantoniow.client.WeatherClient;

public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public void costam(String city) {
        try {
            Double temp = weatherClient.getCurrentTemperature(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}