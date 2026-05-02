package com.aantoniow.client;

public interface WeatherClient {
    Double getCurrentTemperature(String city) throws Exception;
}