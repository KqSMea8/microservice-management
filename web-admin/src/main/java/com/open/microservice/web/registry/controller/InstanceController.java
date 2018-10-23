package com.open.microservice.web.registry.controller;

import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.InstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * instance management controller
 *
 * @author arebya
 */
@RestController
public class InstanceController {

    @Autowired
    private BaseRegistryAdaptor registry;

    @GetMapping(path = "instances/getByApp", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getInstancesByAppName(@RequestParam(name = "app") String appName) {
        if (StringUtils.isEmpty(appName)) {
            return ResponseEntity.badRequest().build();
        }

        List<InstanceEntity> instances = registry.listInstanceUnderApp(appName);

        return ResponseEntity.ok(instances);

    }

    @PostMapping(path = "instances/offline", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity offlineInstance(
            @RequestParam(name = "app") String appName, @RequestParam(name = "instanceId") String instanceId) {
        return ResponseEntity.ok(registry.updateInstanceStatus(appName, instanceId, "OUT_OF_SERVICE"));
    }
}
