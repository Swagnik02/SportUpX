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
        setContentView(R.layout.activity_paytm);
        context = activity = this;
        sessionManager = new SessionManager();



        tv_TxDetails = findViewById(R.id.tv_TxDetails);
        tv_Proceed = findViewById(R.id.tv_Proceed);

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
        orderID = "OrderID" + System.currentTimeMillis()+"-"+customerID+"-"+PayAmount;
//        generateCheckSum();

        tv_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, MyAccountActivity.class);
                startActivity(i);
            }
        });

        // Set the payment response listener
        WLCheckoutActivity.setPaymentResponseListener(this);

        // Call the preloadData() method to preload SDK dependency files
        WLCheckoutActivity.preloadData(context);

    }

    @Override
    public void wlCheckoutPaymentResponse(JSONObject response) {
        // Handle a successful payment response here
    }

    @Override
    public void wlCheckoutPaymentError(@NonNull JSONObject jsonObject) {

    }
}

