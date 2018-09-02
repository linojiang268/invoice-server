package com.icar.invoice.response.entity;

public class ObjectEntity extends ResponseEntity {
    public Object data;

    public ObjectEntity() {
        super(0);
    }

    public ObjectEntity(Object data) {
        super(0);
        this.data = data;
    }
}
