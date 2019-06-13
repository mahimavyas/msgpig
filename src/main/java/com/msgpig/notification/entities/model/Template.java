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
public class Template implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private TemplateType type;
    //sms
    private String smsTextTemplate;
    //push
    private String title;
    private String description;
    private String imageUrl;
    private String htmlTemplate;
    //inapp
    private Boolean showInAPP;
    private String inAPPLayout;
    private String inAPPTitle;
    private String inAPPBody;
    private String inAPPImageUrl;
    private String showPrimaryButton;
    private String primaryButtonText;
    private String primaryButtonCTA;
    private String showSecondaryButton;
    private String secondaryButtonText;
    private String secondaryButtonCTA;
    //email
    private String subject;
    private String body;
    private String toEmailIdPath;
    private String attachmentPath;
    private Boolean sendToUser; 
   
}
