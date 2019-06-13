package com.msgpig.notification.entities.model;

import java.io.Serializable;
import java.util.List;

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
public class EntityCommunicationConfig implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    private String entity;
    private String action;
    private String state;
    private List<Communication> communications;

}
