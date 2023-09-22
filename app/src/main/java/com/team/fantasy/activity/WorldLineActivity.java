package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;

import com.weipl.checkout.WLCheckoutActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WorldLineActivity extends AppCompatActivity implements WLCheckoutActivity.PaymentResponseListener {
    Button buttonBuy;

    private String orderID = "";
    private String customerID = "";
    private String PayAmount = "0.0";

    WorldLineActivity activity;
    Context context;
    ImageView im_back;
    TextView tv_HeaderName;
    SessionManager sessionManager;
    TextView tv_TxDetails,tv_Proceed;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    String AfterPaymentOrderId, AfterPaymentTxId,AfterPaymentAmount,FinalMessage,AfterPaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldline);
        context = activity = this;
        sessionManager = new SessionManager();


        tv_TxDetails = findViewById(R.id.wl_tx_details);
        tv_Proceed = findViewById(R.id.wl_tv_Proceed);
        tv_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MyAccountActivity.class);
                startActivity(i);
            }
        });


//        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);
        tv_HeaderName.setText("PAYMENT OPTION");

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        PayAmount = getIntent().getStringExtra("FinalAmount");
        customerID = sessionManager.getUser(context).getUser_id();
        orderID = "OrderID" + System.currentTimeMillis() + "-" + customerID + "-" + PayAmount;
//        generateCheckSum();

        tv_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MyAccountActivity.class);
                startActivity(i);
            }
        });
        buttonBuy = findViewById(R.id.wl_buyButton);

        // Call preloadData() method and set the listener
        WLCheckoutActivity.setPaymentResponseListener(this);
        WLCheckoutActivity.preloadData(context);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeWorldLinePayment();
            }
        });
    }

    private void initializeWorldLinePayment() {
        try {
            // Create the JSON configuration for initialization
            JSONObject reqJson = new JSONObject();

            // Create JSON for features
            JSONObject jsonFeatures = new JSONObject();
            jsonFeatures.put("enableExpressPay", true);
            jsonFeatures.put("enableInstrumentDeRegistration", true);
            jsonFeatures.put("enableAbortResponse", true);
            jsonFeatures.put("enableMerTxnDetails", true);
            reqJson.put("features", jsonFeatures);

            // Create JSON for consumer data
            JSONObject jsonConsumerData = new JSONObject();
            jsonConsumerData.put("deviceId", "AndroidSH2");
            jsonConsumerData.put("token", "0b125f92d967e06135a7179d2d0a3a12e246dc0ae2b00ff018ebabbe747a4b5e47b5eb7583ec29ca0bb668348e1e2cd065d60f323943b9130138efba0cf109a9");
            jsonConsumerData.put("paymentMode", "all");
            jsonConsumerData.put("merchantLogoUrl", "https://www.paynimo.com/CompanyDocs/company-logo-vertical.png");
            jsonConsumerData.put("merchantId", "L3348");
            jsonConsumerData.put("currency", "INR");
            jsonConsumerData.put("consumerId", "c964634");
            jsonConsumerData.put("consumerMobileNo", "9876543210");
            jsonConsumerData.put("consumerEmailId", "test@test.com");
            jsonConsumerData.put("txnId", "1667804027874");

            // Create JSON for items
            JSONArray jArrayItems = new JSONArray();
            JSONObject jsonItem1 = new JSONObject();
            jsonItem1.put("itemId", "first");
            jsonItem1.put("amount", "10");
            jsonItem1.put("comAmt", "0");
            jArrayItems.put(jsonItem1);
            jsonConsumerData.put("items", jArrayItems);

            // Create JSON for custom styles
            JSONObject jsonCustomStyle = new JSONObject();
            jsonCustomStyle.put("PRIMARY_COLOR_CODE", "#45beaa");
            jsonCustomStyle.put("SECONDARY_COLOR_CODE", "#ffffff");
            jsonCustomStyle.put("BUTTON_COLOR_CODE_1", "#2d8c8c");
            jsonCustomStyle.put("BUTTON_COLOR_CODE_2", "#ffffff");
            jsonConsumerData.put("customStyle", jsonCustomStyle);

            reqJson.put("consumerData", jsonConsumerData);

            // Initialize the checkout
            WLCheckoutActivity.open(WorldLineActivity.this, reqJson);
        } catch (JSONException e) {
            // Handle the JSON exception here, for example, by logging an error message
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void wlCheckoutPaymentResponse(JSONObject response) {
        // Handle a successful payment response here
    }

    @Override
    public void wlCheckoutPaymentError(@NonNull JSONObject jsonObject) {

    }
}

