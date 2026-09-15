package com.labi.studyjobservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudyJobServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyJobServiceApplication.class, args);


    }

}
