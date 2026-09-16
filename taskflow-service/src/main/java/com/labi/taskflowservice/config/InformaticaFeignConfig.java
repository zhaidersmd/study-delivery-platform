package com.labi.taskflowservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class InformaticaFeignConfig {

    @Bean
    public RequestInterceptor informaticaAuthInterceptor(
            @Value("${informatica.username}") String username,
            @Value("${informatica.password}") String password ){


        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        return requestTemplate -> requestTemplate.header
                ("Authorization", "Basic " + encodedCredentials);
    }
}
