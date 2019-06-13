package com.msgpig.notification.entities.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msgpig.notification.entities.entities.CommunicationInfo;
import com.msgpig.notification.entities.entities.Customer;
import com.msgpig.notification.entities.entities.Doctor;
import com.msgpig.notification.entities.enums.UserType;
import com.msgpig.notification.entities.service.UserService;
import com.msgpig.notification.entities.utils.JWTHelper;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    JWTHelper jwtHelper;
    
    
    @Autowired
    RestTemplate restTemplate;

    @Value("${getcustomersInfo}")
    private String customersUrl;

    @Override
    public List<CommunicationInfo> getCommunicationInfo(String[] userIds, UserType type) throws Exception {
       
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept-Charset", "UTF-8");
		headers.add("secToken", jwtHelper.generateKey("1", UserType.INTERNAL));
        if (userIds != null && type != null)
        {
            logger.info("getting commuinciation Info {}, {}",  userIds, type);
            List<String> regIds = null;
            if (type == UserType.CUSTOMER)
            {
                String url = customersUrl + String.join(",", userIds);
                ResponseEntity<Customer[]> c = restTemplate.exchange(
                		url, HttpMethod.GET, new HttpEntity<Object>(headers),
                		Customer[].class);
                if (c != null && c.getBody() != null)
                {
                    Customer[] customers = c.getBody();
                    return getCommunicationInfo(customers);
                }
            }
           
    }
        return null;
    }

    private List<CommunicationInfo> getCommunicationInfo(Customer[] customers) {
        List<CommunicationInfo> commInfo = new ArrayList<CommunicationInfo>();
        if(customers !=null)
        {
            for (Customer customer : customers) {
                CommunicationInfo cInfo = new CommunicationInfo();
                cInfo.setId(customer.getCustomerId());
                cInfo.setDevices(customer.getDevices());
                cInfo.setEmail(customer.getCustomerEmail());
                cInfo.setName(customer.getCustomerName());
                cInfo.setPhoneNumber(customer.getCustomerPhone());
                commInfo.add(cInfo);
            }
           
        }
        return commInfo;
    }

   
}
