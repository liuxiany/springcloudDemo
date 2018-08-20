package com.example.springcloudzuul.zuulenum;

/**
 * zuul中 Filter的类型的枚举
 * @author liuxy
 */
public enum FilterEnum {

    /**
     * 认证服务uri
     */
    AUTH_URL("/auth-server/"),

    /**
     * 获取token的地址
     */
    GET_TOKEN("/auth-server/getToken?userId=");


    private FilterEnum(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }
}
