package com.sdp.cop.octopus.entity;

import java.io.Serializable;

/**
 * mess_template
 * @author 
 */
public class MessTemplate implements Serializable {
    private Long id;

    private String name;

    private String titOrSub;

    private String content;

    private String type;

    private String multKeys;

    private String messspot;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitOrSub() {
        return titOrSub;
    }

    public void setTitOrSub(String titOrSub) {
        this.titOrSub = titOrSub;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMultKeys() {
        return multKeys;
    }

    public void setMultKeys(String multKeys) {
        this.multKeys = multKeys;
    }

    public String getMessspot() {
        return messspot;
    }

    public void setMessspot(String messspot) {
        this.messspot = messspot;
    }
}