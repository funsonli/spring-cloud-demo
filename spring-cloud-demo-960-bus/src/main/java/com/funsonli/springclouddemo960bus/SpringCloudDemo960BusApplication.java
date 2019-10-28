package com.funsonli.springclouddemo960bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope
public class SpringCloudDemo960BusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo960BusApplication.class, args);
    }

}
