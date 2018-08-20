package com.example.springcloudzuul.feign;

import com.example.springcloudzuul.configuration.FeiginConfig;
import com.example.springcloudzuul.feign.hystrix.AuthServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liuxy
 * @date 2018-08-17 14:07
 */
@Service
@FeignClient(name = "auth-server", fallback = AuthServiceHystrix.class,configuration = FeiginConfig.class)
public interface AuthService {

    /**
     * 获取token
     * @param token
     * @return Boolean
     */
    @RequestMapping("auth/checkToken")
    public Boolean checkToken(@RequestParam("token") String token);
}
