package com.msgpig.notification.entities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.msgpig.notification.entities.model.Sequence;

@Service
public class NextSequenceService {

    @Autowired
    private MongoOperations mongo;

    public Long getNextSequence(String seqName)
    {
        Sequence seq = mongo.findAndModify(new Query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                new FindAndModifyOptions().returnNew(true),
                Sequence.class);

        return seq.getSeq();
    }
}
