package com.msgpig.notification.entities.service.impl;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.enums.ServiceType;
import com.msgpig.notification.entities.service.NotificationFacedeService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.service.UserService;
import com.msgpig.notification.entities.utils.Constants;

@Service(value = com.msgpig.notification.entities.utils.Constants.MAIN_NOTIFICATION_SERVICE)
public class NotificationServiceImpl implements NotificationFacedeService
{

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private ExecutorService executor = Executors.newFixedThreadPool(200);

    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Map<String, NotificationService> notificationServices;

    @Override
    public Boolean sendNotication(NotificationRequest request) throws Exception {

        if (!(request.getUserToken() != null || request.getTopic() != null || (request.getUserIds() != null && request.getUserType() != null)))
        {
            throw new Exception("Either of userId type/ token/topic is missing");
        }

        // fill senders list
        if (request.getUserIds() != null && request.getUserType() != null)
        {
            request.setSendersList(userService.getCommunicationInfo(request.getUserIds(), request.getUserType()));
        }

        if (request != null && request.getMedium() != null)
        {
            if (request.getMedium().getEmailTemplateId() != null)
            {
                executor.submit(() -> {
                    if (request != null) {
                        logger.info("Send Email");

                        NotificationService notificationService =
                                notificationServices.get(Constants.getServiceName(ServiceType.EMAIL));
                        try {
                            notificationService.sendNotication(request);
                        } catch (Exception e) {
                            logger.error("Error Send Email {}", e);
                        }

                    }
                });
            }
            if (request.getMedium().getSmsTemplateId() != null)
            {
                executor.submit(() -> {
                    if (request != null) {
                        logger.info("Send SMS");

                        NotificationService notificationService =
                                notificationServices.get(Constants.getServiceName(ServiceType.SMS));
                        try {
                            notificationService.sendNotication(request);
                        } catch (Exception e) {
                            logger.error("Error Send SMS {}", e);
                        }
                    }
                });
            }
            if (request.getMedium().getPushTemplateId() != null)
            {
                executor.submit(() -> {
                    if (request != null) {
                        logger.info("Send Notification");
                        try {

                            NotificationService notificationService =
                                    notificationServices.get(Constants.getServiceName(ServiceType.PUSH));
                            notificationService.sendNotication(request);
                        } catch (Exception e) {
                            logger.info("Error sending notification{}", e);
                        }

                    }
                });

            }
        }
        return true;
    }

}
