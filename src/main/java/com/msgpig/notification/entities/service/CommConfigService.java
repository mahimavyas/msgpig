package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.model.EntityCommunicationConfig;
import com.msgpig.notification.entities.model.Template;

@Service
public interface CommConfigService
{
    EntityCommunicationConfig getConfigById(String entity, String action, String state);
}
