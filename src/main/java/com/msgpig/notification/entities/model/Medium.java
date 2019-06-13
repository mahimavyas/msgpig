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
public class Medium implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer emailTemplateId;
    private Integer smsTemplateId;
    private Integer pushTemplateId;

}
