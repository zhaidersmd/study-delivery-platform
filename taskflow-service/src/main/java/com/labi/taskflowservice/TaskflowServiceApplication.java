package com.labi.taskflowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TaskflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskflowServiceApplication.class, args);
	}

}
