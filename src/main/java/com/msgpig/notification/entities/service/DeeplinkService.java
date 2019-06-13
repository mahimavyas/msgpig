package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.firebase.dynamicLink.DestinationType;

@Service
public interface DeeplinkService
{
    String generateDeepLink(String androidLin, String iosLink, String webLink,UserType type) throws Exception;
}

