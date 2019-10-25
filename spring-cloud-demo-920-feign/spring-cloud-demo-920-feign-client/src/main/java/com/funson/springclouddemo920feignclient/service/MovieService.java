package com.funson.springclouddemo920feignclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/10/25
 */
@Service
@FeignClient(value = "ribbon-server")
public interface MovieService {

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    String getBalance(@RequestParam(value = "name") String name);
}
