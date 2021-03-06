package com.open.microservice.registry.basic;

import com.open.microservice.registry.entity.ApplicationEntity;
import com.open.microservice.registry.entity.InstanceEntity;

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


    List<InstanceEntity> listInstanceUnderApp(String appName);


    String offlineInstance(String appName, String instanceId, String status);


    String upInstance(String appName, String instanceId, String status);


    String updateInstanceMeta(String appName, String instanceId, String key, String value);
}
