package com.example.demo.login.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class MsgEntity {

    private String msg;
    private Object result;

    public MsgEntity(String msg, Object result) {
        this.msg = msg;
        this.result  = result;
    }
}