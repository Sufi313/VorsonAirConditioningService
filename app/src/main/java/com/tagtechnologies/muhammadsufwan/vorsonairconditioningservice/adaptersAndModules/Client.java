package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

/**
 * Created by muhammad.sufwan on 11/22/2017.
 */

public class Client {


    private int id, status;
    private String name, email, number,address;

    public Client(int id, String name, String email, String number, String address, int status) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.address = address;
        this.status = status;

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

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public int getStatus() {
        return status;
    }
}
