package com.qmatic.qp.events.services.visit;

import com.qmatic.qp.api.connectors.dto.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by orchestra on 28.03.2016.
 */
@Component
@Scope("singleton")
public class VisitService {

    @Autowired
    private QmaticRestClientService qmaticRestClient;

    private Map<String, String> deviceToVisitId = new ConcurrentHashMap<String, String>();
    private Map<String, String> visitToDevice = new ConcurrentHashMap<String, String>();

    public void add(String deviceUUID, String visitId) {
        deviceToVisitId.put(deviceUUID, visitId);
        visitToDevice.put(visitId, deviceUUID);
    }

    public Visit getVisit(String branchId, String deviceUUID){
        String visitId = deviceToVisitId.get(deviceUUID);
        if(visitId == null){
            return null;
        }
        Visit visit = qmaticRestClient.getVisit(branchId, visitId);
        //TODO check status
        if(visit == null){
            //NO Visit for this device; or visit expired or else...
            cleanState(deviceUUID, visitId);
            return null;
        }
        // visit still waiting for future serving
        return visit;
    }

    public String getDeviceUUID(String visitId){
        return visitToDevice.get(visitId);
    }

    private void cleanState(String deviceUUID, String visitId){
        deviceToVisitId.remove(deviceUUID);
        visitToDevice.remove(visitId);
    }

}
