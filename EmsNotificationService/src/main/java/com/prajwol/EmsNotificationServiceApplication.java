package com.prajwol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmsNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsNotificationServiceApplication.class, args);
	}

}
