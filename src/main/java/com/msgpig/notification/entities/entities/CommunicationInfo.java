package com.msgpig.notification.entities.entities;

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
public class CommunicationInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    String id;
    String name;
    String phoneNumber;
    String email;
    List<Device> devices;
}
