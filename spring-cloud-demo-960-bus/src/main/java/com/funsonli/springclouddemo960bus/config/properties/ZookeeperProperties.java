package com.funsonli.springclouddemo960bus.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class for
 *
 * @author Funson
 * @date 2019/10/17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private String url;
    private Integer timeout;
    private Integer retry;
}
