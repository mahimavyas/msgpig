package com.msgpig.notification.entities.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.dao.TemplateRepository;
import com.msgpig.notification.entities.model.Template;
import com.msgpig.notification.entities.service.TemplateService;
import com.msgpig.notification.entities.utils.HandlebarsHelper;

@Service
public class TemplateServiceImpl implements TemplateService
{

    @Autowired
    TemplateRepository repo;

    @Override
    public Template getTemplateById(Integer templateId) {

        return repo.findOne(templateId);
    }

    @Override
    public String fillTemplate(String templateStr, Object payload) {
        return HandlebarsHelper.replaceTemplate(templateStr, payload);
    }

}
