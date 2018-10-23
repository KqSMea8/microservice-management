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

    /**
     * get instances list by appname
     *
     * @param appName
     * @return
     */
    @GetMapping(path = "instance/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getInstancesByAppName(@RequestParam(name = "app") String appName) {
        if (StringUtils.isEmpty(appName)) {
            return ResponseEntity.badRequest().build();
        }

        List<InstanceEntity> instances = registry.listInstanceUnderApp(appName);

        return ResponseEntity.ok(instances);

    }

    /**
     * offline the instance
     *
     * @param appName
     * @param instanceId
     * @return
     */
    @PostMapping(path = "instance/offline", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity offlineInstance(
            @RequestParam(name = "app") String appName, @RequestParam(name = "instanceId") String instanceId) {
        return ResponseEntity.ok(registry.offlineInstance(appName, instanceId, "OUT_OF_SERVICE"));
    }

    /**
     * move the instance back to service
     *
     * @param appName
     * @param instanceId
     * @return
     */
    @PostMapping(path = "instance/online", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity onlineInstance(
            @RequestParam(name = "app") String appName, @RequestParam(name = "instanceId") String instanceId) {
        return ResponseEntity.ok(registry.upInstance(appName, instanceId, "UP"));
    }


    /**
     * 更新instance的meta
     *
     * @param appName
     * @param instanceId
     * @return
     */
    @PostMapping(path = "instance/metadata", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateInstanceMeta(@RequestParam(name = "app") String appName,
                                             @RequestParam(name = "instanceId") String instanceId,
                                             @RequestParam(name = "key") String key,
                                             @RequestParam(name = "value") String value) {
        return ResponseEntity.ok(registry.updateInstanceMeta(appName, instanceId, key, value));
    }

}
