package com.open.microservice.web.registry.controller;

import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.ApplicationEntity;
import net.thisptr.jackson.jq.internal.misc.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * application controller
 *
 * @author arebya
 */
@RestController
public class ApplicationController {

    @Autowired
    private BaseRegistryAdaptor registry;


    @GetMapping(path = "/apps", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ApplicationEntity>> appList() {
        return ResponseEntity.ok().body(registry.listApplications());
    }
}
