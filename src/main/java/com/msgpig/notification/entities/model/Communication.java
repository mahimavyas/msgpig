package com.msgpig.notification.entities.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msgpig.notification.entities.enums.TemplateType;
import com.msgpig.notification.entities.enums.UserType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Communication implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private UserType userType;
    private Medium medium;
    private String androidLink;
    private String iosLink;
    private String webLink;
    private String userIdPath;
}
