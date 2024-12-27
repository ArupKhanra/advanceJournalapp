package com.arupkhanra.advanceSpringbootFeaturesAZ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableTransactionManagement
public class AdvanceSpringbootFeaturesAzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvanceSpringbootFeaturesAzApplication.class, args);
	}

}

/*
		*  http://localhost:8081/swagger-ui/index.html   -- url, check your API documentations
 */