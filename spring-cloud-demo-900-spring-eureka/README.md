# Spring Cloud入门样例-900-Eureka服务注册和服务发现

> 每次都读取数据库非常慢，对于相同的查询，我们可以使用缓存。本demo演示如何使用guava和@CachePut 和 @Cacheable缓存数据。

### 前言

本Spring Cloud入门样例准备工作参考：

- [Spring Boot入门样例-001-Java和Maven安装配置](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-001-java.md)
- [Spring Boot入门样例-003-idea 安装配置和插件](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-003-idea.md)
- [Spring Boot入门样例-005-如何运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

相关功能
- 无登录功能的[Spring Boot入门样例-900-cloud整合Eureka](https://github.com/funsonli/spring-boot-demo/tree/master/spring-boot-demo-120-ehcache-cache)

## Eureka Server
### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

### 配置文件

resources/application.yml配置内容
```
server:
  port: 8079

eureka:
  instance:
    hostname: 127.0.0.1
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: eurka-server
  # 客户端basic模式登录帐号和密码
  security:
    user:
      name: admin
      password: 123456
```


### 代码解析


SpringBootCloud900SpringEurekaServerApplication.java 如下 增加注解@EnableEurekaServer 同时增加@EnableWebSecurity并在eureka目录禁用csrf
```
@SpringBootApplication
@EnableEurekaServer
public class SpringBootCloud900SpringEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCloud900SpringEurekaServerApplication.class, args);
    }

    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().ignoringAntMatchers("/eureka/**");
            super.configure(http);
        }
    }
}
```

## Eureka Client
Eureka client 客户就是一个普通的Spring Boot Web项目，在其中增加eureka-client和security依赖

### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

### 配置文件

resources/application.yml配置内容 路径中要Eureka服务器端的帐号和密码
```
spring:
  application:
    name: service-hello

eureka:
  client:
    serviceUrl:
      defaultZone: http://admin:123456@127.0.0.1:8079/eureka/ #路径中要Eureka服务器端的帐号和密码

```


### 代码解析


SpringBootCloud900SpringEurekaClientApplication.java 如下 增加注解@EnableEurekaServer 同时增加@EnableWebSecurity并在eureka目录禁用csrf
```
@SpringBootApplication
public class SpringBootCloud900SpringEurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCloud900SpringEurekaClientApplication.class, args);
    }

}

```

### 运行

点击[运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

先运行服务器

浏览器访问 http://localhost:8079/ 使用帐号和密码登录 显示如下

![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-900-eureka-01.png?raw=true)


再运行客户端

![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-900-eureka-03.png?raw=true)


### 参考
- Spring Cloud入门样例源代码地址 https://github.com/funsonli/spring-cloud-demo
- Bootan源代码地址 https://github.com/funsonli/bootan


### 附
如果您喜欢本Spring Cloud入门样例和样例代码，请[点赞Star](https://github.com/funsonli/spring-cloud-demo)

