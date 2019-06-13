package com.msgpig.notification.entities.service.thirdpartyproviders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import com.msgpig.notification.entities.entities.BasicFileInfo;
import com.msgpig.notification.entities.service.EmailService;
import com.sendgrid.Attachments;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class SendGridEmailServiceImpl implements EmailService
{
    @Value("${sendgrid.key}")
    private String key;
    @Value("${sendgrid.emailFrom}")
    private String emailFrom;

    private static final Logger logger = LoggerFactory.getLogger(SendGridEmailServiceImpl.class);

    @Override
    public Boolean sendEmail(String[] emailTo, String subject, String[] attachments, String body, boolean isHTML) throws Exception {

        logger.info("start sending email to:{},subject:{},body:{},{}", emailTo, subject, body);
        Email from = new Email(emailFrom);
        Email to = new Email(emailTo[0]);
      
        Content content = null;
        if (isHTML)
        {
            content = new Content("text/html", body);
        }
        else
        {
            content = new Content("text/plain", body);
        }

        Mail mail = new Mail(from, subject, to, content);

        if(emailTo.length>1)
        {
            mail.personalization = new ArrayList<Personalization>();
            for (int i = 0; i < emailTo.length; i++) {
                
                Personalization p = new Personalization();
                p.addTo(new Email(emailTo[i]));
                mail.personalization.add(p);
            }
        }
      
        if (attachments != null && attachments.length > 0)
        {
            mail.attachments = new ArrayList<Attachments>();
            for (int i = 0; i < attachments.length; i++) {
                String attachmentUrl = attachments[i];
                BasicFileInfo info = new BasicFileInfo();
                fillByteArrayAndContentType(attachmentUrl,info );
                Attachments a = new Attachments();
                Base64 x = new Base64();
                String imageDataString = x.encodeAsString(info.getFileBytes());
                a.setContent(imageDataString);
                a.setType(info.getContentType());
                a.setFilename(info.getFileName());
                mail.attachments.add(a);
            }
        }
        SendGrid sg = new SendGrid(key);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if(response!=null && response.getStatusCode() !=200)
            {
                logger.error("response not 200:{},subject:{},body:{} with response:{}", emailTo, subject, body, response);
            }
            logger.info("email sent to:{},subject:{},body:{} with response:{}", emailTo, subject, body, response);
        } catch (IOException ex) {
            logger.error("email sent to {}", ex);
        }

       
        return null;
    }

    private void fillByteArrayAndContentType(String urlStr, BasicFileInfo info )
    {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e1) {
            logger.error("Failed while urls:{}", urlStr);
            return;
        }
        InputStream is = null;
        try {       
            is = url.openStream();
           URLConnection cc = url.openConnection();
           info.setContentType(cc.getContentType());  
           info.setFileName(FilenameUtils.getName(url.getPath()));
           info.setFileBytes(IOUtils.toByteArray(is));
        } catch (IOException e) {
            logger.error("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("Failed while urls:{}", urlStr);
                }
            }
        }
     
    }

}
