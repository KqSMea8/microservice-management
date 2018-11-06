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

package com.open.microservice.web.registry.controller;

import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.InstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用和实例拓扑
 *
 * @author arebya
 * @version 1.0
 * @date 2018/11/6
 **/
@RestController
public class TopoController {

    @Autowired
    private BaseRegistryAdaptor registry;

    @GetMapping(path = "cluster", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTopology(@RequestParam("app") String appName) {

        // 全部实例
        List<InstanceEntity> instances = registry.listInstanceUnderApp(appName);
        // regions分布
        Map<String, Map<String, List<InstanceEntity>>> regions = new HashMap<>();
        for (InstanceEntity instanceEntity : instances) {
            String region = instanceEntity.getMetadata().get("region");
            if (StringUtils.isEmpty(region)) {
                region = "default";
            }
            if (regions.containsKey(region)) {
                Map<String, List<InstanceEntity>> zoneMap = regions.get(region);
                String zone = instanceEntity.getMetadata().get("zone");
                if (StringUtils.isEmpty(zone)) {
                    zone = "default";
                }
                // zone分类
                if (zoneMap.containsKey(zone)) {
                    zoneMap.get(zone).add(instanceEntity);
                } else {
                    List<InstanceEntity> list = new ArrayList<>();
                    list.add(instanceEntity);
                    zoneMap.put(zone, list);
                }

            } else {
                Map<String, List<InstanceEntity>> zoneMap = new HashMap<>();
                List<InstanceEntity> list = new ArrayList<>();
                list.add(instanceEntity);
                String zone = instanceEntity.getMetadata().get("zone");
                if (StringUtils.isEmpty(zone)) {
                    zone = "default";
                }
                zoneMap.put(zone, list);
                regions.put(region, zoneMap);
            }
        }
        return ResponseEntity.ok(regions);
    }


    @GetMapping(path = "group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getGroup(@RequestParam("app") String appName,
                                   @RequestParam("condition") String condition,
                                   @RequestParam(name = "value", required = false) String value) {

        Map<String, List<InstanceEntity>> groups = new HashMap<String, List<InstanceEntity>>();

        // 全部实例
        List<InstanceEntity> instances = registry.listInstanceUnderApp(appName);

        for (InstanceEntity instanceEntity : instances) {
            String conditonValue = instanceEntity.getMetadata().get(condition);
            if (StringUtils.isEmpty(conditonValue)) {
                conditonValue = "default";
            }
            if (!StringUtils.isEmpty(value) && !conditonValue.equalsIgnoreCase(value)) {
                //  not match condition value
                continue;
            }
            if (groups.containsKey(conditonValue)) {
                groups.get(conditonValue).add(instanceEntity);
            } else {
                List<InstanceEntity> list = new ArrayList<>();
                list.add(instanceEntity);
                groups.put(conditonValue, list);

            }

        }


        return ResponseEntity.ok(groups);
    }


}
