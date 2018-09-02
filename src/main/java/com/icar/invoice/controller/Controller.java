package com.icar.invoice.controller;

import com.icar.invoice.response.entity.ErrorEntity;
import com.icar.invoice.response.entity.ListEntity;
import com.icar.invoice.response.entity.ObjectEntity;

public class Controller {
    public ErrorEntity jsonException(int code, String message) {
        return new ErrorEntity(code, message);
    }

    public ObjectEntity json() {
        return new ObjectEntity();
    }

    public ObjectEntity json(Object data) {
        return new ObjectEntity(data);
    }

    public ListEntity json(int total, int pages, Object data) {
        return new ListEntity(total, pages, data);
    }
}
