# Spring Cloud入门样例-960-bus配置热加载Bus

> 从统一配置服务器上读取配置后，如果配置服务器再有更改需要重启其他的服务。本demo演示微服务中如何使用Bus通过rabbitmq主动推送配置到相关模块完成配置热加载。

### 前言

本Spring Cloud入门样例准备工作参考：

- [Spring Boot入门样例-001-Java和Maven安装配置](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-001-java.md)
- [Spring Boot入门样例-003-idea 安装配置和插件](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-003-idea.md)
- [Spring Boot入门样例-005-如何运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

## Eureka Server
监控管理所有接入的服务器，本节Eureka使用spring-cloud-demo-910-ribbon中的spring-cloud-demo-eureka-server


## Config Server
Config proxy网关也是一个Spring Boot项目，进行路由转发

### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
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
```

### 配置文件

resources/application.yml配置内容 path上的/movie 和 /user 和后端的路径没有关系
```
server:
  port: 8888

spring:
  application:
    name: config-server
  cloud:
    config:
      label: maseter
      server:
        git:
          uri: https://github.com/funsonli/spring-cloud-demo-config/
          search-paths:
          username:
          password:

eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8079/eureka/

```


### 代码解析


SpringCloudDemo950ConfigServerApplication.java 如下 加上@EnableConfigServer注解
```
@SpringBootApplication
@EnableConfigServer
public class SpringCloudDemo950ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemo950ConfigServerApplication.class, args);
    }

}


```

### 运行

点击[运行](https://github.com/funsonli/spring-boot-demo/blob/master/doc/spring-boot-demo-005-run.md)

运行eureka服务器

运行Config Server服务器

浏览器访问 http://localhost:8888/zookeeper/dev

```
{"name":"zookeeper","profiles":["dev"],"label":null,"version":"6027b01541855679317040e9d6d28a9d4869b58b","state":null,"propertySources":[{"name":"https://github.com/funsonli/spring-cloud-demo-config//application.yml","source":{"zookeeper.url":"127.0.0.1:2181","zookeeper.timeout":1000,"zookeeper.retry":3}}]}
```

### 相关说明

/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties

其中，{application} 被用于 spring 配置的配置文件名称，在 spring boot 中通常默认为 application；{profile} 表示激活的配置文件，通常用于区分开发/测试/生产环境；{label} 则表示 git 的分支，默认为 master。


## Config Client
Config client 客户就是一个普通的Spring Boot Web项目，读取config server中的配置

### pox.xml
必要的依赖如下，具体参见该项目的pox.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

### 配置文件

resources/application.yml配置内容 需要制定uri路径为配置服务器的url路径
```
server:
  port: 8077

spring:
  profiles:
    active: dev
  application:
    name: config-client
  cloud:
    config:
      label: master
      profile: dev
      uri: http://localhost:8888/

eureka:
  client:
    serviceUrl:
      defaultZone: http://127.0.0.1:8079/eureka/
```


### 代码解析


ZookeeperProperties.java 如下 实际通过Config server读取github上的配置
```
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private String url;
    private Integer timeout;
    private Integer retry;
}

```


StudentController.java 如下 
```
@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    ZookeeperProperties zookeeperProperties;

    @GetMapping("/zookeeper")
    public String get(HttpServletRequest request) {
        return zookeeperProperties.getUrl();
    }
}

```

### 运行

运行eureka服务器

运行Config Server服务器

运行Config Client服务器

``` 
浏览器访问 http://localhost:8077/student/zookeeper

127.0.0.1:2181
```

该项目的yml配置文件中没有任何zookeeper配置。实际通过Config server读取github上的配置







### 参考
- Spring Cloud入门样例源代码地址 https://github.com/funsonli/spring-cloud-demo
- Bootan源代码地址 https://github.com/funsonli/bootan


### 附
如果您喜欢本Spring Cloud入门样例和样例代码，请[点赞Star](https://github.com/funsonli/spring-cloud-demo)

