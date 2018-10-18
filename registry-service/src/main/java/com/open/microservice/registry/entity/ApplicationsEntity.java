package com.open.microservice.registry.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author arebya
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationsEntity implements Serializable {

    private List<ApplicationEntity> application;


    public List<ApplicationEntity> getApplication() {
        return application;
    }

    public void setApplication(List<ApplicationEntity> application) {
        this.application = application;
    }


}
