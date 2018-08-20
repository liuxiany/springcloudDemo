package com.example.authserver.dao;

import com.example.authserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuxy
 * @date 2018-08-17 16:43
 */
public interface UserEntityJpa<UserEntityJpa> extends JpaRepository<UserEntity,String> {

    /**
     * 根据id获得用户信息
     * @param id
     * @return
     */
    public UserEntity getUserEntityById(String id);
}
