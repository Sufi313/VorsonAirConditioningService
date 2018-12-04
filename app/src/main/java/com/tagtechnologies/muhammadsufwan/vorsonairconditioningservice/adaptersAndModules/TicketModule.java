package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;
/*******************************************************/
/* Created by muhammad.sufwan on 6/7/2018 user email ${EMAIL};. */

/*******************************************************/
public class TicketModule {

    private int id, invoice_id,status;
    private String help, message,created_at,task_ended_at,tech_name,tech_number;

    public TicketModule(int id, int invoice_id,
                        int status, String help, String message, String created_at, String task_ended_at, String tech_name,String tech_number) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.status = status;
        this.help = help;
        this.message = message;
        this.created_at = created_at;
        this.task_ended_at = task_ended_at;
        this.tech_name = tech_name;
        this.tech_number = tech_number;
    }

    public int getId() {
        return id;
    }


    public int getInvoice_id() {
        return invoice_id;
    }

    public int getStatus() {
        return status;
    }

    public String getHelp() {
        return help;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getTask_ended_at() {
        return task_ended_at;
    }

    public String getTech_name() {
        return tech_name;
    }

    public String getTech_number() {
        return tech_number;
    }


}
