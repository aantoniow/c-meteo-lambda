package com.aantoniow.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import tools.jackson.databind.ObjectMapper;

class OpenMeteoClient implements WeatherClient {
    private final String openMeteoUrl = "https://api.open-meteo.com/v1/forecast?current=temperature_2m&latitude=51.1&longitude=17.0333";
    private final String geoCodingUrl = "https://geocoding-api.open-meteo.com/v1/search?name={city}&count=1&format=json";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenMeteoClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public double getCurrentTemperature(String city) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    private Coordinates getCoordinates() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(geoCodingUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Coordinates("test", "dupa");
    }

    private record Coordinates(String longitude, String latitude) {
    };

}