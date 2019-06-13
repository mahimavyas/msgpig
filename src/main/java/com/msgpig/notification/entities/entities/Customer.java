package com.msgpig.notification.entities.entities;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

  
    private String customerId;
    private String customerUID;
    
    private String customerName;
    private String secToken;
    
    private String customerPhone;
    private String customerEmail;
    private Integer customerAge;
    private Long dateOfBirth;
    private String customerProfileImage;
    private Long createdAt;
    private Long updatedAt;
    private String updatedBy;
    private List<Device> devices;
}
