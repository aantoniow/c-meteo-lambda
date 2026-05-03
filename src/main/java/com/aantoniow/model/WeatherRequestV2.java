package com.aantoniow.model;

import java.util.Map;

public class WeatherRequestV2 {

    private Map<String, String> queryStringParameters;

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public String getCityFromRequest() {
        return queryStringParameters.get("city");
    }

}