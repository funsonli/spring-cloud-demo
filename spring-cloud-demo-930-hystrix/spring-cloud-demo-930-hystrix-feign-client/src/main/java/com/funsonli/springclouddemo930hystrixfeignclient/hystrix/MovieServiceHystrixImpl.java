package com.funsonli.springclouddemo930hystrixfeignclient.hystrix;

import com.funsonli.springclouddemo930hystrixfeignclient.service.MovieService;
import org.springframework.stereotype.Component;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/10/25
 */
@Component
public class MovieServiceHystrixImpl implements MovieService {
    @Override
    public String getBalance(String name) {
        return name + "服务器繁忙，请稍后再试";
    }
}
