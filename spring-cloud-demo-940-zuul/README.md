# Spring Cloud入门样例-930-Hystrix断路器熔断服务

> 当服务器宕机或者访问过大，客户端需要直接返回服务器繁忙。本demo演示如何客户端使用hystrix进行熔断服务，不再继续访问服务器导致超时前端nginx返回502错误。

### 前言

本Spring Cloud入门样例准备工作参考：

- [Spring Boot入门样例-001-Java和Maven安装配置](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-001-java.md)
- [Spring Boot入门样例-003-idea 安装配置和插件](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-003-idea.md)
- [Spring Boot入门样例-005-如何运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

## Eureka Server
监控管理所有接入的服务器，本节Eureka使用spring-cloud-demo-910-ribbon中的spring-cloud-demo-eureka-server


## Feign Server
Feign Server 客户就是一个普通的Spring Boot Web项目，运行spring-cloud-demo-920-feign中的spring-cloud-demo-920-feign-server


## Feign Client
Feign client 客户就是一个普通的Spring Boot Web项目，在其中增加eureka-client依赖 运行spring-cloud-demo-930-feign-hystrix-feign-client项目

## Zuul Proxy
zuul proxy网关也是一个Spring Boot项目，进行路由转发

### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```

### 配置文件

resources/application.yml配置内容 path上的/movie 和 /user 和后端的路径没有关系
```
server:
  port: 8000

spring:
  application:
    name: zuul-proxy

eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8079/eureka/

zuul:
  routes:
    user:
      path: /user/**
      serviceId: ribbon-server
    movie:
      path: /movie/**
      serviceId: feign-client
```


### 代码解析


SpringCloudDemo940ZuulApplication.java 如下 加上@EnableZuulProxy @EnableEurekaClient 注解
```
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class SpringCloudDemo940ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo940ZuulApplication.class, args);
    }

}

```

### 运行

点击[运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

先运行Eureka服务器

浏览器访问 http://localhost:8079/ 

![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-900-eureka-01.png?raw=true)

启动spring-cloud-demo-920-feign-server作为服务端 可以访问 http://localhost:8086/balance?name=a 验证

运行spring-cloud-demo-930-hystrix-feign-client Feign Client项目作为另一个服务端 可以访问 http://localhost:8080/buy?name=funsonli 验证

启动spring-cloud-demo-940-zuul项目
```
浏览器访问 http://localhost:8000/movie/buy?name=funsonli
浏览器访问 http://localhost:8000/user/balance?name=funsonli
```
内容不一致表示他们来自不同的后端，路径上的/movie 和 /user 和后端的路径没有关系

## Ribbion Client
Ribbon client 客户就是一个普通的Spring Boot Web项目，在其中增加eureka-client依赖

### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```

### 配置文件

resources/application.yml配置内容 Hystrix需要在配置文件中配置启用
```
server:
  port: 8080

spring:
  application:
    name: ribbon-client

eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8079/eureka/
```


### 代码解析


MovieService.java 如下 ribbon-server为ribbon 服务器 yml中配置的名称。 服务不可用调用getBalanceFallback方法
```
@Service
public class MovieService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getBalanceFallback")
    public String getBalance(String name) {
        return restTemplate.getForObject("http://ribbon-server/balance?name=" + name, String.class);
    }

    private String getBalanceFallback(String name){
        return name + "服务器繁忙，请稍后再试";
    }
}

```


MovieController.java 如下 
```
@Slf4j
@RestController
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/buy")
    public String buy(@RequestParam String name) {
        String balance = movieService.getBalance(name);
        return balance + " I am client";
    }
}

```

运行和结果和Fiegn client一样

### 参考
- Spring Cloud入门样例源代码地址 https://github.com/funsonli/spring-cloud-demo
- Bootan源代码地址 https://github.com/funsonli/bootan


### 附
如果您喜欢本Spring Cloud入门样例和样例代码，请[点赞Star](https://github.com/funsonli/spring-cloud-demo)

