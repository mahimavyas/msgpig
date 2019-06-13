package com.msgpig.notification.entities.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.model.Template;
import com.msgpig.notification.entities.service.DeeplinkService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.service.SMSService;
import com.msgpig.notification.entities.service.TemplateService;
import com.msgpig.notification.entities.utils.Constants;

@Service(value = Constants.SMS_Notification_Service)
public class SMSNotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationServiceImpl.class);

    @Autowired
    SMSService smsService;

    @Autowired
    DeeplinkService deepLinkService;

    @Autowired
    TemplateService templateService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public Boolean sendNotication(NotificationRequest request) throws Exception {
        logger.info("Start sending sms {}", request);
        Template template = templateService.getTemplateById(request.getMedium().getSmsTemplateId());
        if (template != null)
        {

            List<String> phoneNumbers = null;
            if (request.getSendersList() != null)
            {
                phoneNumbers = request.getSendersList().stream().map(a -> a.getPhoneNumber())
                        .distinct().collect(Collectors.toList());
            }

            String androidLink = templateService.fillTemplate(request.getAndroidLink(), request.getPayload());
            String iosLink = templateService.fillTemplate(request.getIosLink(), request.getPayload());
            String webLink = templateService.fillTemplate(request.getWebLink(), request.getPayload());
            logger.info("this is web link {}", webLink );
            if (webLink == null)
            {
                webLink = baseUrl;
            }
            String deeplink = deepLinkService.generateDeepLink(androidLink, iosLink, webLink, request.getUserType());
            if (deeplink == null)
            {
                logger.error("Can't trigger notification deeplink is null");
            }

            String smsTemplate = template.getSmsTextTemplate();
            String smsText = templateService.fillTemplate(smsTemplate, request.getPayload());
            for (String phone : phoneNumbers) {
                if (phone != null && !phone.isEmpty())
                {
                    logger.info(" senting sms {} -{}", phone, smsText);
                    if (deeplink == null)
                        smsService.sendSMS(phone, smsText);
                    else
                        smsService.sendSMS(phone, smsText + " " + deeplink);
                }
            }

        }

        return false;
    }
}
