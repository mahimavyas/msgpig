package com.msgpig.notification.entities.controller.request;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msgpig.notification.entities.entities.CommunicationInfo;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.entities.model.Medium;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String[] userIds;
    private List<CommunicationInfo> sendersList;
    private UserType userType;
    private String userToken;
    private String topic;
    private Medium medium;
    private Object payload;
    private String androidLink;
    private String iosLink;
    private String webLink;

}
