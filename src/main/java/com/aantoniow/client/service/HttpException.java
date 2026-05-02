package com.aantoniow.client.service;

public class HttpException extends RuntimeException {
    public HttpException(String message) {
        super(message);
    }
    public HttpException(String message, Exception cause) {
        super(message, cause);
    }

}