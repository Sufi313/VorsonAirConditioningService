package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;
/*******************************************************/
/* Created by muhammad.sufwan on 4/17/2018 user email ${EMAIL};. */

/*******************************************************/
public class TechnicianModule {

    int id;
    String name,email,address,mobile,dob;

    public TechnicianModule(int id, String name, String email,String address,String mobile,String dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDob() {
        return dob;
    }
}
