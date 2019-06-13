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
public class NData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String imageUrl;
    private String click_action;
    private String body;
    private String title;
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
}
