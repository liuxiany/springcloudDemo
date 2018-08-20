package com.example.springcloudzuul.feign.hystrix;

import com.example.springcloudzuul.feign.AuthService;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthService {
    @Override
    public Boolean checkToken(String token) {
        return false;
    }
}
