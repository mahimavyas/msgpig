package com.msgpig.notification.entities.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationRequestModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private NotificationData notification;
    private NData data;
    private String to;
    private List<String> registration_ids;
    private String priority;
}

