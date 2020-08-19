package com.circuitbreaker.exception;

import org.springframework.http.HttpStatus;

/**
 * HttpClientException.java
 *
 * The implementor of the library can throw a custom exception using this class
 * so That the incerceptor can check for 4xx exceptions so that i will not add to
 * consider this as a failure count.
 *
 * or implementor of this library can add nontrip
 *
 */
public class HttpClientException extends RuntimeException {

    private String api;
    private HttpStatus statusCode;
    private String error;

    public HttpClientException(String api, HttpStatus statusCode, String error) {
        super(error);
        this.api = api;
        this.statusCode = statusCode;
        this.error = error;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
