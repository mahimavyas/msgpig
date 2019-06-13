package com.msgpig.notification.entities.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.entities.CommunicationInfo;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.firebase.dynamicLink.DestinationType;

@Service
public interface UserService
{
    List<CommunicationInfo> getCommunicationInfo(String[] userIds, UserType type) throws Exception;
}

