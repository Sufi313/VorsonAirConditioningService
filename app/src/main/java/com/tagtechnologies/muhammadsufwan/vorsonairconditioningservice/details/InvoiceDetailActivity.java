package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.details;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidItemAdapter;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.HistoryPaidItemInvoiceModule;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.ConfirmPayAmountActivity;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.clients.SharedPrefManagerClient;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.R;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.Apis;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.utilities.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class InvoiceDetailActivity extends AppCompatActivity {


    private static final String TAG = InvoiceDetailActivity.class.getSimpleName();
    private static final String EXTRA_INVOICE_ID = "invoice_id";

    private TextView invoice_type_heading,invoice_balance,invoice_advance,invoice_total,invoice_sales_tax,invoice_subtotal,invoice_branch_address
    ,invoice_name_address,invoice_dn,invoice_date,invoice_no,vorson_STRN,vorson_SNTN;
    private LinearLayout lini;


    private String INVOICE_NO;
    private String INVOICE_DATE;
    private int INVOICE_DN_NO;
    private String INVOICE_SNTN_NO;
    private String INVOICE_STRN_NO;
    private String INVOICE_NAME_ADDRESS;
    private String INVOICE_BRANCH_ADDRESS;
    private double INVOICE_SUBTOTAL;
    private double INVOICE_SALES_TAX;
    private double INVOICE_TOTAL;
    private double INVOICE_ADVANCE;
    private double INVOICE_BALNCE;
    private String INVOICE_TYPE;
    private int INVOICE_STATUS;

    private int invoice_id;
    private int client_id;
    private Button payOut;
    private String payAmount;
    private android.app.AlertDialog alertDialog;

    private HistoryPaidItemAdapter adapter;
    private RecyclerView recyclerView;
    private List<HistoryPaidItemInvoiceModule> paidItemList;


//    PDF GENERATOR
    private Bitmap b;
    private int totalHeight;
    private int totalWidth;
    private File myPath;
    private String path;

//    PAYPAL INTEGRATION

    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)

            .clientId("AYSq3RDGsmBLJE-otTkBtM-jBRd1TCQwFf9RGfwddNXWz0uFU9ztymylOhRS");

    public static final String clientSecret = "EGnHDxD_qRPdaLdZz8iCr8N7_MzF-YHPTkjs6NKYQvQSBngp4PTTVWkPZRbL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);
        ImageView logoImage = findViewById(R.id.toolbarLogoInvoiceDetail);
        logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbarInvoiceDetail);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        alertDialog = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(InvoiceDetailActivity.this).build();

        ImageView goBack = findViewById(R.id.goBackInvoiceDetail);


        lini = findViewById(R.id.branch_layout);
        vorson_SNTN = findViewById(R.id.vorsonSNTN);
        vorson_STRN = findViewById(R.id.vorsonSTRN);
        invoice_no = findViewById(R.id.detail_invoice_no);
        invoice_date = findViewById(R.id.detail_invoice_date);
        invoice_dn = findViewById(R.id.detail_invoice_dn);
        invoice_name_address = findViewById(R.id.detail_invoice_name_address);
        invoice_branch_address = findViewById(R.id.detail_invoice_branch_address);
        invoice_subtotal = findViewById(R.id.detail_invoice_subtotal);
        invoice_sales_tax = findViewById(R.id.detail_invoice_salesTax);
        invoice_total = findViewById(R.id.detail_invoice_total);
        invoice_advance = findViewById(R.id.detail_invoice_advance);
        invoice_balance = findViewById(R.id.detail_invoice_balance);
        invoice_type_heading = findViewById(R.id.detail_invoice_type_heading);
        payOut = findViewById(R.id.takeToPaypal);

        client_id = SharedPrefManagerClient.getInstance(this).getClient().getId();

        Intent intent = getIntent();
        invoice_id = intent.getIntExtra(EXTRA_INVOICE_ID, 0);

        getInvoiceData gid = new getInvoiceData();
        gid.execute();

        recyclerView = findViewById(R.id.itemRecyclerView);
        paidItemList = new ArrayList<>();
        adapter = new HistoryPaidItemAdapter(this, paidItemList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);

        payOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payAmount = String.valueOf(INVOICE_TOTAL);
                beginPayment(payAmount, "Invoice No: "+INVOICE_NO);

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        System.out.println(8);

    }

    private void setData(){

        invoice_no.setText(INVOICE_NO);
        invoice_date.setText(INVOICE_DATE);
        invoice_dn.setText(String.valueOf(INVOICE_DN_NO));
        invoice_name_address.setText(INVOICE_NAME_ADDRESS);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DecimalFormat formatter = new DecimalFormat("#,##,###.00");

            String valueSubtotal = getResources().getString(R.string.rupees) + formatter.format(INVOICE_SUBTOTAL);
            String valueAdvance = getResources().getString(R.string.rupees) + formatter.format(INVOICE_ADVANCE);
            String valueSalesTax = getResources().getString(R.string.rupees) + formatter.format(INVOICE_SALES_TAX);
            String valueTotal = getResources().getString(R.string.rupees) + formatter.format(INVOICE_TOTAL);
            String valueBalance = getResources().getString(R.string.rupees) + formatter.format(INVOICE_BALNCE);
            invoice_subtotal.setText(valueSubtotal);
            invoice_sales_tax.setText(valueSalesTax);
            invoice_total.setText(valueTotal);
            invoice_advance.setText(valueAdvance);
            invoice_balance.setText(valueBalance);
        } else {

            String valueTotal = getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", INVOICE_TOTAL));
            String valueSubtotal = getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", INVOICE_SUBTOTAL));
            String valueSalesTax = getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", INVOICE_SALES_TAX));
            String valueAdvance = getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", INVOICE_ADVANCE));
            String valueBalance = getResources().getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", INVOICE_BALNCE));

            invoice_subtotal.setText(valueSubtotal);
            invoice_sales_tax.setText(valueSalesTax);
            invoice_total.setText(valueTotal);
            invoice_advance.setText(valueAdvance);
            invoice_balance.setText(valueBalance);

        }

        if (INVOICE_STATUS == 0) {
            payOut.setVisibility(View.VISIBLE);
        }

        if (INVOICE_TYPE.equalsIgnoreCase("SSTI")) {
            vorson_SNTN.setVisibility(View.VISIBLE);
            vorson_SNTN.setText("SNTN# " + INVOICE_SNTN_NO);
            invoice_type_heading.setText(getString(R.string.sindh_sales_tax_invoice));
        }
        if (INVOICE_TYPE.equalsIgnoreCase("GSTI")) {
            vorson_STRN.setVisibility(View.VISIBLE);
            vorson_STRN.setText("STRN# " + INVOICE_STRN_NO);
            invoice_type_heading.setText(getString(R.string.general_sales_tax_invoices));
        }
        if (INVOICE_TYPE.equalsIgnoreCase("SI")) {
            invoice_type_heading.setText(getString(R.string.sales_tax_invoice));
        }
        if (!INVOICE_BRANCH_ADDRESS.equalsIgnoreCase("-")) {
            lini.setVisibility(View.VISIBLE);
            invoice_branch_address.setText(INVOICE_BRANCH_ADDRESS);
        }
    }

        class getInvoiceData extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                alertDialog.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                alertDialog.cancel();


                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {


                        JSONObject jsonObject = obj.getJSONObject("invoice");

                        INVOICE_TYPE = jsonObject.getString("invoice_type");
                        INVOICE_SNTN_NO = jsonObject.getString("sntn_no");
                        INVOICE_STRN_NO = jsonObject.getString("strn_no");
                        INVOICE_NO = jsonObject.getString("invoice_no");
                        INVOICE_DATE = jsonObject.getString("created_at");
                        INVOICE_DN_NO = jsonObject.getInt("dn_no");
                        INVOICE_NAME_ADDRESS = jsonObject.getString("name_address");
                        INVOICE_BRANCH_ADDRESS = jsonObject.getString("branch_address");
                        INVOICE_SUBTOTAL = jsonObject.getDouble("subtotal");
                        INVOICE_SALES_TAX = jsonObject.getDouble("sales_tax");
                        INVOICE_TOTAL = jsonObject.getDouble("total");
                        INVOICE_ADVANCE = jsonObject.getDouble("advance");
                        INVOICE_BALNCE = jsonObject.getDouble("balance_due");
                        INVOICE_STATUS = jsonObject.getInt("status");

                        setData();

                        JSONArray invoiceItem = jsonObject.getJSONArray("items");

                        for (int j = 0; j < invoiceItem.length(); j++) {

                            obj = invoiceItem.getJSONObject(j);

                            paidItemList.add(new HistoryPaidItemInvoiceModule(

                                    obj.getInt("id"),
                                    obj.getInt("invoice_id"),
                                    obj.getString("item_code"),
                                    obj.getString("description"),
                                    obj.getString("location"),
                                    obj.getString("quantity"),
                                    obj.getString("rate"),
                                    obj.getString("amount")

                            ));

                        }

                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                            DecimalFormat formatter = new DecimalFormat("#,##,###.00");
//                            String value = getString(R.string.rupees) + formatter.format(totalInvoiceAmount);
//                            totalAmount.setText(value);
//
//                        } else {
//                            String value = getString(R.string.rupees) + String.valueOf(String.format(Locale.US, "%.2f", totalInvoiceAmount));
//                            totalAmount.setText(value);
//                        }



                    } else {
                        Toast.makeText(getApplicationContext(), "No Invoices found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.wtf("JSON ERROR FETCH INVOICE DATA", e.getMessage());
                }


            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("invoice_id", String.valueOf(invoice_id));

                //returing the response
                return requestHandler.sendPostRequest(Apis.URL_FOR_GET_INVOICE_DATA, params);

            }

        }



    public void beginPayment(String payAmount, String forPay) {
        Intent serviceConfig = new Intent(this, PayPalService.class);
        serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(serviceConfig);

        PayPalPayment payment = new PayPalPayment(new BigDecimal(payAmount),
                "USD", forPay, PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentConfig = new Intent(this, PaymentActivity.class);
        paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentConfig, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(
                    PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String paymentDetails = confirm.toJSONObject().toString(4);


                    startActivity(new Intent(this, ConfirmPayAmountActivity.class)
                            .putExtra("PaymentDetails", paymentDetails)
                            .putExtra("PaymentAmount", payAmount)
                    );

                    finish();

                } catch (JSONException e) {
                    Log.e(TAG, "no confirmation data: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i(TAG, "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i(TAG, "Invalid payment / config set");
        }
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void takeScreenShot() {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

        path = folder.getAbsolutePath();
        path = path + "/" + "signature_pdf_" + System.currentTimeMillis() + ".pdf";// path where pdf will be stored

        View u = findViewById(R.id.nestedScrollViewInvoiceDetailActivity);
        NestedScrollView z = findViewById(R.id.nestedScrollViewInvoiceDetailActivity); // parent view
        totalHeight = z.getChildAt(0).getHeight();// parent view height
        totalWidth = z.getChildAt(0).getWidth();// parent view width

        //Save bitmap to  below path
        String extr = Environment.getExternalStorageDirectory() + "/Signature/";
        File file = new File(extr);
        if (!file.exists())
            file.mkdir();
        String fileName = "signature_img_.jpg";
        myPath = new File(extr, fileName);
        Uri imagesUri = Uri.parse(myPath.getPath());
        FileOutputStream fos = null;
        b = getBitmapFromView(u ,totalWidth,totalHeight);

        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        int totalHeight = z.getChildAt(0).getHeight();
//        int totalWidth = z.getChildAt(0).getWidth();
//        u.layout(0, 0, totalWidth, totalHeight);
//        u.buildDrawingCache(true);
//        Bitmap b = Bitmap.createBitmap(u.getDrawingCache());
//        u.setDrawingCacheEnabled(false);
//
//        //Save bitmap
//        String extr = Environment.getExternalStorageDirectory().toString() +   File.separator + "Folder";
//        String fileName = new SimpleDateFormat("yyyyMMddhhmm'_report.jpg'").format(new Date());
//        File myPath = new File(extr, fileName);
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(myPath);
//            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
//        }catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        createPdf();// create pdf after creating bitmap and saving

    }

    private void createPdf() {

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);


        Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(path);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

    }

    public static Bitmap getBitmapFromView(View view, int totalWidth, int totalHeight) {
        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

}
