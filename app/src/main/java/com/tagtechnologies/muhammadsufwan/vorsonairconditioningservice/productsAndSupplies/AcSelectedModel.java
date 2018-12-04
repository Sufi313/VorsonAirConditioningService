package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.productsAndSupplies;

public class AcSelectedModel {

    int id;
    String model,quantity,fileName,encodedFile;

    public AcSelectedModel(int id, String model, String quantity, String fileName, String encodedFile) {
        this.id = id;
        this.model = model;
        this.quantity = quantity;
        this.fileName = fileName;
        this.encodedFile = encodedFile;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getFileName() {
        return fileName;
    }

    public String getEncodedFile() {
        return encodedFile;
    }
}
