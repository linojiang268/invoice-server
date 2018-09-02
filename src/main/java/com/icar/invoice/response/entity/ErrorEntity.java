package com.icar.invoice.response.entity;

public class ErrorEntity extends ResponseEntity {
    public String msg;

    public ErrorEntity(int code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }
}
