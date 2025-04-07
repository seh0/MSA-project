package com.example.anotherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AnotherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotherServiceApplication.class, args);
	}

}
