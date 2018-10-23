package com.open.microservice.registry.eureka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.ApplicationEntity;
import com.open.microservice.registry.entity.ApplicationsEntity;
import com.open.microservice.registry.entity.InstanceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arebya
 */
@Component
public class EurekaRegistryAdaptor implements BaseRegistryAdaptor {

    private final Logger LOGGER = LoggerFactory.getLogger(EurekaRegistryAdaptor.class);

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Value("${registry.eureka.server}")
    private String eurekaServer;
    @Value("${registry.eureka.auth.username}")
    private String username;
    @Value("${registry.eureka.auth.password}")
    private String password;


    @PostConstruct
    public void init() {
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(username, password));
    }

    /**
     * register application list
     *
     * @return
     */
    @Override
    public List<ApplicationEntity> listApplications() {
        List<ApplicationEntity> list = new ArrayList<>();

        ResponseEntity<String> response = restTemplate.exchange(
                eurekaServer + "/apps", HttpMethod.GET, null, String.class);
        try {
            JsonNode applications = MAPPER.readTree(response.getBody());

            JsonNode node = applications.get("applications");

            ApplicationsEntity applicationsEntity = MAPPER.readValue(node.toString(), ApplicationsEntity.class);
            list = applicationsEntity.getApplication();
        } catch (Exception e) {
            LOGGER.error("read result form eureka server error,url:[/eureka/apps]", e);
        }

        return list;
    }


    @Override
    public List<InstanceEntity> listInstanceUnderApp(String appName) {

        List<InstanceEntity> instances = new ArrayList<>();
        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(eurekaServer + "/apps/" + appName, HttpMethod.GET,
                            null, String.class);
            JsonNode application = MAPPER.readTree(response.getBody());
            JsonNode node = application.get("application");
            ApplicationEntity applicationEntity = MAPPER.readValue(node.toString(), ApplicationEntity.class);
            instances = applicationEntity.getInstance();
        } catch (Exception e) {
            LOGGER.error("read result form eureka server error,url:[/eureka/apps/" + appName + "]", e);
        }
        return instances;
    }


    @Override
    public String offlineInstance(String appName, String instanceId, String status) {
        ResponseEntity<String> response = updateRequestOperation(appName, instanceId, status, HttpMethod.PUT);
        // body==null
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return "offline success";
        }
        return response.getBody();

    }


    @Override
    public String upInstance(String appName, String instanceId, String status) {

        ResponseEntity<String> response = updateRequestOperation(appName, instanceId, status, HttpMethod.DELETE);
        // body==null
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return "back to service success";
        }
        return response.getBody();
    }


    @Override
    public String updateInstanceMeta(String appName, String instanceId, String key, String value) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    eurekaServer + "/apps/" + appName + "/" + instanceId + "/metadata?" + key + "=" + value,
                    HttpMethod.PUT, null, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return "update metadata success";
            }
        } catch (Exception e) {
            LOGGER.error("read result form eureka server error,url:[/apps/"
                    + appName + "/" + instanceId + "/metadata?" + key + "=" + value, e);
        }
        return "update metadata fail";
    }

    private ResponseEntity<String> updateRequestOperation(String appName, String instanceId,
                                                          String status, HttpMethod method) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    eurekaServer + "/apps/" + appName + "/" + instanceId + "/status?value=" + status,
                    method, null, String.class);
            LOGGER.info("update app:{} instance:{} status response:{}", appName, instanceId, status);
            return response;
        } catch (Exception e) {
            LOGGER.error("read result form eureka server error,url:[/eureka/apps/" + appName + "/"
                    + instanceId + "/status?value=" + status + "]", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("request error");
        }
    }
}
