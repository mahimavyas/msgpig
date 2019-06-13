package com.msgpig.notification.entities.service;

import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.model.Template;

@Service
public interface TemplateService
{
    Template getTemplateById(Integer template);
    
    String fillTemplate(String templateStr, Object payload);
}
