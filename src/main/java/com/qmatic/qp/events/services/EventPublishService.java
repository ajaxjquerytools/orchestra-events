/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.qmatic.qp.events.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.qmatic.qp.core.common.QPEvent;

/**
 * EventService, publishes event messages to enabled services.
 *
 * @author gavsmi
 */
@Service
public class EventPublishService {

    private static final Logger log = LoggerFactory.getLogger(EventPublishService.class);

    @Autowired
    @Qualifier("webhook")
    private EventService webhookService;

    @Autowired
    @Qualifier("ws")
    private EventService websocketService;

    @Autowired
    @Qualifier("androidGcm")
    private EventService androidGcmService;

    @Async
    public void publishMessage(QPEvent event) throws Exception {
        // Publish message to each enabled service
        publishMessageToService(event, webhookService);
        publishMessageToService(event, websocketService);
        publishMessageToService(event, androidGcmService);
    }

    private void publishMessageToService(QPEvent event, EventService eventService) {
        try {
            if (webhookService.isEnabled()) {
                log.info("{} enabled, publishing...", eventService.serviceName());
                eventService.publishMessage(event);
            }
        } catch (Exception ex) {
            log.error("Cant publish message to service {}", eventService.serviceName());
        }
    }
}
