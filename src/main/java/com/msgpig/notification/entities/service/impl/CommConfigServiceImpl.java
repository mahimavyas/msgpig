package com.msgpig.notification.entities.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.dao.EntityCommunicationConfigRepository;
import com.msgpig.notification.entities.model.EntityCommunicationConfig;
import com.msgpig.notification.entities.service.CommConfigService;

@Service
public class CommConfigServiceImpl implements CommConfigService
{

    @Autowired
    EntityCommunicationConfigRepository repo;

    @Override
    public EntityCommunicationConfig getConfigById(String entity, String action, String state) {

        if (state == null)
            state = "ALL";
        return repo.findEntityCommunicationConfigById(entity, action, state);
    }

}
