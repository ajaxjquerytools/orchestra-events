package jackson.model.request;

import java.io.Serializable;

/**
 * Created by orchestra on 01.03.2016.
 */
public class AndroidRegister implements Serializable{
    private static final long serialVersionUID = -1l;

    private String ticketId;
    private String type;
    private String token;

    public AndroidRegister(){

    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
