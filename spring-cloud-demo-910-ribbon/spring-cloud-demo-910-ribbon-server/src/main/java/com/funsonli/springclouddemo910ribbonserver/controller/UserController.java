package com.funsonli.springclouddemo910ribbonserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for
 *
 * @author Funson
 * @date 2019/10/17
 */
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
