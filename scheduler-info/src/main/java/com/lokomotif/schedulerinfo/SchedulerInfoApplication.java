package com.lokomotif.schedulerinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulerInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerInfoApplication.class, args);
	}

}
