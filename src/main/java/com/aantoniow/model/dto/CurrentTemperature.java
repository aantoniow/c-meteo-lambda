package com.aantoniow.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentTemperature {
    @JsonProperty("temperature_2m")
    double temperature;

    public double getTemperature() {
        return temperature;
    }

}