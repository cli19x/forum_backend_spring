package com.example.cs4704.model_reponse;

import org.springframework.context.annotation.Bean;


public class ResponseObject {
    private String msg;
    private String errMsg;
    private  Object objects;

    public ResponseObject() {
    }

    public ResponseObject(String msg, String errMsg, Object objects) {
        this.msg = msg;
        this.errMsg = errMsg;
        this.objects = objects;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Object getObjects() {
        return objects;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setObjects(Object objects) {
        this.objects = objects;
    }
}
