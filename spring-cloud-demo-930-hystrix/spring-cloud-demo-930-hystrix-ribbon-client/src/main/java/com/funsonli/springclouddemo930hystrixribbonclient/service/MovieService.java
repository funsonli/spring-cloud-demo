package com.funsonli.springclouddemo930hystrixribbonclient.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/10/25
 */
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
