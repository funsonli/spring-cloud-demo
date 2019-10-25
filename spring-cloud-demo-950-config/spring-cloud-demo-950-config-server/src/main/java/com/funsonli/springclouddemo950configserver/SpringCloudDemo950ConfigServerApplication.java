package com.funsonli.springclouddemo950configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudDemo950ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo950ConfigServerApplication.class, args);
    }

}
