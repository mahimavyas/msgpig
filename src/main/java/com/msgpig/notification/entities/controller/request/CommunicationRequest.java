package com.msgpig.notification.entities.controller.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.entities.model.Medium;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunicationRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String entity;
    private String action;
    private String state;
    private Object payload;

}
