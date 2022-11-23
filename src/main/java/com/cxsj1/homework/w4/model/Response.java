package com.cxsj1.homework.w4.model;

public class Response {
    public int code;
    public String message;
    public Object data;

    public Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
