package com.qmatic.qp.events.services.visit;

import com.qmatic.qp.api.connectors.dto.Visit;
import com.qmatic.qp.rest.QpCentralRestTemplateFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by orchestra on 28.03.2016.
 */
@Component
@Scope("singleton")
public class QmaticRestClientService {
    private static final Logger log = LoggerFactory.getLogger(QmaticRestClientService.class);

    @Value("${qmatic.core.http.prefix}")
    private String httpPrefix;

    @Value("${qmatic.core.user}")
    private String user;

    @Value("${qmatic.core.password}")
    private String password;

    @Value("${qmatic.core.host}")
    private String host;

    @Value("${qmatic.core.port}")
    private int port;

    @Value("${qmatic.core.servicepoint}")
    private String SERVICE_POINT_PATH;

    private RestTemplate restTemplate;

    @PostConstruct
    public void initRestTemplate() {
        restTemplate = QpCentralRestTemplateFactory.createRestTemplate(user, password, host, port);
    }

    public Visit getVisit(String branchId, String visitId) {
        final String GET_VISIT_RESOURCE = "/branches/{branchId}/visits/{visitId}";
        final String GET_VISIT_FULL_PATH = getServicePointPath() + GET_VISIT_RESOURCE;
        try {
            log.info("Getting visit info from " + GET_VISIT_FULL_PATH + "with branchId=" + branchId + "and visitId=" + visitId);
            ResponseEntity<Visit> responseEntity = restTemplate.getForEntity(GET_VISIT_FULL_PATH, Visit.class, branchId, visitId);
            //TODO check response correct
            return responseEntity.getBody();
        } catch (Exception ex) {
            //todo: che
            //TODO: handle exception correct;
            log.error(ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    public String getServicePointPath() {
        return httpPrefix + "://" + host + ":" + port + SERVICE_POINT_PATH;
    }
}
