package com.aantoniow.service;

import com.aantoniow.client.WeatherClient;
import com.aantoniow.model.WeatherRequest;
import com.aantoniow.model.WeatherRequestV2;
import com.aantoniow.model.WeatherResponse;

public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getTemperatureAndCategorise(WeatherRequest request) {
        if (request.getCity() == null || request.getCity().isBlank()) {
            throw new WeatherServiceException("No valid city provided!");
        }
        String city = request.getCity();
        double temp = weatherClient.getCurrentTemperature(city);
        String category = categoriseTemp(temp);
        return new WeatherResponse(temp, category);
    }

    public WeatherResponse getTemperatureAndCategorise(WeatherRequestV2 request) {
        String city = request.getCityFromRequest();
        if (city == null || city.isBlank()) {
            throw new WeatherServiceException("No valid city provided!");
        }
        double temp = weatherClient.getCurrentTemperature(city);
        String category = categoriseTemp(temp);
        return new WeatherResponse(temp, category);
    }

    private String categoriseTemp(double temp) {
        if (temp <= 0)
            return "Freezing";
        if (temp <= 10)
            return "Cold";
        if (temp <= 20)
            return "Mild";
        if (temp <= 30)
            return "Warm";
        return "Hot";
    }

}