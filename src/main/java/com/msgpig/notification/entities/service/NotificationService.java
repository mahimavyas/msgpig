package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;

@Service
public interface NotificationService
{
    Boolean sendNotication(NotificationRequest request) throws Exception;
}

