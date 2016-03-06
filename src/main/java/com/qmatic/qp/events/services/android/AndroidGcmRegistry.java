/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.qmatic.qp.events.services.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple singleton registry to keep a list of registered endpoints.
 * 
 * @author gavsmi
 *
 */
@Component
@Scope("singleton")
public class AndroidGcmRegistry {

	private static final Logger log = LoggerFactory.getLogger(AndroidGcmRegistry.class);
			
	private Map<String, String> ticketToToken = new ConcurrentHashMap<String, String>();
	
	public void register(String visitId, String token) {
		if(!ticketToToken.containsKey(visitId)) {
			log.debug("Adding user {} with token {}", visitId, token);
			ticketToToken.put(visitId, token);
		} else {
			log.debug("Received registration for already registered endpoint {}", visitId);
		}
	}

	public boolean contains(String ticketId) {
		return ticketToToken.containsKey(ticketId);
	}

	public void unregister(String userId) {
		ticketToToken.remove(userId);
	}

    public String getToken(String ticketId) {
        return ticketToToken.get(ticketId);
    }
}
