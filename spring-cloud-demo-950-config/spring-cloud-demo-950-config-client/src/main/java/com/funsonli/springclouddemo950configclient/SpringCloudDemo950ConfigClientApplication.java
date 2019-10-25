package com.funsonli.springclouddemo950configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudDemo950ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo950ConfigClientApplication.class, args);
    }
}
