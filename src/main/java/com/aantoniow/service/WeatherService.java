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
        String city = request.getCity();
        validateCity(city);
        return process(city);
    }

    public WeatherResponse getTemperatureAndCategorise(WeatherRequestV2 request) {
        String city = request.getCityFromRequest();
        validateCity(city);
        return process(city);
    }

    private void validateCity(String city) {
        if (city == null || city.isBlank()) {
            throw new WeatherServiceException("No valid city provided!");
        }
    }

    private WeatherResponse process(String city) {
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