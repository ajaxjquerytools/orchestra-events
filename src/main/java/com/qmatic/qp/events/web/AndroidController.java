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
        androidGcmRegistry.register(androidRegister.getUser(), androidRegister.getToken());
    }

    @RequestMapping(value = "isRegister/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity isRegister(@PathVariable String userId) {
        boolean contains = androidGcmRegistry.contains(userId);
        return contains ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "users/unregister/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void deregister(@PathVariable String userId) {
        androidGcmRegistry.unregister(userId);
    }
}
