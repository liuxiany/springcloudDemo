package com.example.authserver.service.impl;

import com.example.authserver.dao.UserEntityJpa;
import com.example.authserver.entity.UserEntity;
import com.example.authserver.service.TokenService;
import com.example.authserver.service.UserService;
import com.example.authserver.util.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author liuxy
 * @date 2018-08-20 15:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityJpa userEntityJpa;

    @Autowired
    private TokenService tokenService;

    @Value("${authSecret}")
    private String authSecret;

    @Override
    public UserEntity addUser(UserEntity userEntity) throws Exception{

        userEntity.setId(UUIDUtil.generateShortUuid(20));
        UserEntity user = (UserEntity) userEntityJpa.save(userEntity);

        //生成token
        tokenService.createToken(user.getId());

        return user;
    }
}
