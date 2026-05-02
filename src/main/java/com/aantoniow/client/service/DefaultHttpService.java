package com.aantoniow.client.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DefaultHttpService implements HttpService {

    private final HttpClient httpClient;

    public DefaultHttpService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpException("Geocoding failed: " + response.statusCode());
            }
            return response.body();

        } catch (IOException e) {
            throw new HttpException("Interrupted", e);
        } catch (InterruptedException e) {
            throw new HttpException("IO error", e);
        }
    }
}