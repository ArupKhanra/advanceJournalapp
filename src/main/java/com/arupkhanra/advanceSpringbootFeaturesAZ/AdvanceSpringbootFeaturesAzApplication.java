package com.arupkhanra.advanceSpringbootFeaturesAZ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class AdvanceSpringbootFeaturesAzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvanceSpringbootFeaturesAzApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}

/*
		*  http://localhost:8081/swagger-ui/index.html   -- swagger url, check your API documentations
 */