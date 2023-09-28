package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.utils.PaytmConstants;
import com.team.fantasy.utils.SessionManager;

import com.team.fantasy.utils.WLConstants;
import com.weipl.checkout.WLCheckoutActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WorldLineActivity extends AppCompatActivity implements WLCheckoutActivity.PaymentResponseListener,ResponseManager {
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
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);
        tv_HeaderName.setText("PAYMENT OPTION");
        buttonBuy = findViewById(R.id.wl_buyButton);

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
        System.out.println(generateToken());


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

    private void generateCheckSum() {
        String checkSum = "";
//        initializeWorldLinePayment();

    }
    private static String generateToken() {
        try {
            // Replace these values with your actual data
            String merchantId = "L3348";
            String txnId = "1695896030940";
            String totalAmount = "10";
            String consumerId = "c964634";
            String consumerMobileNo = "9876543210";
            String consumerEmailId = "test@test.com";
            String salt = "YourSalt";

            // Trim and concatenate all the required data fields with a pipe character "|"
            String dataToHash = String.format("%s|%s|%s|%s|%s|%s|%s",
                    merchantId.trim(),
                    txnId.trim(),
                    totalAmount.trim(),
                    consumerId.trim(),
                    consumerMobileNo.trim(),
                    consumerEmailId.trim(),
                    salt);

            // Choose SHA-512 as the hashing algorithm
            String hashingAlgorithm = "SHA-512";

            // Hash the data using the selected algorithm
            MessageDigest digest = MessageDigest.getInstance(hashingAlgorithm);
            byte[] encodedHash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the NoSuchAlgorithmException appropriately
            e.printStackTrace();
            return null;
        }
    }


    private void initializeWorldLinePayment() {
        try {
            JSONObject reqJson = new JSONObject();

            JSONObject jsonFeatures = new JSONObject();
            jsonFeatures.put("enableExpressPay", WLConstants.ENABLE_EXPRESS_PAY);
            jsonFeatures.put("enableInstrumentDeRegistration", WLConstants.ENABLE_INSTRUMENT_DEREGISTRATION);
            jsonFeatures.put("enableAbortResponse", WLConstants.ENABLE_ABORT_RESPONSE);
            jsonFeatures.put("enableMerTxnDetails", WLConstants.ENABLE_MER_TXN_DETAILS);
            jsonFeatures.put("enableNewWindowFlow", true);

            reqJson.put("features", jsonFeatures);

            JSONObject jsonConsumerData = new JSONObject();
            jsonConsumerData.put("deviceId", "AndroidSH2");
            jsonConsumerData.put("token", "3352c02fe30bdc014f44a64a9ef579287a0ae3a75bd19545b5a52f011e30c424f5b06a8be100008230ac40c36269b39f021b1c92b56a42b0007e9ab4fb49fe1d");
//            jsonConsumerData.put("token", generateToken());
            jsonConsumerData.put("returnUrl", "https://www.tekprocess.co.in/MerchantIntegrationClient/MerchantResponsePage.jsp");    //merchant response page URL
            jsonConsumerData.put("responseHandler", "handleResponse");
            jsonConsumerData.put("paymentMode", "all");
            jsonConsumerData.put("merchantLogoUrl", "https://www.paynimo.com/CompanyDocs/company-logo-vertical.png");  //provided merchant logo will be displayed
            jsonConsumerData.put("merchantId", "L3348");
            jsonConsumerData.put("currency", "INR");
            jsonConsumerData.put("consumerId", "c964634");
            jsonConsumerData.put("consumerMobileNo", "9876543210");
            jsonConsumerData.put("consumerEmailId", "test@test.com");
            jsonConsumerData.put("txnId", "1695896030940");



            JSONArray jArrayItems = new JSONArray();
            JSONObject jsonItem1 = new JSONObject();
            jsonItem1.put("itemId", "first");
            jsonItem1.put("amount", "10");
            jsonItem1.put("comAmt", "0");
            jArrayItems.put(jsonItem1);
            jsonConsumerData.put("items", jArrayItems);

            JSONObject jsonCustomStyle = new JSONObject();
            jsonCustomStyle.put("PRIMARY_COLOR_CODE", "#45beaa");
            jsonCustomStyle.put("SECONDARY_COLOR_CODE", "#ffffff");
            jsonCustomStyle.put("BUTTON_COLOR_CODE_1", "#2d8c8c");
            jsonCustomStyle.put("BUTTON_COLOR_CODE_2", "#ffffff");
            jsonConsumerData.put("customStyle", jsonCustomStyle);

            reqJson.put("consumerData", jsonConsumerData);

            WLCheckoutActivity.open(context, reqJson);
        } catch (JSONException e) {
            // Handle the JSON exception here, for example, by logging an error message
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void wlCheckoutPaymentResponse(JSONObject response) {

            try {
                String msg = response.optString("msg");
                String[] msgParts = msg.split("\\|");

                // Extract individual components from the response
                String txnStatus = msgParts[0];
                String txnMsg = msgParts[1];
                String txnErrMsg = msgParts[2];
                String clntTxnRef = msgParts[3];
                String tpslBankCd = msgParts[4];
                String tpslTxnId = msgParts[5];
                String txnAmt = msgParts[6];
                String clntRqstMeta = msgParts[7];
                String tpslTxnTime = msgParts[8];
                String balAmt = msgParts[9];
                String cardId = msgParts[10];
                String aliasName = msgParts[11];
                String bankTransactionId = msgParts[12];
                String mandateRegNo = msgParts[13];
                String token = msgParts[14];
                String hash = msgParts[15];

            // Check the transaction status and take appropriate actions
            if ("0300".equals(txnStatus)) {
                // Payment was successful
                // Update your UI and database accordingly
            } else if ("0399".equals(txnStatus)) {
                // Payment failed
                // Display an error message to the user
            } else if ("0398".equals(txnStatus)) {
                // Payment initiated, waiting for confirmation
                // Update your UI to indicate the payment is being processed
            } else if ("0396".equals(txnStatus)) {
                // Payment awaited, waiting for further action
                // Update your UI to indicate the payment is being awaited
            } else if ("0392".equals(txnStatus)) {
                // Payment aborted
                // Display a message to the user indicating the payment was aborted
            } else {
                // Handle other status codes as needed
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur while processing the response
        }
    }

    @Override
    public void wlCheckoutPaymentError(JSONObject response) {
        try {
            String errorCode = response.optString("error_code");
            String errorDesc = response.optString("error_desc");

            // Display the error code and description to the user
            // You can also log this information for debugging
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur while processing the error response
        }
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

    }

    @Override
    public void onError(Context mContext, String type, String message) {

    }
}

