package com.qmatic.qp.events.services.android;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.qmatic.qp.core.common.QPEvent;
import com.qmatic.qp.events.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.whispersystems.gcm.server.Message;
import org.whispersystems.gcm.server.Result;
import org.whispersystems.gcm.server.Sender;


/**
 * Created by orchestra on 29.02.2016.
 */
@Service
@Qualifier("androidGcm")
public class AndroidGcmService implements EventService {

    private static final Logger log = LoggerFactory.getLogger(AndroidGcmRegistry.class);

    @Override
    public void publishMessage(QPEvent event) {
        //publishing to all devices; todo: fix in future;
        boolean calledToWindow = false;
        //TODO: Refactor this

        log.info("====AndroidGcmService:event name={}",event.getEventName());
        if (event.getEventName().equals("VISIT_CALL")) {

            String apiKey = "AIzaSyDo4twzffWsWxC2is5y1brCzQes4aBxWAs";
            String registeredDestination = "APA91bHGkL_KLP-h4EAeOZyRZ66Qu7zeQlXPv-Nnn5399Vm94781OcVMIfIJX-Bcjse0FTuQF7bVVMoH4-vIMcFPkSzMw1ySoL7hqqF-lN5uRwe-oP0K7j6-iX4_6omQmRiBSTmfs8zd";
            Sender sender = new Sender(apiKey);

            Object window = event.getParameters().get("servicePointName");
            Object ticket = event.getParameters().get("ticket");

            String messageToSend = String.format("Please go to '%s', your ticket is %s", window.toString(), ticket.toString());

            //String messageTemplate = new String(java.nio.charset.Charset.forName("UTF-8").encode(messageToSend).array(),java.nio.charset.Charset.forName("ISO-8859-1"));

            ListenableFuture<Result> future = sender.send(Message.newBuilder()
                    .withDestination(registeredDestination)
                    .withDataPart("message", messageToSend).build());

            Futures.addCallback(future, new FutureCallback<Result>() {
                @Override
                public void onSuccess(Result result) {
                    if (result.isSuccess()) {
                        log.info("Sended successfully messageId {}", result.getMessageId());
                        // Maybe do something with result.getMessageId()
                    } else {
                        log.error("Message not sended error {}", result.getError());
                        // Maybe do something with result.getError(), or check result.isUnregistered, etc..
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    // Handle network failure or server 500
                    log.error("Message not sended: " + throwable.getMessage());
                }
            });
        }
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String serviceName() {
        return "androidGcmService";
    }
}
