package com.chillingburnouts.smarthack;

import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
public class SmarthackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmarthackApplication.class, args);
	}

}
