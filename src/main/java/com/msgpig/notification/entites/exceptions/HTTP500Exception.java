package com.msgpig.notification.entites.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "There is something wrong with the server")
public class HTTP500Exception extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

}