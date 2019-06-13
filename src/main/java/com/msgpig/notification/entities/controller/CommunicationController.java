package com.msgpig.notification.entities.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msgpig.notification.entities.controller.request.CommunicationRequest;
import com.msgpig.notification.entities.controller.request.NotificationRequest;
import com.msgpig.notification.entities.controller.response.SuccessResponse;
import com.msgpig.notification.entities.service.CommunicationService;
import com.msgpig.notification.entities.service.NotificationService;
import com.msgpig.notification.entities.utils.ResponseUtils;

@RestController
@RequestMapping("/communications")
public class CommunicationController {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationController.class);
    @Autowired
    CommunicationService commService;

    // read all
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<Object> send(

            @RequestBody CommunicationRequest request
            ) {

        logger.info("Start sending communication {}",request);
        HttpHeaders headers = ResponseUtils.getResponseHeader();
        HttpStatus status = HttpStatus.OK;
        if (request != null)
        {
            try {

                if (commService.sendNotication(request))
                {
                    logger.info("Exited  sending communication");
                    SuccessResponse resp = new SuccessResponse();
                    resp.setMsg("Triggered successfully");
                    return new ResponseEntity<Object>(resp, headers, status);
                }

            } catch (Exception e) {
                logger.error("Error sending communication", e);
                return ResponseUtils.sendErrorResponse(new
                        com.msgpig.notification.entities.controller.response.ErrorResponse("001", "ErrorResponse", e.getMessage()),
                        headers,
                        HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseUtils.sendErrorResponse(new
                com.msgpig.notification.entities.controller.response.ErrorResponse("001", "ErrorResponse", "Error sending communication"),
                headers,
                HttpStatus.BAD_REQUEST);

    }

}
