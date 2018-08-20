package com.example.authserver.controller;

import com.example.authserver.service.TokenService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证controller
 * @author liuxy
 * @date 2018/8/10
 */
@RestController
@RequestMapping("/auth/")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 验证此token是否合法
     * @param token
     * @return
     */
    @RequestMapping("checkToken")
    public Boolean checkToken(@RequestParam("token") String token) throws Exception{

        logger.info("token is {}",token);

        boolean rightful = tokenService.checkToken(token);

        return rightful;
    }

    /**
     * 获取token
     * @return
     */
    @RequestMapping("getToken")
    public String getToken(@RequestParam String userId) throws Exception{

        logger.info("getToken");

        String token = tokenService.createToken(userId);

        return token;
    }
}
