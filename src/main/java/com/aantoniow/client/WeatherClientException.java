package com.aantoniow.client;

public class WeatherClientException extends RuntimeException {
    public WeatherClientException(String message) {
        super(message);
    }

    public WeatherClientException(String message, Exception cause) {
        super(message, cause);
    }

}