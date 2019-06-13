package com.msgpig.notification.entities.controller.response;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
}
