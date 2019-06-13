package com.msgpig.notification.entities.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String deviceId;

    private String deviceType;
    
    private String deviceToken;
    
    private Boolean isActive;
    

}
