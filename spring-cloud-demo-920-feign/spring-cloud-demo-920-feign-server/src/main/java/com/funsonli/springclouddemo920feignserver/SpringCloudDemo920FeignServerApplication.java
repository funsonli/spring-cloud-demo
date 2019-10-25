package com.funsonli.springclouddemo920feignserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudDemo920FeignServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo920FeignServerApplication.class, args);
    }

}
