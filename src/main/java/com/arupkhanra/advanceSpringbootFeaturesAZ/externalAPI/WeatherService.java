package com.arupkhanra.advanceSpringbootFeaturesAZ.externalAPI;


import com.arupkhanra.advanceSpringbootFeaturesAZ.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    private static final String API = "http://api.weatherstack.com/current";

    public WeatherResponse getWeather(String city) {
        String apiKey = System.getenv("WEATHER_API_KEY");

        if (city == null || city.isEmpty()) {
            logger.error("City name is null or empty.");
            return null;
        }

        // Build API URL
        String url = UriComponentsBuilder.fromHttpUrl(API)
                .queryParam("access_key", apiKey)
                .queryParam("query", city)
                .toUriString();

        logger.info("Weather API URL: {}", url);

        // Fetch from Redis cache first
        WeatherResponse cachedWeather = redisService.get("Weather_of_" + city, WeatherResponse.class);
        if (cachedWeather != null) {
            return cachedWeather;
        }

        // If not found in Redis, make the API call
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse weatherResponse = response.getBody();

        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            logger.info("Weather API Response: {}", weatherResponse);

            // Store the weather data in Redis for future use
            redisService.set("Weather_of_" + city, weatherResponse, 10000L);  // Set TTL to 5 minutes
            return weatherResponse;
        } else {
            logger.error("Error: Weather data is empty or malformed.");
            return null;
        }
    }
}

//@Service
//public class WeatherService {
//
//    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private RedisService redisService;
//
////    @Autowired
////    private AppCache appCache;
//    private static final String API = "http://api.weatherstack.com/current";
//
//    public WeatherResponse getWeather(String city) {
//        String apiKey = System.getenv("WEATHER_API_KEY");
//        // Validate city parameter
//        if (city == null || city.isEmpty()) {
//            logger.error("City name is null or empty.");
//            return null;
//        }
//
//        // Construct the URL with the query parameters
//        String url = UriComponentsBuilder.fromHttpUrl(API)
//                .queryParam("access_key", apiKey)
//                .queryParam("query", city)
//                .toUriString();
//
//        // Log the final URL to check
//        logger.info("Weather API URL: {}", url);
//
//        // Make the API call and log the response
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
//
//        WeatherResponse weatherResponseCase = redisService.get("Weather_of_"+city, WeatherResponse.class);
//        if(weatherResponseCase != null){
//            return weatherResponseCase;
//
//        }else{
//
//            WeatherResponse weatherResponse = response.getBody();
//            if (weatherResponse != null && weatherResponse.getCurrent() != null) {
//                logger.info("Weather API Response: {}", weatherResponse);
//            } else {
//                logger.error("Error: Weather data is empty or malformed.");
//                return null;
//            }
//            if(weatherResponse!= null){
//                redisService.set("Weather_of_"+city,weatherResponse,300l);
//            }
//            return weatherResponse;
//        }
//        // Process and log the response body
//
//
//
//    }
//
//}




  //  public WeatherResponse getWeather(String city){
//            String apiKey = System.getenv("WEATHER_API_KEY");
//            String finaApi = appCache.appCache.get(AppCache.keys.Weather_API.toString())
//                    .replace(PlaceHolder.CITY,city).replace(PlaceHolder.API_KEY,apiKey);
//            ResponseEntity<WeatherResponse> response =
//                    restTemplate.exchange(finaApi, HttpMethod.GET, null, WeatherResponse.class);
//            return response.getBody();
//        }