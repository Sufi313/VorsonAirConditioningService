package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by Muhammad.Sufwan on 5/8/2018 user email ${EMAIL};. */

/*******************************************************/
public class AssignJobModule {

    private int id, complaint_id,complaintNumber,status;
    private String clientName, serviceType, servicePriority, phoneNumber, message, address;

    public AssignJobModule(int id, int complaintNumber, String clientName,
                           String serviceType, String servicePriority, String phoneNumber, String message, String address, int status) {
        this.id = id;
        this.complaintNumber = complaintNumber;
        this.clientName = clientName;
        this.serviceType = serviceType;
        this.servicePriority = servicePriority;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getComplaintNumber() {
        return complaintNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServicePriority() {
        return servicePriority;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public int getStatus(){
        return status;
    }
}
