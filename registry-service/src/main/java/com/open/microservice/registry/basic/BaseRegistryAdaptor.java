package com.open.microservice.registry.basic;

import com.open.microservice.registry.entity.ApplicationEntity;

import java.util.List;

/**
 * @authgr arebya
 */
public interface BaseRegistryAdaptor {
    /**
     * list applications
     *
     * @return
     */
    List<ApplicationEntity> listApplications();
}
