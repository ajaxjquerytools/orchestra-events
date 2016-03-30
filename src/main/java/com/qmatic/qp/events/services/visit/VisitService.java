package com.qmatic.qp.events.services.visit;

import com.qmatic.qp.api.connectors.dto.Visit;
import jackson.model.request.Ticket;
import jackson.model.request.VisitRequest;
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

    private Map<String, VisitRequest> deviceToVisitId = new ConcurrentHashMap<String, VisitRequest>();
    private Map<String, String> visitToDevice = new ConcurrentHashMap<String, String>();

    public void add(String deviceUUID, VisitRequest visitRequest) {
        deviceToVisitId.put(deviceUUID, visitRequest);
        visitToDevice.put(visitRequest.getVisitId(), deviceUUID);
    }

    public Ticket getTicket(String deviceUUID) {
        VisitRequest storedVisit = deviceToVisitId.get(deviceUUID);
        if (storedVisit == null) {
            return null;
        }
        Visit visit = qmaticRestClient.getVisit(storedVisit.getBranchId().toString(), storedVisit.getVisitId());
        //TODO check status
        if (visit == null) {
            //NO Visit for this device; or visit expired or else...
            cleanState(deviceUUID,  storedVisit.getVisitId());
            return null;
        }
        // visit still waiting for future serving
        return createTicket(visit, storedVisit);
    }

    public String getDeviceUUID(String visitId) {
        return visitToDevice.get(visitId);
    }

    private void cleanState(String deviceUUID, String visitId) {
        deviceToVisitId.remove(deviceUUID);
        visitToDevice.remove(visitId);
    }

    protected Ticket createTicket(Visit visit, VisitRequest visitRequest){
            Ticket ticket = new Ticket();
            ticket.setTicketNumber(visit.getTicketId());

            ticket.setBranchId(visitRequest.getBranchId());
            ticket.setClientId(visitRequest.getClientId());
            ticket.setServiceId(visitRequest.getServiceId());
            ticket.setQueueId(visitRequest.getQueueId());
            ticket.setVisitId(visitRequest.getVisitId());

            ticket.setServiceName(visitRequest.getServiceName());
            ticket.setBranchName(visitRequest.getServiceName());

            ticket.setBranchAddressLine1(visitRequest.getBranchAddressLine1());
            ticket.setBranchAddressLine2(visitRequest.getBranchAddressLine2());
            ticket.setBranchAddressLine3(visitRequest.getBranchAddressLine3());
            ticket.setBranchAddressLine4(visitRequest.getBranchAddressLine4());
            return ticket;
    }
}
