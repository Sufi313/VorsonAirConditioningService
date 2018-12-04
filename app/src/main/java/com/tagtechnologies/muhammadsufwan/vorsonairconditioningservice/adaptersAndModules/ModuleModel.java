package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

/*******************************************************/
/* Created by Muhammad.Sufwan on 2/21/2018 user email; ${EMAIL}. */
/*******************************************************/

public class ModuleModel {

    int mid, brand_id, color_id, weight_id;
    String model, image;

    public ModuleModel(int mid, int brand_id, int color_id, int weight_id, String model, String image) {
        this.mid = mid;
        this.brand_id= brand_id;
        this.color_id= color_id;
        this.weight_id= weight_id;
        this.model = model;
        this.image = image;
    }

    public int getMid() {
        return this.mid;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public int getColor_id() {
        return color_id;
    }

    public int getWeight_id() {
        return weight_id;
    }

    public String getModel() {
        return model;
    }

    public String getImage() {
        return image;
    }
}
