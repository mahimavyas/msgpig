package com.msgpig.notification.entities.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.model.Template;
import com.msgpig.notification.entities.service.EmailService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.service.TemplateService;
import com.msgpig.notification.entities.utils.Constants;

@Service(value = Constants.Email_Notification_Service)
public class EmailNotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    @Autowired
    TemplateService templateService;

    @Autowired
    EmailService emailService;

    @Override
    public Boolean sendNotication(NotificationRequest request) throws Exception {
        logger.info("Start sending email {}", request);
        Template template = templateService.getTemplateById(request.getMedium().getEmailTemplateId());
        if (template != null)
        {
            String body = templateService.fillTemplate(template.getBody(), request.getPayload());
            String toEmail = null;
            if (template.getToEmailIdPath() != null)
                toEmail = templateService.fillTemplate(template.getToEmailIdPath(), request.getPayload());

            String attachments = templateService.fillTemplate(template.getAttachmentPath(), request.getPayload());
            String subject = templateService.fillTemplate(template.getSubject(), request.getPayload());

            if (template.getSendToUser() != null && !template.getSendToUser())
            {
                // get email Id of the sender ==> based on user Type call different APIs and get emailInfo
            }
            String[] attachmentArray = null, emailToArray = null;
            if (toEmail == null)
            {
                toEmail = "mahesh@iliv.in";
            }
            if (toEmail != null &&
                    !toEmail.isEmpty())
            {
                emailToArray = Arrays.stream(toEmail.split(",")).map(String::trim).toArray(String[]::new);
            }
            if (attachments != null)
            {
                attachmentArray = Arrays.stream(attachments.split(",")).map(String::trim).toArray(String[]::new);
            }

            emailService.sendEmail(emailToArray, subject, attachmentArray, body, true);

        }
        logger.info("email senting end {}", request);
        return true;
    }
}
