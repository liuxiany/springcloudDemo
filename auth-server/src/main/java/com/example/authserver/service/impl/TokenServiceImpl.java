package com.example.authserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.authserver.dao.UserEntityJpa;
import com.example.authserver.entity.UserEntity;
import com.example.authserver.service.TokenService;
import io.jsonwebtoken.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxy
 * @date 2018-08-17 15:25
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private UserEntityJpa userEntityJpa;

    @Value("${authSecret}")
    private String authSecret;

    @Override
    public String createToken(String userId) throws Exception{

        logger.info("authSecret:{}",authSecret);

        if(StringUtils.isBlank(userId)){
            return null;
        }

        UserEntity userEntity = userEntityJpa.getUserEntityById(userId);

        if(userEntity == null){
            return null;
        }

        Key key = new SecretKeySpec(
                authSecret.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> stringObjectMap = new HashMap<String, Object>(16){
            {
                put("alg", "HS256");
                put("typ", "JWT");
            }
        };

        JSONObject payload = new JSONObject(){
            {
                put("userId",userId);
                //3分钟后过期
                put("exp",System.currentTimeMillis() + 180000);
            }
        };

        String compactJws = Jwts.builder()
                .setHeader(stringObjectMap)
                .setPayload(payload.toJSONString())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();


        /*System.out.println("jwt key:" + new String(key.getEncoded()));
        System.out.println("jwt payload:" + payload);
        System.out.println("jwt encoded:" + compactJws);*/

        return compactJws;

    }

    @Override
    public Boolean checkToken(String token) throws Exception{

        if(StringUtils.isBlank(token)){
            return false;
        }

        logger.info("token is {}",token);

        Key key = new SecretKeySpec(authSecret.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);

        Header header = claimsJws.getHeader();

        Claims body = claimsJws.getBody();

        /*System.out.println("jwt header:" + header);
        System.out.println("jwt body:" + body);
        System.out.println("jwt body userId:" + body.get("userId", String.class));*/

        Map<String, Object> stringObjectMap = new HashMap<String, Object>(16){
            {
                put("alg", header.get("alg"));
                put("typ", header.get("typ"));
            }
        };

        JSONObject payload = new JSONObject(){
            {
                put("userId",body.get("userId"));
                //3分钟后过期
                put("exp",body.get("exp"));
            }
        };

        String secondCompact = Jwts.builder()
                .setHeader(stringObjectMap)
                .setPayload(JSON.toJSONString(payload))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();


//        System.out.println("jwt encoded:" + secondCompact);

        if(!token.equals(secondCompact)){
           return false;
        }

        String userId = body.get("userId",String.class);

        if(StringUtils.isBlank(userId)){
            return false;
        }

        UserEntity userEntity = userEntityJpa.getUserEntityById(userId);

        if(userEntity == null){
            return false;
        }

        //校验是否过期
        Long expDate = (Long) body.get("exp");

        logger.info("expDate:{},currentTimeMillis:{}",expDate,System.currentTimeMillis());

        if(expDate <= System.currentTimeMillis()){
            return false;
        }

        return true;
    }
}
