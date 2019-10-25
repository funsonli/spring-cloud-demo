package com.funsonli.springclouddemo930hystrixfeignclient.service;

import com.funsonli.springclouddemo930hystrixfeignclient.hystric.MovieServiceHystricImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/10/25
 */
@FeignClient(value = "ribbon-server", fallback = MovieServiceHystricImpl.class)
public interface MovieService {

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    String getBalance(@RequestParam(value = "name") String name);
}
