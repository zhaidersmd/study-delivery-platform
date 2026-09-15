package com.labi.taskflowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskflowServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskflowServiceApplication.class, args);
	}

}
