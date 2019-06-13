package com.msgpig.notification.entities.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.msgpig.notification.entities.controller.response.ErrorResponse;
import com.msgpig.notification.entities.controller.response.GenericResponse;

public class ResponseUtils {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtils.class);

    public static HttpHeaders getResponseHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return headers;
    }

    public static HttpEntity<Object> sendResponse(Object response, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<Object>(response, headers, status);
    }

    public static ResponseEntity<Object> sendErrorResponse(ErrorResponse response, HttpHeaders headers, HttpStatus status, String methodName) {
        GenericResponse<Object> res = new GenericResponse<>();
        res.setError(response);
        logger.error("Error in " + methodName + " with response: " + res.toString());
        return new ResponseEntity<Object>(res, headers, status);
    }

    public static ResponseEntity<Object> sendErrorResponse(ErrorResponse response, HttpHeaders headers, HttpStatus status) {
        GenericResponse<Object> res = new GenericResponse<>();
        res.setError(response);
        logger.error("Error with response: " + res.toString());
        return new ResponseEntity<Object>(res, headers, status);
    }

}
