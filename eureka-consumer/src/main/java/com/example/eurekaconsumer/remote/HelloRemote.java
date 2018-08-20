package com.example.eurekaconsumer.remote;

import com.example.eurekaconsumer.config.FeiginConfig;
import com.example.eurekaconsumer.remote.hystrix.HelloRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "springcloud-zuul", fallback = HelloRemoteHystrix.class,configuration = FeiginConfig.class)
@Component
//@RequestMapping("hello")
//加入熔断器功能后不能在类上使用requestMapping注解
public interface HelloRemote {

    @RequestMapping(value = "eureka-provider/hello/helloWorld")
    public String helloWorld(@RequestParam(value = "name") String name);

}
