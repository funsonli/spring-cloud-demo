package com.funsonli.springbootcloud900springeurekaclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for
 *
 * @author Funson
 * @date 2019/10/17
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
