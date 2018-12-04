package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.productsAndSupplies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;

import java.util.Objects;

public class ProductsAndCategoryActivity extends AppCompatActivity {


    private CardView newAC, usedAC, suppliesAC;
    private ImageView goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_and_category);
        Toolbar toolbar = findViewById(R.id.toolbarProductCategoryActivity);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);


//        Binding Cards

        goBack = findViewById(R.id.goBackProductCategoryActivity);
        newAC = findViewById(R.id.new_ac_form);
        usedAC = findViewById(R.id.used_ac_form);
        suppliesAC = findViewById(R.id.supplies_ac_form);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        newAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductsAndCategoryActivity.this, NewAcPurchaseActivity.class));
            }
        });

        usedAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductsAndCategoryActivity.this, NewAcPurchaseActivity.class));
            }
        });

        suppliesAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductsAndCategoryActivity.this, NewAcPurchaseActivity.class));
            }
        });
    }
}
