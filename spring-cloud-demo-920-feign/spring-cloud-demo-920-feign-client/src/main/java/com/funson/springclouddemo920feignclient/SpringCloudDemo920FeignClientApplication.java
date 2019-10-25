package com.funson.springclouddemo920feignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudDemo920FeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo920FeignClientApplication.class, args);
    }

}
