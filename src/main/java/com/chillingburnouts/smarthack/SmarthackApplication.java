package com.chillingburnouts.smarthack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmarthackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmarthackApplication.class, args);
	}

}
