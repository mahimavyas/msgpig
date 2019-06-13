package com.msgpig.notification.entities.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.msgpig.notification.entities.model.EntityCommunicationConfig;

public interface EntityCommunicationConfigRepository extends MongoRepository<EntityCommunicationConfig, Integer> {

    @Query(value = "{'entity':?0, 'action':?1 ,'state': ?2}")
    EntityCommunicationConfig findEntityCommunicationConfigById(String entity, String action, String state);
}
