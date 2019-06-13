package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;

@Service
public interface NotificationFacedeService
{
    Boolean sendNotication(NotificationRequest request) throws Exception;
}

