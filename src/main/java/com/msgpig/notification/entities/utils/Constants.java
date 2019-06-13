package com.msgpig.notification.entities.utils;

import com.msgpig.notification.entities.enums.ServiceType;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.firebase.dynamicLink.DestinationOS;
import com.msgpig.notification.firebase.dynamicLink.DestinationType;

public final class Constants {
    public static final String TEMPLATE = "template";
    public static final String EntityCommunicationConfig = "communicationconfig";
    public static final String MAIN_NOTIFICATION_SERVICE = "mainNotificationService";
    public static final String Push_Notification_Service = "pushNotificationService";
    public static final String Email_Notification_Service = "emailNotificationService";
    public static final String SMS_Notification_Service = "smsNotificationService";
    public static final String SENDER_ID = "MSGPIG";
    public static final String INDIA_COUNTRY_CODE = "91";
    public static final String ANDROID_APP = "com.xxx.yyyy"; //TODO:change it to your app
    public static final String IOS_APP = "com.xxx.yyyy"; //TODO:change it to your app
    public static final String IOS_APP_STORE_ID = "23232313131"; //TODO:change it to your app

    public static String getServiceName(ServiceType type)
    {
        if (type == ServiceType.EMAIL)
        {
            return Email_Notification_Service;
        }
        else if (type == ServiceType.SMS)
        {
            return SMS_Notification_Service;
        }
        else if (type == ServiceType.PUSH)
        {
            return Push_Notification_Service;
        }
        return null;
    }

    public static String getPackageName(DestinationOS os, UserType type)
    {
    	if (type == UserType.CUSTOMER)
        {
            return (os == DestinationOS.Android)?ANDROID_APP:IOS_APP;
        }

        return null;
    }
    
    public static String getAPPStoreID(UserType type)
    {

        if (type == UserType.CUSTOMER)
        {
            return IOS_APP_STORE_ID;
        }

        return null;
    }
}
