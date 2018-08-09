package com.example.springcloudzuul.zuulenum;

/**
 * zuul中 Filter的类型的枚举
 * @author liuxy
 */
public enum FilterEnum {

    /**
     *pre
     */
    PRE("pre"),

    /**
     * routing
     */
    ROUTING("routing"),

    /**
     * post
     */
    POST("post"),

    /**
     * error
     */
    ERROR("error");


    private FilterEnum(String value){
        this.value = value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }
}
