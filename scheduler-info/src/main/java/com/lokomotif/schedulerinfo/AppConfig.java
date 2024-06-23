package com.lokomotif.schedulerinfo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EntityScan("com.lokomotif.schedulerinfo.model")
@ComponentScan(basePackages = "com.lokomotif.schedulerinfo")
public class AppConfig {

    @Bean(name= "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

