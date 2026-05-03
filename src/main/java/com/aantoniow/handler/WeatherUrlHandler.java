package com.aantoniow.handler;

import com.aantoniow.client.OpenMeteoClient;
import com.aantoniow.client.WeatherClient;
import com.aantoniow.client.service.DefaultHttpService;
import com.aantoniow.client.service.HttpService;
import com.aantoniow.config.AppConfig;
import com.aantoniow.model.WeatherRequestV2;
import com.aantoniow.model.WeatherResponse;
import com.aantoniow.service.WeatherService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class WeatherUrlHandler implements RequestHandler<WeatherRequestV2, WeatherResponse> {

    private final WeatherService weatherService;

    public WeatherUrlHandler() {
        AppConfig config = new AppConfig();
        HttpService httpService = new DefaultHttpService(config.getHttpClient());
        WeatherClient weatherClient = new OpenMeteoClient(httpService, config.getObjectMapper());
        this.weatherService = new WeatherService(weatherClient);
    }

    @Override
    public WeatherResponse handleRequest(WeatherRequestV2 request, Context context) {
        return weatherService.getTemperatureAndCategorise(request);
    }

}
