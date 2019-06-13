package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.CommunicationRequest;
import com.msgpig.notification.entities.controller.request.NotificationRequest;

@Service
public interface CommunicationService
{
    Boolean sendNotication(CommunicationRequest request) throws Exception;
}
