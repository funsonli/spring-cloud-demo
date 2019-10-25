package com.funsonli.springclouddemo950configclient.controller;

import com.funsonli.springclouddemo950configclient.config.properties.ZookeeperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Student Controller
 *
 * @author Funson
 * @date 2019/10/12
 */
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
