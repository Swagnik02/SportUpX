package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.Resource;
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

public class WorldLineActivity extends AppCompatActivity implements WLCheckoutActivity.PaymentResponseListener, ResponseManager {
    Button buttonBuy;

    public static String transactionID = "";
    public String customerID = "";
    public static String PayAmount = "0.0";

    public static WorldLineActivity activity;
    public static Context context;
    ImageView im_back;
    TextView tv_HeaderName;
    SessionManager sessionManager;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    String AfterPaymentOrderId, AfterPaymentTxId, AfterPaymentAmount, FinalMessage, AfterPaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worldline);

        context = activity = this;
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        initViews();

        System.out.println(WLConstants.TOTAL_AMOUNT);

        // Call preloadData() method and set the listener
        WLCheckoutActivity.setPaymentResponseListener(this);
        WLCheckoutActivity.preloadData(context);

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeWorldLinePayment();
            }
        });
//                initializeWorldLinePayment();

    }


    public void initViews() {

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);
        TextView viewAmnt = findViewById(R.id.wl_amnt);
        TextView viewTxnId = findViewById(R.id.wl_txnId);

        im_back.setColorFilter(getResources().getColor(R.color.white));
        tv_HeaderName.setText("WolrdLine Payments");
        tv_HeaderName.setTextColor(getResources().getColor(R.color.white));

        findViewById(R.id.head).setBackgroundColor(Color.parseColor(WLConstants.PRIMARY_COLOR_CODE));
        buttonBuy = findViewById(R.id.wl_proceed);

        customerID = sessionManager.getUser(context).getUser_id();
        PayAmount = getIntent().getStringExtra("FinalAmount");
        transactionID = "TxnID" + System.currentTimeMillis() + "-" + customerID + "-" + PayAmount;

        WLConstants.TOTAL_AMOUNT = PayAmount;
        WLConstants.TXN_ID = transactionID;

        viewAmnt.setText(String.format("₹ %s", WLConstants.TOTAL_AMOUNT));
        viewTxnId.setText(WLConstants.TXN_ID);

        //        generateCheckSum();
        System.out.println(generateToken());

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void generateCheckSum() {
        String checkSum = "";
//        initializeWorldLinePayment();

    }

    private static String generateToken() {
        try {
            String dataToHash = String.format("%s|%s|%s|%s|%s|%s|%s",
                    WLConstants.MERCHANT_ID,
                    WLConstants.TXN_ID,
                    WLConstants.TOTAL_AMOUNT,
                    WLConstants.CONSUMER_ID,
                    WLConstants.CONSUMER_MOBILE_NO,
                    WLConstants.CONSUMER_EMAIL_ID,
                    WLConstants.SALT);

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
//            jsonFeatures.put("enableNewWindowFlow", WLConstants.ENABLE_NEW_WINDOW_FLOW);

            reqJson.put("features", jsonFeatures);

            JSONObject jsonConsumerData = new JSONObject();
            jsonConsumerData.put("deviceId", WLConstants.DEVICE_ID);
            jsonConsumerData.put("token", WLConstants.TOKEN);
//            jsonConsumerData.put("returnUrl", WLConstants.RETURN_URL);
//            jsonConsumerData.put("responseHandler", WLConstants.RESPONSE_HANDLER);
            jsonConsumerData.put("paymentMode", WLConstants.PAYMENT_MODE);
            jsonConsumerData.put("merchantLogoUrl", WLConstants.MERCHANT_LOGO_URL);
            jsonConsumerData.put("merchantId", WLConstants.MERCHANT_ID);
            jsonConsumerData.put("currency", WLConstants.CURRENCY);
            jsonConsumerData.put("consumerId", WLConstants.CONSUMER_ID);
            jsonConsumerData.put("consumerMobileNo", WLConstants.CONSUMER_MOBILE_NO);
            jsonConsumerData.put("consumerEmailId", WLConstants.CONSUMER_EMAIL_ID);
            jsonConsumerData.put("txnId", WLConstants.TXN_ID);

            JSONArray jArrayItems = new JSONArray();
            JSONObject jsonItem1 = new JSONObject();
            jsonItem1.put("itemId", WLConstants.ITEM_ID);
            jsonItem1.put("amount", WLConstants.TOTAL_AMOUNT);
            jsonItem1.put("comAmt", WLConstants.ITEM_COM_AMT);
            jArrayItems.put(jsonItem1);
            jsonConsumerData.put("items", jArrayItems);

            JSONObject jsonCustomStyle = new JSONObject();
            jsonCustomStyle.put("PRIMARY_COLOR_CODE", WLConstants.PRIMARY_COLOR_CODE);
            jsonCustomStyle.put("SECONDARY_COLOR_CODE", WLConstants.SECONDARY_COLOR_CODE);
            jsonCustomStyle.put("BUTTON_COLOR_CODE_1", WLConstants.BUTTON_COLOR_CODE_1);
            jsonCustomStyle.put("BUTTON_COLOR_CODE_2", WLConstants.BUTTON_COLOR_CODE_2);
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

