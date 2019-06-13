package com.msgpig.notification.entities.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.enums.UserType;

@Service
public class JWTHelper {

    @Value("${jwtkey}")
    private String key;

    private static final Logger logger = LoggerFactory.getLogger(JWTHelper.class);

    public String generateKey(String userId, UserType userType)
    {
        try {
            String jwt = Jwts.builder()
                    .setSubject(userId)
                    .setExpiration(new Date(CommonUtils.addDaysToDateLong(CommonUtils.getCurrentTimeInMillis(),30)))
                    .claim("userType", userType.toString())
                    .claim("permissions", null)
                    .signWith(
                            SignatureAlgorithm.HS256,
                            key.getBytes("UTF-8")
                    )
                    .compact();
            return jwt;
        } catch (InvalidKeyException e) {
            logger.error("Error generating JWT links{}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("Error generating JWT links{}", e);
        }
        return null;
    }


}
