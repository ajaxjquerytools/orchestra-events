package jackson.model.request;

import java.io.Serializable;

/**
 * Created by orchestra on 28.03.2016.
 */
public class VisitRequest implements Serializable{

    private static final long serialVersionUID = -1l;

    private String visitId;

    public VisitRequest() {
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
