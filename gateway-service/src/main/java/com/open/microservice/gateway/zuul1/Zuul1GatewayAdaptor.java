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

package com.open.microservice.gateway.zuul1;

import com.open.microservice.gateway.basic.BaseGateWayAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author arebya
 * @version 1.0
 * @date 2018/11/15
 **/
@Component
public class Zuul1GatewayAdaptor implements BaseGateWayAdaptor {

    private final Logger LOGGER = LoggerFactory.getLogger(Zuul1GatewayAdaptor.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Map<String, String> getRoutes(String server) {
        Map<String, String> routes = new HashMap<>();

        if (StringUtils.isEmpty(server)) {
            return routes;
        }
        try {
            ResponseEntity<HashMap> result = restTemplate.getForEntity(
                    verifyServer(server) + "/admin/routes", HashMap.class);

            routes = result.getBody();
        } catch (Exception e) {
            LOGGER.error("get routes error ", e);
        }
        return routes;
    }

    @Override
    public String refreshRoutes(String server) {
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(
                    verifyServer(server) + "/admin/refresh", null, String.class);
            LOGGER.info("refresh result,code:{},response:{}", result.getStatusCode(), result.getBody());
            return result.getBody();
        } catch (Exception e) {
            LOGGER.error("refresh routes error ", e);
        }
        return "refresh occur exception";
    }


    private String verifyServer(String server) {
        if (!server.startsWith("http://")) {
            server = "http://" + server;
        }

        return server;
    }
}
