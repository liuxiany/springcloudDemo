package com.example.eurekaconsumer.remote.hystrix;

import com.example.eurekaconsumer.remote.HelloRemote;
import org.springframework.stereotype.Component;

@Component
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String helloWorld(String name) {
        return "hello:" + name + ",remote failed";
    }
}
