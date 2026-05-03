package com.aantoniow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aantoniow.client.WeatherClient;
import com.aantoniow.model.WeatherRequest;
import com.aantoniow.model.WeatherResponse;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    private WeatherClient weatherClient;

    @InjectMocks
    private WeatherService tested;

    @ParameterizedTest
    @CsvSource({ "22.1,Warm", "-1.5, Freezing", "39.0, Hot", "15.1, Mild", "3.55, Cold" })
    void shouldReturnValidResponseWhenValidCity(double temperature, String category) {
        // given
        String city = "Wrocław";
        WeatherRequest validRequest = new WeatherRequest(city);
        when(weatherClient.getCurrentTemperature(city)).thenReturn(temperature);

        // when
        var response = tested.getTemperatureAndCategorise(validRequest);

        // then
        WeatherResponse validResponse = new WeatherResponse(temperature, category);
        assertEquals(validResponse.getTemperature(), response.getTemperature());
        assertEquals(validResponse.getCategory(), response.getCategory());
    }

    @Test
    void shouldThrowWhenInvalidRequest() {
        // given
        String city = "";
        WeatherRequest invalidRequest = new WeatherRequest(city);
        // when / then
        assertThrowsExactly(WeatherServiceException.class, () -> tested.getTemperatureAndCategorise(invalidRequest));
    }

}