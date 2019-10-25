# Spring Cloud入门样例-910-Ribbon负载均衡

> 有多个服务器时，客户端随机访问服务器。本demo演示如何使用Ribbon进行负载均衡。

### 前言

本Spring Cloud入门样例准备工作参考：

- [Spring Boot入门样例-001-Java和Maven安装配置](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-001-java.md)
- [Spring Boot入门样例-003-idea 安装配置和插件](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-003-idea.md)
- [Spring Boot入门样例-005-如何运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

## Eureka Server
### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
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
}
```

## Ribbon Server
Ribbon Server 客户就是一个普通的Spring Boot Web项目，在其中增加eureka-client依赖

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
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### 配置文件

resources/application.yml配置内容 指定为8086端口，同时想8079端口的eureka服务器注册
```
server:
  port: 8086

spring:
  application:
    name: ribbon-server

eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8079/eureka/
```


### 代码解析

UserController.java 如下 用户余额，正常查询数据库返回用户余额
```

@Slf4j
@RestController
public class UserController {

    @Value("${server.port}")
    String port;

    @GetMapping("/balance")
    public String balance(@RequestParam String name) {
        if ("funsonli".equals(name)) {
            return "balance is 30 from port" +  port;
        }

        return "balance is 0 from port" +  port;
    }
}

```

## Ribbon Client
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
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

```

### 配置文件

resources/application.yml配置内容 路径中要Eureka服务器端的帐号和密码
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


MovieService.java 如下 ribbon-server为ribbon 服务器 yml中配置的名称
```
@Service
public class MovieService {

    @Autowired
    RestTemplate restTemplate;

    public String getBalance(String name) {
        return restTemplate.getForObject("http://ribbon-server/balance?name=" + name, String.class);
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
        return balance;
    }
}

```

### 运行

点击[运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

先运行Eureka服务器

浏览器访问 http://localhost:8079/ 

![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-900-eureka-01.png?raw=true)

编译运行多台ribbon 服务器， 在右侧的Maven中编译项目

![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-910-ribbon-00.png?raw=true)

```
D:\project\spring-cloud-demo\spring-cloud-demo-910-ribbon\spring-cloud-demo-910-ribbon-server\target>
java -jar spring-cloud-demo-910-ribbon-server-0.0.1-SNAPSHOT.jar --server.port=8086

D:\project\spring-cloud-demo\spring-cloud-demo-910-ribbon\spring-cloud-demo-910-ribbon-server\target>
java -jar spring-cloud-demo-910-ribbon-server-0.0.1-SNAPSHOT.jar --server.port=8087
```

运行Ribbon Client项目

浏览器访问 http://localhost:8079/ 
![图片](https://raw.githubusercontent.com/funsonli/spring-cloud-demo/master/doc/images/spring-cloud-demo-910-ribbon-01.png?raw=true)


浏览器访问 http://localhost:8080/buy?name=funsonli

刷新该页面，看看Client项目随机从两个Server获取数据


### 参考
- Spring Cloud入门样例源代码地址 https://github.com/funsonli/spring-cloud-demo
- Bootan源代码地址 https://github.com/funsonli/bootan


### 附
如果您喜欢本Spring Cloud入门样例和样例代码，请[点赞Star](https://github.com/funsonli/spring-cloud-demo)

