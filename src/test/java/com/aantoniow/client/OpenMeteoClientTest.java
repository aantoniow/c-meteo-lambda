package com.aantoniow.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aantoniow.client.service.HttpService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class OpenMeteoClientTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Mock
    private HttpService httpService;
    @InjectMocks
    private OpenMeteoClient tested;

    private String geocodingValid = """
            {
              "results": [
                {
                  "id": 3081368,
                  "name": "Wroclaw",
                  "latitude": 51.1,
                  "longitude": 17.03333,
                  "elevation": 119.0,
                  "feature_code": "PPLA",
                  "country_code": "PL",
                  "admin1_id": 3337492,
                  "admin2_id": 7530801,
                  "admin3_id": 7531292,
                  "timezone": "Europe/Warsaw",
                  "population": 634893,
                  "country_id": 798544,
                  "country": "Poland",
                  "admin1": "Lower Silesia",
                  "admin2": "Wrocław",
                  "admin3": "Wrocław"
                }
              ],
              "generationtime_ms": 0.23186207
            }
                        """;

    private String geocodingInvalid = """
            {
              "generationtime_ms": 0.25713444
            }
                                        """;
    private String temperatureInvalid = """
            {
                "error": true,
                    "reason": "Longitude must be in range of -180 to 180°. Given: 1.012838e+13."
            }

            """;

    private String temperatureValid = """
            {
              "latitude": 51.1,
              "longitude": 17.039999,
              "generationtime_ms": 0.038743019104003906,
              "utc_offset_seconds": 0,
              "timezone": "GMT",
              "timezone_abbreviation": "GMT",
              "elevation": 118.0,
              "current_units": {
                "time": "iso8601",
                "interval": "seconds",
                "temperature_2m": "°C"
              },
              "current": {
                "time": "2026-05-03T11:30",
                "interval": 900,
                "temperature_2m": 25.8
              }
            }
                    """;

    @Test
    void shouldGetTemperatureFromValidCity() {
        // given
        String city = "Wroclaw";
        when(httpService.get(contains("geocoding"))).thenReturn(geocodingValid);
        when(httpService.get(contains("forecast"))).thenReturn(temperatureValid);

        // when
        var response = tested.getCurrentTemperature(city);

        // then
        assertEquals(25.8, response);
    }

}