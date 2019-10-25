package com.funsonli.springclouddemo930hystrixribbonclient.controller;

import com.funsonli.springclouddemo930hystrixribbonclient.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/buy")
    public String buy(@RequestParam String name) {
        String balance = movieService.getBalance(name);
        return balance + " I am client";
    }
}
