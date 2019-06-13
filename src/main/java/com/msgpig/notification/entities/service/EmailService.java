package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;

@Service
public interface EmailService
{
   
    Boolean sendEmail(String[] emailTo, String subject, String[] attachments, String body, boolean isHTML) throws Exception;
}

