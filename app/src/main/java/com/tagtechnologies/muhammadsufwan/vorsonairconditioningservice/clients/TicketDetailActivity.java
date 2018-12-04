package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

public class TicketDetailActivity extends AppCompatActivity {


    private static final String EXTRA_INVOICE_ID = "invoice_id";
    private static final String EXTRA_COMP_ID = "id";
    private static final String EXTRA_COMP_HELP = "help";
    private static final String EXTRA_COMP_DESC = "message";
    private static final String EXTRA_COMP_DATE = "date";
    private static final String EXTRA_COMP_STATUS = "status";
    private static final String EXTRA_TECHNI_NAME = "tech_name";
    private static final String EXTRA_TECHNI_NUMBER = "number";
    private static final String EXTRA_SOLVED_DATE = "task_end_date";

    private TextView comp_id,comp_type,comp_desc,comp_date,comp_status,techni_name,techni_number,invoice_id,task_end_date;
    private LinearLayout section_techni,section_invoice;
    private Button btn_1,btn_2;
    private ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        Toolbar toolbar = findViewById(R.id.toolbarTicketDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        comp_id = findViewById(R.id.tvTicketDetail_id);
        comp_type = findViewById(R.id.tvTicketDetail_type);
        comp_desc = findViewById(R.id.tvTicketDetail_message);
        comp_date = findViewById(R.id.tvTicketDetail_date);
        comp_status = findViewById(R.id.tvTicketDetail_status);
        techni_name = findViewById(R.id.tvTicketDetail_techniName);
        techni_number = findViewById(R.id.tvTicketDetail_techniNumber);
        invoice_id = findViewById(R.id.tvTicketDetail_invoiceID);
        task_end_date = findViewById(R.id.tvTicketDetail_invoiceAmount);

        section_techni = findViewById(R.id.TicketDetail_techniLayout);
        section_invoice = findViewById(R.id.TicketDetail_ivoiceLayout);

        btn_1 = findViewById(R.id.TicketDetail_btn_1);
        btn_2 = findViewById(R.id.TicketDetail_btn_2);

        goback = findViewById(R.id.goBackTicketDetail);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        int status = intent.getIntExtra(EXTRA_COMP_STATUS,0);
        if (status==0){

            int complaint_id = intent.getIntExtra(EXTRA_COMP_ID, 0);
            comp_id.setText(String.valueOf(complaint_id));
            String help = intent.getStringExtra(EXTRA_COMP_HELP);
            comp_type.setText(help);
            String message = intent.getStringExtra(EXTRA_COMP_DESC);
            comp_desc.setText(message);
            String date = intent.getStringExtra(EXTRA_COMP_DATE);
            comp_date.setText(date);
            comp_status.setText("Pending");
        }
        if (status==1){

            int complaint_id = intent.getIntExtra(EXTRA_COMP_ID, 0);
            comp_id.setText(String.valueOf(complaint_id));
            String help = intent.getStringExtra(EXTRA_COMP_HELP);
            comp_type.setText(help);
            String message = intent.getStringExtra(EXTRA_COMP_DESC);
            comp_desc.setText(message);
            String date = intent.getStringExtra(EXTRA_COMP_DATE);
            comp_date.setText(date);
            String tech_name = intent.getStringExtra(EXTRA_TECHNI_NAME);
            techni_name.setText(tech_name);
            String tech_number = intent.getStringExtra(EXTRA_TECHNI_NUMBER);
            techni_number.setText(tech_number);
            section_techni.setVisibility(View.VISIBLE);
            comp_status.setText("In Progress");
        }
        if (status==2){

            int complaint_id = intent.getIntExtra(EXTRA_COMP_ID, 0);
            comp_id.setText(String.valueOf(complaint_id));
            String help = intent.getStringExtra(EXTRA_COMP_HELP);
            comp_type.setText(help);
            String message = intent.getStringExtra(EXTRA_COMP_DESC);
            comp_desc.setText(message);
            String date = intent.getStringExtra(EXTRA_COMP_DATE);
            comp_date.setText(date);
            String tech_name = intent.getStringExtra(EXTRA_TECHNI_NAME);
            techni_name.setText(tech_name);
            String tech_number = intent.getStringExtra(EXTRA_TECHNI_NUMBER);
            techni_number.setText(tech_number);
            section_techni.setVisibility(View.VISIBLE);
            int inv_id = intent.getIntExtra(EXTRA_INVOICE_ID, 0);
            invoice_id.setText(String.valueOf(inv_id));
            String task_ended = intent.getStringExtra(EXTRA_SOLVED_DATE);
            task_end_date.setText(task_ended);
            section_invoice.setVisibility(View.VISIBLE);
            comp_status.setText("Completed");
        }

    }
}
