package com.msgpig.notification.entities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msgpig.notification.entities.dao.TemplateRepository;
import com.msgpig.notification.entities.model.Template;
import com.msgpig.notification.entities.service.NextSequenceService;
import com.msgpig.notification.entities.utils.Constants;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    NextSequenceService nextSeq;

    // create
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Template template) {

        template.setId(nextSeq.getNextSequence(Constants.TEMPLATE).intValue());
        templateRepository.save(template);
    }

    // read
    @RequestMapping(value = "/{id}")
    public Template read(@PathVariable Integer id) {
        return templateRepository.findOne(id);
    }

    // read all
    @RequestMapping(value = "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Template> findAll(

            ) {
        return templateRepository.findAll();
    }

    // update
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Template template) {
        templateRepository.save(template);
    }

    // delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        templateRepository.delete(id);
    }

}
