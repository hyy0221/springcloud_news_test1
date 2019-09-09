package com.cloud.microservicearticle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceArticleApplication.class, args);
    }

}
