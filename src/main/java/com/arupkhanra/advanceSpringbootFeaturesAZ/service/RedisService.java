package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Retrieves an object from Redis.
     *
     * @param key        the key for the object to be fetched from Redis.
     * @param entryClass the class type of the object to be returned.
     * @param <T>        the type of the object.
     * @return the object of type T, or null if the key doesn't exist or there's an error.
     */
    public <T> T get(String key, Class<T> entryClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null) {
                log.info("Retrieved key: {}, value: {}", key, value);
                return objectMapper.readValue(value.toString(), entryClass);
            } else {
                log.warn("No value found for key: {}", key);
                return null;
            }
        } catch (JsonProcessingException e) {
            log.error("Error while deserializing data from Redis for key: {}. Exception: {}", key, e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error("Unexpected error while fetching from Redis for key: {}. Exception: {}", key, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Stores an object in Redis with a specified TTL (Time-to-Live).
     *
     * @param key the key for the object to be stored in Redis.
     * @param value the object to be stored.
     * @param ttl the time-to-live (TTL) in minutes for the object in Redis.
     */
    public void set(String key, Object value, Long ttl) {
        try {
            // Serialize the object to a JSON string
            String jsonValue = objectMapper.writeValueAsString(value);

            log.info("Setting key: {}, value: {}", key, jsonValue);

            // Save the JSON string in Redis with TTL
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.MINUTES);

            log.info("Key: {} successfully set in Redis with TTL of {} minutes", key, ttl);
        } catch (JsonProcessingException e) {
            log.error("Error while serializing the object to JSON for key: {}. Exception: {}", key, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while setting data in Redis for key: {}. Exception: {}", key, e.getMessage(), e);
        }
    }
}
