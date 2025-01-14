package com.arupkhanra.advanceSpringbootFeaturesAZ.service;


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
    private RedisTemplate redisTemplate;

    public <T>T get(String key, Class<T> entryClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o != null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(o.toString(), entryClass);
            } else {
                log.info("No value found for key: " + key);
                return null;
            }
        } catch (Exception e) {
            log.info("exception redis Service class " + e.getMessage());
            return null;
        }
    }
// ttl meaning Time To Limit
    public void set(String key, Object o,Long ttl) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o);
             redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.MINUTES);

        } catch (Exception e) {

            log.info("exception redis Service class "+e.getMessage());
        }
    }
}
