package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

public class ModelNewAC {

    private int id;
    private String name,image;

    public ModelNewAC(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModelNewAC(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
