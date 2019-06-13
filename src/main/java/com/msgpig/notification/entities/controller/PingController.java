package com.msgpig.notification.entities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msgpig.notification.entities.service.EmailService;
import com.msgpig.notification.entities.utils.CommonUtils;


@RestController
public class PingController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String getPingResponse() {
    		//Add health check conditions
            return "PONG";
    }
    
}
