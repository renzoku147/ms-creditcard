package com.java.everis.mscreditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsCreditcardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditcardApplication.class, args);
	}

}
