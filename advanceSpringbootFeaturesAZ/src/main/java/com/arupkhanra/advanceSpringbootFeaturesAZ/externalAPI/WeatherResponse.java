package com.arupkhanra.advanceSpringbootFeaturesAZ.externalAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Current {
        private int temperature;

        @JsonProperty("weather_descriptions")  // Properly map to JSON field
        private List<String> weatherDescriptions;

        @JsonProperty("feelslike")  // Properly map to JSON field
        private int feelsLike;

        @Override
        public String toString() {
            return "Current{temperature=" + temperature +
                    ", weatherDescriptions=" + weatherDescriptions +
                    ", feelsLike=" + feelsLike + "}";
        }
    }

    @Override
    public String toString() {
        return "WeatherResponse{current=" + current + "}";
    }
}