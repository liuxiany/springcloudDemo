package com.example.springcloudzuul.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ResultMessage {
    private String code;
    private String message;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public ResultMessage(){

    }

    public ResultMessage(String code, String message, Date date){
        this.code = code;
        this.message = message;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
