package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.technician.daptersAndModels;
/*******************************************************/
/* Created by muhammad.sufwan on 4/25/2018 user email ${EMAIL};. */

import java.util.List;

/*******************************************************/
public class JobFormModel {

    private int id,status;
    private String client_id_name, created_at,time_in,time_out,distance;

    public JobFormModel(int id, String client_id_name, String created_at,
                        String time_in, String time_out, String distance, int status) {

        this.id = id;
        this.client_id_name = client_id_name;
        this.created_at = created_at;
        this.time_in = time_in;
        this.time_out = time_out;
        this.distance = distance;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public String getClient_id_name() {
        return client_id_name;
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

    public String getDistance() {
        return distance;
    }
    public int getStatus() {
        return status;
    }

}
