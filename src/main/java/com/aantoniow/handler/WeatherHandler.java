package com.aantoniow.handler;

import com.aantoniow.client.OpenMeteoClient;
import com.aantoniow.client.WeatherClient;
import com.aantoniow.client.service.DefaultHttpService;
import com.aantoniow.client.service.HttpService;
import com.aantoniow.config.AppConfig;
import com.aantoniow.service.WeatherService;

public class WeatherHandler {

    private final WeatherService weatherService;

    public WeatherHandler() {
        AppConfig config = new AppConfig();
        HttpService httpService = new DefaultHttpService(config.getHttpClient());
        WeatherClient weatherClient = new OpenMeteoClient(httpService, config.getObjectMapper());
        this.weatherService = new WeatherService(weatherClient);
    }
}