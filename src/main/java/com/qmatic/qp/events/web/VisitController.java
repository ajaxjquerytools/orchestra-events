package com.qmatic.qp.events.web;

import com.qmatic.qp.events.services.android.AndroidGcmRegistry;
import com.qmatic.qp.events.services.visit.VisitService;
import jackson.model.request.Ticket;
import jackson.model.request.VisitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by orchestra on 28.03.2016.
 */
@Controller
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    private AndroidGcmRegistry androidGcmRegistry;

    @Autowired
    private VisitService visitService;

    @RequestMapping(value = "store/{deviceUUID}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void storeVisit(@PathVariable String deviceUUID, @RequestBody VisitRequest visitRequest) {
        if (StringUtils.isEmpty(deviceUUID)) {
            throw new IllegalArgumentException("DeviceUUID can't be null");
        }
        if (visitRequest == null || StringUtils.isEmpty(visitRequest.getVisitId())) {
            throw new IllegalArgumentException("VisitId cant be null");
        }
        visitService.add(deviceUUID, visitRequest);
    }

    @RequestMapping(value = "device/{deviceUUID}", method = RequestMethod.GET)
    public ResponseEntity getCurrentVisit( @PathVariable String deviceUUID) {
        Ticket ticket = visitService.getTicket(deviceUUID);
        return ticket != null ? new ResponseEntity(ticket, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
