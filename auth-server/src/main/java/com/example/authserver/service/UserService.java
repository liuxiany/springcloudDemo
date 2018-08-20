package com.example.authserver.service;

import com.example.authserver.entity.UserEntity;

/**
 * @author liuxy
 * @date 2018-08-20 15:23
 */
public interface UserService {

    UserEntity addUser(UserEntity userEntity) throws Exception;
}
