package com.example.authserver.service;

/**
 * @author liuxy
 * @date 2018-08-17 15:25
 */
public interface TokenService {

    /**
     * 创建token
     * @param userId
     * @return
     */
    public String createToken(String userId) throws Exception;

    /**
     * 验证token
     * @param token
     * @return
     */
    public Boolean checkToken(String token) throws Exception;
}
