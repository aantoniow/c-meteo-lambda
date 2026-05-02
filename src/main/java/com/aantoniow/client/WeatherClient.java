package com.aantoniow.client;

interface WeatherClient {
    double getCurrentTemperature(String city) throws Exception;
}