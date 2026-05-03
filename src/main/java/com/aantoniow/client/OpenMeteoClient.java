package com.aantoniow.client;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.aantoniow.client.service.HttpService;
import com.aantoniow.model.dto.Coordinates;
import com.aantoniow.model.dto.GeocodingResponse;
import com.aantoniow.model.dto.GeocodingResults;
import com.aantoniow.model.dto.OpenMeteoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenMeteoClient implements WeatherClient {
    private final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast?current=temperature_2m&latitude=%s&longitude=%s";
    private final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&format=json";

    private final HttpService httpService;
    private final ObjectMapper objectMapper;

    public OpenMeteoClient(HttpService httpService, ObjectMapper objectMapper) {
        this.httpService = httpService;
        this.objectMapper = objectMapper;
    }

    @Override
    public double getCurrentTemperature(String city) {
        Coordinates coords = getCoordinates(city);
        return getTemperature(coords);
    }

    private Coordinates getCoordinates(String city) {
        String url = String.format(
                GEOCODING_URL,
                URLEncoder.encode(city, StandardCharsets.UTF_8));
        try {
            GeocodingResponse formattedResponseList = objectMapper.readValue(httpService.get(url),
                    GeocodingResponse.class);
            if (formattedResponseList.getResults() == null || formattedResponseList.getResults().isEmpty()) {
                throw new WeatherClientException("City not found");
            }
            GeocodingResults result = formattedResponseList.getResults().get(0);
            return new Coordinates(result.getLatitude(), result.getLongitude());

        } catch (JsonMappingException e) {
            throw new WeatherClientException("City parsing problem", e);
        } catch (JsonProcessingException e) {
            throw new WeatherClientException("City parsing problem", e);
        } catch (Exception e) {
            throw new WeatherClientException("Failed to call API", e);
        }
    }

    private double getTemperature(Coordinates coords) {
        String url = String.format(
                Locale.US,
                FORECAST_URL,
                coords.latitude(),
                coords.longitude());

        try {
            OpenMeteoResponse meteo = objectMapper.readValue(httpService.get(url), OpenMeteoResponse.class);
            if (meteo.getCurrent() == null) {
                throw new WeatherClientException("Temperature not found");
            }

            return meteo.getCurrent().getTemperature();
        } catch (JsonMappingException e) {
            throw new WeatherClientException("Temperature parsing problem", e);
        } catch (JsonProcessingException e) {
            throw new WeatherClientException("Temperature parsing problem", e);
        } catch (Exception e) {
            throw new WeatherClientException("Failed to call API", e);
        }
    }

}