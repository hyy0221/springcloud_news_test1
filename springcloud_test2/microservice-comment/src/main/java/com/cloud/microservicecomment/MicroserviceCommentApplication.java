package com.cloud.microservicecomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCommentApplication.class, args);
    }

}
