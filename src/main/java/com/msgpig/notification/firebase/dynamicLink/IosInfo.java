package com.msgpig.notification.firebase.dynamicLink;

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
public class IosInfo implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String iosBundleId;
    private String iosFallbackLink;
    private String iosCustomScheme;
    private String iosIpadFallbackLink;
    private String iosIpadBundleId;
    private String iosAppStoreId;
}
