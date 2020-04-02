package com.windsockui.datastore.entities;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class JsonData {

    @Id
    @GeneratedValue
    UUID id;

    String domain;

    String path;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    String json;

    /** Getters and Setters **/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonRawValue
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
