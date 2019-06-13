package com.msgpig.notification.entities.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.CommunicationRequest;
import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.model.Communication;
import com.msgpig.notification.entities.model.EntityCommunicationConfig;
import com.msgpig.notification.entities.service.CommConfigService;
import com.msgpig.notification.entities.service.CommunicationService;
import com.msgpig.notification.entities.service.NotificationFacedeService;
import com.msgpig.notification.entities.service.NotificationService;

@Service
public class CommunicationServiceImpl implements CommunicationService
{

    private static final Logger logger = LoggerFactory.getLogger(CommunicationServiceImpl.class);

    private ExecutorService executor = Executors.newFixedThreadPool(200);

    @Autowired
    CommConfigService commConfigService;

    @Autowired
    NotificationFacedeService notificationService;

    @Override
    public Boolean sendNotication(CommunicationRequest request) throws Exception {

        if (request == null)
            throw new Exception("Empty request");

        if (request.getAction() == null || request.getEntity() == null)
            throw new Exception("Entity/Action missing");

        // Get configurations
        EntityCommunicationConfig config = commConfigService.getConfigById(request.getEntity(), request.getAction(), request.getState());
        if (config == null || config.getCommunications() == null)
            throw new Exception("Configuation missing. Please add config");

        config.getCommunications().forEach(comm -> {
            executor.submit(() -> {
                if (comm != null) {
                    logger.info("Trigger comm");
                    notify(comm, request.getPayload());
                    logger.info("comm triggered");
                }
            });

        });

        return true;

    }

    private Boolean notify(Communication comm, Object payload)
    {
        String userIdPath = comm.getUserIdPath();
        PropertyUtilsBean pub = new PropertyUtilsBean();
        try {
            Object userId = pub.getProperty(payload, userIdPath);
            if (userId != null)
            {
                NotificationRequest request = NotificationRequest.builder()
                        .iosLink(comm.getIosLink())
                        .webLink(comm.getWebLink())
                        .androidLink(comm.getAndroidLink())
                        .medium(comm.getMedium()).payload(payload)
                        .userType(comm.getUserType()).userIds(new String[] { userId.toString() }).build();

                return notificationService.sendNotication(request);
            }

        } catch (IllegalAccessException e) {
            logger.error("Error triggering communication {}", e);
        } catch (InvocationTargetException e) {
            logger.error("Error triggering communication {}", e);
        } catch (NoSuchMethodException e) {
            logger.error("Error triggering communication {}", e);
        } catch (Exception e) {
            logger.error("Error triggering communication {}", e);
        }
        return false;
    }

}
