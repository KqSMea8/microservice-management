package com.open.microservice.registry.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author arebya
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationEntity implements Serializable {

    private String name;

    private List<InstanceEntity> instance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InstanceEntity> getInstance() {
        return instance;
    }

    public void setInstance(List<InstanceEntity> instance) {
        this.instance = instance;
    }
}
