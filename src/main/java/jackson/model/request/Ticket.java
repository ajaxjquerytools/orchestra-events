package jackson.model.request;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

public class Ticket implements Serializable {
   private static final long serialVersionUID = -1302293291237052216L;
   private String clientId;
   private Long serviceId;
   private Long branchId;
   private Long queueId;
   private String ticketNumber;
   private String  visitId;

   private String serviceName;
   private String branchName;
   private String branchAddressLine1;
   private String branchAddressLine2;
   private String branchAddressLine3;
   private String branchAddressLine4;

   public String getVisitId() {
      return this.visitId;
   }

   public void setVisitId(String visitId) {
      this.visitId = visitId;
   }

   public String getClientId() {
      return this.clientId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getTicketNumber() {
      return this.ticketNumber;
   }

   public void setTicketNumber(String ticketNumber) {
      this.ticketNumber = ticketNumber;
   }

   public Long getServiceId() {
      return this.serviceId;
   }

   public void setServiceId(Long serviceId) {
      this.serviceId = serviceId;
   }

   public Long getBranchId() {
      return this.branchId;
   }

   public void setBranchId(Long branchId) {
      this.branchId = branchId;
   }

   public Long getQueueId() {
      return this.queueId;
   }

   public void setQueueId(Long queueId) {
      this.queueId = queueId;
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
}
