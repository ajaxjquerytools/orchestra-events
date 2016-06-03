package jackson.model.request;

import java.io.Serializable;

/**
 * Created by orchestra on 28.03.2016.
 */
public class VisitRequest implements Serializable {

    private static final long serialVersionUID = -1l;

    private String visitId;
    private Long branchId;
    private Long queueId;
    private Long serviceId;
    private String clientId;
    private String serviceName;
    private String branchName;
    private String branchAddressLine1;
    private String branchAddressLine2;
    private String branchAddressLine3;
    private String branchAddressLine4;


    public VisitRequest() {
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddressLine1() {
        return branchAddressLine1;
    }

    public void setBranchAddressLine1(String branchAddressLine1) {
        this.branchAddressLine1 = branchAddressLine1;
    }

    public String getBranchAddressLine2() {
        return branchAddressLine2;
    }

    public void setBranchAddressLine2(String branchAddressLine2) {
        this.branchAddressLine2 = branchAddressLine2;
    }

    public String getBranchAddressLine3() {
        return branchAddressLine3;
    }

    public void setBranchAddressLine3(String branchAddressLine3) {
        this.branchAddressLine3 = branchAddressLine3;
    }

    public String getBranchAddressLine4() {
        return branchAddressLine4;
    }

    public void setBranchAddressLine4(String branchAddressLine4) {
        this.branchAddressLine4 = branchAddressLine4;
    }

    @Override
    public String toString() {
        return "VisitRequest{" +
                "visitId='" + visitId + '\'' +
                ", branchId=" + branchId +
                ", queueId=" + queueId +
                ", serviceId=" + serviceId +
                ", clientId='" + clientId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchAddressLine1='" + branchAddressLine1 + '\'' +
                ", branchAddressLine2='" + branchAddressLine2 + '\'' +
                ", branchAddressLine3='" + branchAddressLine3 + '\'' +
                ", branchAddressLine4='" + branchAddressLine4 + '\'' +
                '}';
    }
}
