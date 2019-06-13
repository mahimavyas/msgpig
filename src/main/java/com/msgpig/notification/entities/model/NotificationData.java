package com.msgpig.notification.entities.model;

import java.io.Serializable;

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
public class NotificationData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String body;

    private String title;
    
    private String sound;
    
    private String subtitle;
    
  //  private String click_action;
    
    private String icon;

}
