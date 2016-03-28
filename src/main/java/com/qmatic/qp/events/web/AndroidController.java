package com.qmatic.qp.events.web;

import com.qmatic.qp.events.services.android.AndroidGcmRegistry;
import jackson.model.request.AndroidRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by orchestra on 01.03.2016.
 */
@Controller
@RequestMapping("/android")
public class AndroidController {
    @Autowired
    private AndroidGcmRegistry androidGcmRegistry;

    @RequestMapping(value = "user/register", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody AndroidRegister androidRegister) {
        androidGcmRegistry.register(androidRegister.getTicketId(), androidRegister.getToken());
    }

    @RequestMapping(value = "token/{deviceUUID}", method = RequestMethod.GET)
    public ResponseEntity getToken(@PathVariable String deviceUUID) {
        String token = androidGcmRegistry.getToken(deviceUUID);
        return token == null ? new ResponseEntity(token, HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "users/unregister/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void deregister(@PathVariable String userId) {
        androidGcmRegistry.unregister(userId);
    }
}
