package com.msgpig.notification.entities.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.msgpig.notification.entities.model.Template;

public interface TemplateRepository extends MongoRepository<Template, Integer> {


}
