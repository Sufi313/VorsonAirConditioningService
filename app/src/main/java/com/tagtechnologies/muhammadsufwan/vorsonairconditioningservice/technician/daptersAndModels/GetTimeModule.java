package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by muhammad.sufwan on 5/22/2018 user email ${EMAIL};. */

/*******************************************************/
public class GetTimeModule {

    private int id;
    private String clientName, created_at, time_in, time_out, working_time;

    public GetTimeModule(int id, String clientName,
                         String created_at, String time_in, String time_out, String working_time) {
        this.id = id;
        this.clientName = clientName;
        this.created_at = created_at;
        this.time_in = time_in;
        this.time_out = time_out;
        this.working_time = working_time;
    }

    public int getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getTime_in() {
        return time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public String getWorking_time() {
        return working_time;
    }

}
