package com.example.springcloudzuul.zuulenum;

public enum HttpParameterEnum {

    /**
     * token
     */
    TOKEN("token");


    private String value;

    private HttpParameterEnum(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
