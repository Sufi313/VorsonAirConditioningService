package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*                                 Created by muhammad.sufwan on 3/28/2018                                   */
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


import java.util.List;

public class HistoryPaidModule {

    private int id, dn_no, status;
    private String invoice_type, sntn_no, stran_no, invoice_no, created_at, name_address, branch_address;
    private float total, subtotal,sales_tax,advance,balance_due;

    private List<HistoryPaidItemInvoiceModule> invoiceItem;

    public HistoryPaidModule(int id, int dn_no, int status, String invoice_type, String sntn_no,
                             String stran_no, String invoice_no, String created_at, String name_address,
                             String branch_address, float subtotal, float sales_tax, float total,
                             float advance, float balance_due, List<HistoryPaidItemInvoiceModule> invoiceItem) {
        this.id = id;
        this.dn_no = dn_no;
        this.status = status;
        this.invoice_type = invoice_type;
        this.sntn_no = sntn_no;
        this.stran_no = stran_no;
        this.invoice_no = invoice_no;
        this.created_at = created_at;
        this.name_address = name_address;
        this.branch_address = branch_address;
        this.subtotal = subtotal;
        this.sales_tax = sales_tax;
        this.total = total;
        this.advance = advance;
        this.balance_due = balance_due;
        this.invoiceItem = invoiceItem;
    }


    public int getId() {
        return id;
    }

    public int getDn_no() {
        return dn_no;
    }

    public int getStatus() {
        return status;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public String getSntn_no() {
        return sntn_no;
    }

    public String getStran_no() {
        return stran_no;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getName_address() {
        return name_address;
    }

    public String getBranch_address() {
        return branch_address;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getSales_tax() {
        return sales_tax;
    }

    public float getTotal() {
        return total;
    }

    public float getAdvance() {
        return advance;
    }

    public float getBalance_due() {
        return balance_due;
    }

    public List<HistoryPaidItemInvoiceModule> getInvoiceItem() {
        return invoiceItem;
    }

}
