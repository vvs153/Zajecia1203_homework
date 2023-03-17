package com.sda.homework1203.errors;

import lombok.Data;


public class Error<T> {
    private int code;
    private T message;
    private ErrorType errorType = ErrorType.GENERAL;

    public Error(int code, T message, ErrorType errorType) {
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }
    public Error(int code, T message) {
        this.code = code;
        this.message = message;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
