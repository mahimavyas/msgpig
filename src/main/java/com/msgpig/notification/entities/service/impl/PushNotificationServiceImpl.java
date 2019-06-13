package com.msgpig.notification.entities.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.entities.Device;
import com.msgpig.notification.entities.model.NData;
import com.msgpig.notification.entities.model.NotificationData;
import com.msgpig.notification.entities.model.NotificationRequestModel;
import com.msgpig.notification.entities.model.Template;
import com.msgpig.notification.entities.service.DeeplinkService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.service.TemplateService;
import com.msgpig.notification.entities.utils.Constants;

@Service(value = Constants.Push_Notification_Service)
public class PushNotificationServiceImpl implements NotificationService {

    @Value("${firebase.config.fcm}")
    private String url;

    @Value("${firebase.config.key}")
    private String key;

    @Autowired
    TemplateService templateService;

    @Autowired
    DeeplinkService deepLinkService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
    private static Gson gson = new Gson();

    public Boolean send(NotificationRequestModel request) throws UnsupportedEncodingException
    {

        if ((request.getRegistration_ids() != null && request.getRegistration_ids().size() > 0) || request.getTo() != null)
        {
            logger.info("Start sending notification {}", gson.toJson(request));
            logger.info("notification url {}", url);
            logger.info("notification key {}", key);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "key=" + key);
            headers.add("Accept-Charset", "UTF-8");

            HttpEntity<NotificationRequestModel> entity = new HttpEntity<NotificationRequestModel>(request, headers);
            try {
                ResponseEntity<String> dataEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                if (dataEntity.getStatusCode()
                != HttpStatus.OK)
                {
                    logger.error("Error sending notification {}", dataEntity);
                    return false;
                }
            } catch (RestClientException e) {

                logger.error("Error sending notification {}", e);
                return false;
            }

            logger.info("notification sent successfully {}", request);
            return true;
        } else
        {
            logger.error("Error sending notification registration ids missing");
            return false;
        }
    }

    @Override
    public Boolean sendNotication(NotificationRequest request) throws Exception {
        Template template = templateService.getTemplateById(request.getMedium().getPushTemplateId());
        if (template != null)
        {

            NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
            notificationRequestModel.setPriority("high");
            NotificationData notificationData = new NotificationData();

            notificationData.setBody(templateService.fillTemplate(template.getDescription(), request.getPayload()));
            notificationData.setTitle(templateService.fillTemplate(template.getTitle(), request.getPayload()));

            String androidLink = templateService.fillTemplate(request.getAndroidLink(), request.getPayload());
            String iosLink = templateService.fillTemplate(request.getIosLink(), request.getPayload());
            String webLink = templateService.fillTemplate(request.getWebLink(), request.getPayload());
            if (webLink == null)
            {
                webLink = baseUrl;
            }
           

            // notificationData.setClick_action(androidLink);
            notificationData.setSound("default");
            notificationRequestModel.setNotification(notificationData);
            notificationRequestModel.setData(fillNData(template, request.getPayload(), androidLink));

            if (request.getTopic() != null)
            {
                notificationRequestModel.setTo("/topics/" + request.getTopic());
            }
            else if (request.getSendersList() != null)
            {
                List<String> regIds = request.getSendersList().stream().filter(a -> a.getDevices() != null)
                        .flatMap(a -> a.getDevices().stream().map(Device::getDeviceToken)).distinct().collect(Collectors.toList());

                if (regIds != null && regIds.size() == 1)
                {
                    notificationRequestModel.setTo(regIds.get(0));
                }
                else
                    notificationRequestModel.setRegistration_ids(regIds);
            }
            else if (request.getUserToken() != null)
            {
                notificationRequestModel.setTo(request.getUserToken());
            }

            return send(notificationRequestModel);

        }
        return false;
    }

    private NData fillNData(Template template, Object payload, String clickAction)
    {

        return NData.builder().
                secondaryButtonCTA(template.getSecondaryButtonCTA())
                .click_action(clickAction)
                .imageUrl(template.getImageUrl())
                .inAPPBody(template.getInAPPBody())
                .inAPPImageUrl(template.getInAPPImageUrl())
                .inAPPLayout(template.getInAPPLayout())
                .inAPPTitle(template.getInAPPTitle())
                .primaryButtonCTA(template.getPrimaryButtonCTA())
                .primaryButtonText(template.getPrimaryButtonText())
                .secondaryButtonText(template.getSecondaryButtonText())
                .showInAPP(template.getShowInAPP())
                .showPrimaryButton("Test")
                .showSecondaryButton("Test")
                .body(templateService.fillTemplate(template.getDescription(), payload))
                .title(templateService.fillTemplate(template.getTitle(), payload))
                .build();

    }
}
