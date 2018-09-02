package com.icar.invoice.response.entity;

public class ListEntity extends ResponseEntity {
    public int total;
    public int pages;
    public Object data;

    public ListEntity(int total, int pages, Object data) {
        super(0);
        this.total = total;
        this.pages = pages;
        this.data = data;
    }
}
