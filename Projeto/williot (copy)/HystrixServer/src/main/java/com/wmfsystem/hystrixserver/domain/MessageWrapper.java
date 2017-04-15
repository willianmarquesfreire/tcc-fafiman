package com.wmfsystem.hystrixserver.domain;

/**
 * Created by wmfsystem on 4/15/17.
 */
public class MessageWrapper<T> {

    private T wrapped;
    private String message;

    public MessageWrapper(T wrapped, String message) {
        this.wrapped = wrapped;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public T getWrapped() {
        return wrapped;
    }
}