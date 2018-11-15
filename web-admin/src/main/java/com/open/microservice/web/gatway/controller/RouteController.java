/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.open.microservice.web.gatway.controller;

import com.open.microservice.gateway.basic.BaseGateWayAdaptor;
import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.InstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arebya
 * @version 1.0
 * @date 2018/11/15
 **/
@RestController
public class RouteController {

    @Autowired
    private BaseRegistryAdaptor registryAdaptor;

    @Autowired
    private BaseGateWayAdaptor baseGateWayAdaptor;

    @Value("${gateway.service.name}")
    private String gateWayServiceName;


    @GetMapping(path = "/routes/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> routesList() {

        List<InstanceEntity> instanceEntityList = registryAdaptor.listInstanceUnderApp(gateWayServiceName);

        Map<String, String> routes = baseGateWayAdaptor.getRoutes(instanceEntityList.get(0).getInstanceId());

        return ResponseEntity.ok(routes);
    }


    @PostMapping(path = "/routes/refresh", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> routesRefresh() {

        List<InstanceEntity> instanceEntityList = registryAdaptor.listInstanceUnderApp(gateWayServiceName);
        Map<String, String> statusMap = new HashMap<>();
        for (InstanceEntity entity : instanceEntityList) {
            String result = baseGateWayAdaptor.refreshRoutes(entity.getInstanceId());
            statusMap.put(entity.getInstanceId(), result);
        }

        return ResponseEntity.ok(statusMap);
    }
}
