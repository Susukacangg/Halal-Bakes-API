package com.hbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HbapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbapiApplication.class, args);
	}

}
