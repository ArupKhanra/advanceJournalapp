package com.arupkhanra.advanceSpringbootFeaturesAZ.externalAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private RestTemplate restTemplate;

    // Externalized configuration for API key and endpoint
//    @Value("${weather.api.key}")
    private static final String apiKey = "8feb25615999ef5bf9265d360179d9ab";

    private static final String API = "http://api.weatherstack.com/current";

    public WeatherResponse getWeather(String city) {
        // Validate city parameter
        if (city == null || city.isEmpty()) {
            logger.error("City name is null or empty.");
            return null;
        }

        // Construct the URL with the query parameters
        String url = UriComponentsBuilder.fromHttpUrl(API)
                .queryParam("access_key", apiKey)
                .queryParam("query", city)
                .toUriString();

        // Log the final URL to check
        logger.info("Weather API URL: {}", url);

        // Make the API call and log the response
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);

        // Process and log the response body
        WeatherResponse weatherResponse = response.getBody();
        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            logger.info("Weather API Response: {}", weatherResponse);
        } else {
            logger.error("Error: Weather data is empty or malformed.");
            return null;
        }

        return weatherResponse;
    }
}
