package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
 class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void redisTest(){

      //  redisTemplate.opsForValue().set("email","arup.kj8@gmail.com");
        Object object = redisTemplate.opsForValue().get("username");
        int i =1;
    }
}
