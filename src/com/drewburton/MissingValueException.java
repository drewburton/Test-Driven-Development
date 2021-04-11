package com.drewburton;

public class MissingValueException extends RuntimeException{
    public MissingValueException(String message) {
        super(message);
    }
}
