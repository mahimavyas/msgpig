package com.msgpig.notification.entities.model;

import org.springframework.data.annotation.Id;

public class Sequence {
    private static final long serialVersionUID = -8158045646647244282L;
    @Id
    private String _id;
    private Long seq;

    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = id;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
