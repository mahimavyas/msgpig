package com.msgpig.notification.entities.service.thirdpartyproviders;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.service.SMSService;
import com.msgpig.notification.entities.service.impl.SMSNotificationServiceImpl;
import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;

@Service
public class PlivoSMSService implements SMSService
{
    @Value("${plivo.authId}")
    private String authId;
    @Value("${plivo.authToken}")
    private String authToken;

    @Value("${senderId}")
    private String senderId;

    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationServiceImpl.class);

    @Override
    public Boolean sendSMS(String receiver, String text) throws Exception {
        try {
            logger.info("Start Sending SMS {} ", receiver);

            if (receiver != null && !receiver.isEmpty())
            {
                LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();

                RestAPI api = new RestAPI(authId, authToken, "v1");
                parameters.put("src", senderId);
                parameters.put("dst", receiver);
                parameters.put("text", text);
                parameters.put("method", "GET");

                MessageResponse msgResponse = api.sendMessage(parameters);

                if (msgResponse.serverCode.intValue() != 202) {
                    throw new Exception("SMS was not delivered to number " + receiver + "; reason: " + msgResponse.error);
                }
            }
            logger.info("ended sending SMS");
            return true;

        } catch (Exception e) {

            logger.error("Error Sending SMS {}", e);
            return false;
        }
    }

}
