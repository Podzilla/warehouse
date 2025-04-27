package com.podzilla.warehouse.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReverseGeoCodingUtil {

    @Value("${opencage.api.key}")
    private String apiKey;

    private final String GEOCODE_URL = "https://api.opencagedata.com/geocode/v1/json?q={lat},{lng}&key={key}";

    private final RestTemplate restTemplate;

    public ReverseGeoCodingUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String reverseGeocode(double latitude, double longitude) {
        String url = GEOCODE_URL;

        String response = restTemplate.getForObject(url, String.class, latitude, longitude, apiKey);

        return response;
    }
}
