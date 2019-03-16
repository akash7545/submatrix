package com.amura.assignment.model;

import java.io.Serializable;

public class Response implements Serializable{

    private Object response;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
