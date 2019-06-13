package com.msgpig.notification.entities.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.controller.response.SuccessResponse;
import com.msgpig.notification.entities.service.NotificationFacedeService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.utils.Constants;
import com.msgpig.notification.entities.utils.ResponseUtils;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    @Qualifier(Constants.MAIN_NOTIFICATION_SERVICE)
    NotificationFacedeService notificationService;

    // read all
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<Object> send(

            @RequestBody NotificationRequest request
            ) {

        logger.info("Start send notification");
        HttpHeaders headers = ResponseUtils.getResponseHeader();
        HttpStatus status = HttpStatus.OK;
        if (request != null)
        {
            try {

                if (notificationService.sendNotication(request))
                {
                    logger.info("Exited send notification");
                    SuccessResponse resp = new SuccessResponse();
                    resp.setMsg("Triggered successfully");
                    return new ResponseEntity<Object>(resp, headers, status);
                }

            } catch (Exception e) {
                logger.error("Error send notification", e);
                return ResponseUtils.sendErrorResponse(new
                        com.msgpig.notification.entities.controller.response.ErrorResponse("001", "ErrorResponse", e.getMessage()),
                        headers,
                        HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseUtils.sendErrorResponse(new
                com.msgpig.notification.entities.controller.response.ErrorResponse("001", "ErrorResponse", "Error  send notification"),
                headers,
                HttpStatus.BAD_REQUEST);

    }

}
