package com.example.springcloudzuul.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Map;

public class ResultMessage {
    private Integer code;
    private String message;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Map<String,Object> data;

    public ResultMessage(){

    }

    public ResultMessage(Integer code, String message, Date date,Map<String,Object>  data){
        this.code = code;
        this.message = message;
        this.date = date;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
