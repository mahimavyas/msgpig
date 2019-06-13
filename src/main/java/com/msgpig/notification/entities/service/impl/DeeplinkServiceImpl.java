package com.msgpig.notification.entities.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.entities.service.DeeplinkService;
import com.msgpig.notification.entities.utils.Constants;
import com.msgpig.notification.firebase.dynamicLink.AndroidInfo;
import com.msgpig.notification.firebase.dynamicLink.DestinationOS;
import com.msgpig.notification.firebase.dynamicLink.DestinationType;
import com.msgpig.notification.firebase.dynamicLink.DynamicDeepLinkRequest;
import com.msgpig.notification.firebase.dynamicLink.DynamicDeepLinkResponse;
import com.msgpig.notification.firebase.dynamicLink.DynamicLinkInfo;
import com.msgpig.notification.firebase.dynamicLink.IosInfo;
import com.msgpig.notification.firebase.dynamicLink.NavigationInfo;
import com.msgpig.notification.firebase.dynamicLink.Suffix;

@Service
public class DeeplinkServiceImpl implements DeeplinkService {

    private static final Logger logger = LoggerFactory.getLogger(DeeplinkServiceImpl.class);

    @Value("${firebase.dynamiclink.url}")
    private String firebaseDynamicLinkUrl;

    @Value("${firebase.baseLink.url}")
    private String domainLink;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String generateDeepLink(String androidLink, String iosLink, String webLink, UserType type) throws Exception {
        logger.info("started generating firebase link");
        DynamicDeepLinkRequest request = new DynamicDeepLinkRequest();
        Suffix suffix = new Suffix();
        suffix.setOption("UNGUESSABLE");
        request.setSuffix(suffix);
        DynamicLinkInfo info = new DynamicLinkInfo();
        info.setLink(webLink);
        info.setDynamicLinkDomain(domainLink);
        AndroidInfo aInfo = new AndroidInfo();
        aInfo.setAndroidLink(androidLink);
        aInfo.setAndroidPackageName(Constants.getPackageName(DestinationOS.Android, type));
        info.setAndroidInfo(aInfo);
        IosInfo iInfo = new IosInfo();
        iInfo.setIosBundleId(Constants.getPackageName(DestinationOS.IOS, type));
        iInfo.setIosAppStoreId(Constants.getAPPStoreID(type));
        iInfo.setIosIpadBundleId(Constants.getPackageName(DestinationOS.IOS, type));
        info.setIosInfo(iInfo);
        
        NavigationInfo nInfo = new NavigationInfo();
        nInfo.setEnableForcedRedirect(true);
        info.setNavigationInfo(nInfo);
        
        request.setDynamicLinkInfo(info);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept-Charset", "UTF-8");

        HttpEntity<DynamicDeepLinkRequest> entityRequest = new HttpEntity<DynamicDeepLinkRequest>(request, headers);
        try {
            ResponseEntity<DynamicDeepLinkResponse> dataEntity = restTemplate.exchange(firebaseDynamicLinkUrl, HttpMethod.POST, entityRequest,
                    DynamicDeepLinkResponse.class);
            if (dataEntity.getStatusCode()
                != HttpStatus.OK)
            {
                logger.error("Error generating link{}", dataEntity);
            }
            else
            {
                DynamicDeepLinkResponse response = dataEntity.getBody();
                if (response != null)
                {
                    logger.info("generated firebase link");
                    return response.getShortLink();
                }

            }

        } catch (RestClientException e) {
            logger.error("Error generating link {}", e);
        }
        
        return null;
    }
}
