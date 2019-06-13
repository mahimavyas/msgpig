package com.msgpig.notification.entities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msgpig.notification.entities.dao.EntityCommunicationConfigRepository;
import com.msgpig.notification.entities.model.EntityCommunicationConfig;
import com.msgpig.notification.entities.service.NextSequenceService;
import com.msgpig.notification.entities.utils.Constants;

@RestController
@RequestMapping("/entitycommunicationconfig")
public class EntityCommunicationConfigController {

    @Autowired
    EntityCommunicationConfigRepository repo;

    @Autowired
    NextSequenceService nextSeq;

    // create
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody EntityCommunicationConfig entityCommunicationConfig) {

        entityCommunicationConfig.setId(nextSeq.getNextSequence(Constants.EntityCommunicationConfig).intValue());
        if(entityCommunicationConfig.getState() ==null)
        {
            entityCommunicationConfig.setState("ALL");
        }
        repo.save(entityCommunicationConfig);
    }

    // read
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public EntityCommunicationConfig read(@PathVariable Integer id) {
        return repo.findOne(id);
    }

    // read all
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<EntityCommunicationConfig> findAll(

            ) {
        return repo.findAll();
    }

    // update
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody EntityCommunicationConfig entityCommunicationConfig) {
        repo.save(entityCommunicationConfig);
    }

    // delete
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        repo.delete(id);
    }

}
