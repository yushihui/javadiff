package com.future.jdiff;

public class Operation {

    private char sign;
    private String name;
    private char tag;

    public Operation(char sign, String name, char tag) {
        this.sign = sign;
        this.name = name;
        this.tag = tag;
    }

    public char getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public char getTag() {
        return tag;
    }
}
