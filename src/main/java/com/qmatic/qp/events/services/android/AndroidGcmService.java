package com.qmatic.qp.events.services.android;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.qmatic.qp.api.connectors.dto.Visit;
import com.qmatic.qp.core.common.QPEvent;
import com.qmatic.qp.events.services.EventService;
import com.qmatic.qp.events.services.visit.VisitService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.whispersystems.gcm.server.GsmSender;
import org.whispersystems.gcm.server.Message;
import org.whispersystems.gcm.server.Result;
import org.whispersystems.gcm.server.Sender;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * Created by orchestra on 29.02.2016.
 */
@Service
@Qualifier("androidGcm")
public class AndroidGcmService implements EventService {

    private static final Logger log = LoggerFactory.getLogger(AndroidGcmRegistry.class);


    @Value("${qmatic.android.serverKey}")
    private String gcmServerKey;

    @Value("${qmatic.push.message}")
    private String pushMessageTemplate;


    @Autowired
    private AndroidGcmRegistry gcmRegistry;

    @Autowired
    private VisitService visitService;

    private String encodedMessage;

    @Override
    public void publishMessage(QPEvent event) {
        log.info("====AndroidGcmService:event name={}", event.getEventName());
        if (event.getEventName().equals("VISIT_CALL")) {

            Long visitId = (Long) event.getParameters().get("visitId");
            String deviceUUID = visitService.getDeviceUUID(visitId.toString());

            if (!gcmRegistry.contains(deviceUUID)) {
                return;
            }

            String registeredDestination = gcmRegistry.getToken(deviceUUID);
            GsmSender sender = new GsmSender(gcmServerKey);

            String ticketId = (String) event.getParameters().get("ticket");
            String window = (String) event.getParameters().get("servicePointName");

            String encodedMessageToSend = getEncodedMessage(window, ticketId);

            ListenableFuture<Result> future = sender.send(Message.newBuilder()
                    .withDestination(registeredDestination)
                    .withDataPart("message", encodedMessageToSend).build());

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

    public String getEncodedMessage(String window, String ticketId) {
        String encodedMessageToSend = "Please go to window";
        try {
            String msgTpl = StringEscapeUtils.unescapeJava(pushMessageTemplate);
            String messageToSend = String.format(msgTpl, window, ticketId);
            encodedMessageToSend = java.net.URLEncoder.encode(messageToSend, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return encodedMessageToSend;
    }
}
