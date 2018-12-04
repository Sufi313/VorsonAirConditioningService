package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

/*****************************************************************/
/* Created by muhammad.sufwan on 3/30/2018 user email ${EMAIL};. */
/*****************************************************************/

public class HistoryPaidItemInvoiceModule {

    private int id, invoice_id;
    private String item_code, description, location, quantity, rate, amount;

    public HistoryPaidItemInvoiceModule(int id, int invoice_id, String item_code, String description,
                                        String location, String quantity, String rate, String amount) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.item_code = item_code;
        this.description = description;
        this.location = location;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRate() {
        return rate;
    }

    public String getAmount() {
        return amount;
    }
}
