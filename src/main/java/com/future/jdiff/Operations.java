package com.future.jdiff;

public interface Operations {

    public Operation ADD = new Operation('+', "add", 'a');
    public Operation DELETE = new Operation('-', "delete", 'd');
    public Operation CHANGE = new Operation('~', "change", 'c');
    public Operation EQUAL = new Operation('=', "equal", 'e');

}
