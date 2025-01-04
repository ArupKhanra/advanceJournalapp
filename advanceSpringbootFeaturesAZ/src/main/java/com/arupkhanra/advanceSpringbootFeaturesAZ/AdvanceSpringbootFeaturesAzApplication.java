package com.arupkhanra.advanceSpringbootFeaturesAZ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AdvanceSpringbootFeaturesAzApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvanceSpringbootFeaturesAzApplication.class, args);
	}

}

/*
		*  http://localhost:8081/swagger-ui/index.html   -- swagger url, check your API documentations
 */