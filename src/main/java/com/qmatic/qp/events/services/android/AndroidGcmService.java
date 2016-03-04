package com.qmatic.qp.events.services.android;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.qmatic.qp.core.common.QPEvent;
import com.qmatic.qp.events.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String GCM_SERVER_KEY = "AIzaSyDo4twzffWsWxC2is5y1brCzQes4aBxWAs";
    @Autowired
    private AndroidGcmRegistry gcmRegistry;

    @Override
    public void publishMessage(QPEvent event) {
        boolean calledToWindow = false;
        log.info("====AndroidGcmService:event name={}", event.getEventName());
        if (event.getEventName().equals("VISIT_CALL")) {

            String ticketId = (String)event.getParameters().get("ticket");

            if (!gcmRegistry.contains(ticketId)) {
                return;
            }

            String registeredDestination = gcmRegistry.getToken(ticketId);
            Sender sender = new Sender(GCM_SERVER_KEY);

            Object window = event.getParameters().get("servicePointName");


            String messageToSend = String.format("Please go to '%s', your ticket is %s", window.toString(), ticketId);

            ListenableFuture<Result> future = sender.send(Message.newBuilder()
                    .withDestination(registeredDestination)
                    .withDataPart("message", messageToSend).build());

            Futures.addCallback(future, new FutureCallback<Result>() {
                @Override
                public void onSuccess(Result result) {
                    if (result.isSuccess()) {
                        log.info("Sended successfully messageId {}", result.getMessageId());
                    } else {
                        log.error("Message not sended error {}", result.getError());
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
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
