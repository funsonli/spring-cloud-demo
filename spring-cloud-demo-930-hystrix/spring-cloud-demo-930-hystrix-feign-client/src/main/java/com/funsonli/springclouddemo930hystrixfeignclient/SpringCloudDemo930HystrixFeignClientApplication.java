package com.funsonli.springclouddemo930hystrixfeignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class SpringCloudDemo930HystrixFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo930HystrixFeignClientApplication.class, args);
    }

}
