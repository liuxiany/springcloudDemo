package com.example.eurekaconsumer.controller;

import com.example.eurekaconsumer.remote.HelloRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloRemote helloRemote;

    @RequestMapping("/helloWorld/{name}")
    public String index(@PathVariable("name") String name) {

        logger.info("enter consumer helloworld method");

        //测试zuul 路由重试
        /*try{
            Thread.sleep(1000000);
        }catch ( Exception e){
            logger.error(" hello two error",e);
        }*/

        return helloRemote.helloWorld(name);
    }
}
