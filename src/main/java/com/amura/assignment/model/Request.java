package com.amura.assignment.model;

import java.io.Serializable;

public class Request implements Serializable {
    private String input;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
