package com.funsonli.springclouddemo910ribbonclient.service;

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

    public String getBalance(String name) {
        return restTemplate.getForObject("http://ribbon-server/balance?name=" + name, String.class);
    }
}
