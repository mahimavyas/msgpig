package com.msgpig.notification.entities.controller.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg;

    private String desc;
    
    public SuccessResponse(String msg)
    {
        this.msg = msg;
    }
    

}
