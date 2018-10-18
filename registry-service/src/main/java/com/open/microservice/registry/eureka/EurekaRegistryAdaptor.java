package com.open.microservice.registry.eureka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.microservice.registry.basic.BaseRegistryAdaptor;
import com.open.microservice.registry.entity.ApplicationEntity;
import com.open.microservice.registry.entity.ApplicationsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
    public List<ApplicationEntity> listApplications() {
        List<ApplicationEntity> list = new ArrayList<>();

        ResponseEntity<String> response = restTemplate.exchange(
                eurekaServer + "/eureka/apps", HttpMethod.GET, null, String.class);
        try {
            JsonNode applications = MAPPER.readTree(response.getBody());

            JsonNode node = applications.get("applications");

            ApplicationsEntity applicationsEntity = MAPPER.readValue(node.toString(), ApplicationsEntity.class);
            list = applicationsEntity.getApplication();
        } catch (Exception e) {
            LOGGER.error("read result form eureka server error", e);
        }

        return list;
    }
}
