package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
//import com.elevenbaazigars.CashfreePackage.CashfreeActivity;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityPaymentOptionBinding;

import org.json.JSONException;
import org.json.JSONObject;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.SEND_PAYMENT_DATA_PHONEPE;
import static com.team.fantasy.APICallingPackage.Constants.SEND_PAYMENT_DATA_PHONEPE_TYPE;

import java.sql.SQLOutput;


public class PaymentOptionActivity extends AppCompatActivity implements ResponseManager {
    private String IntentPaymentUrl;
    PaymentOptionActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    String IntentFinalAmount;
    ActivityPaymentOptionBinding binding;
    String paymentResponseUrl;
    JSONObject paymentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_option);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        IntentPaymentUrl = getIntent().getStringExtra("FinalUrl");
//        System.out.println("Final Url: "+ IntentPaymentUrl);
        IntentFinalAmount = getIntent().getStringExtra("FinalAmount");

        binding.tvPaymentFinalAmount.setText("₹ " + IntentFinalAmount);


        JSONObject paymentData = new JSONObject();
                try {
                    paymentData.put("user_id", sessionManager.getUser(context).getUser_id());
                    paymentData.put("amount", IntentFinalAmount);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                callSendPaymentDataApi(paymentData);

        binding.RLPaytmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, PaytmActivity.class);
                    i.putExtra("FinalAmount", IntentFinalAmount);
                    startActivity(i);
                }

            }
        });

        binding.RLPayUMoneyPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, PayUMoneyPaymentActivity.class);
                    i.putExtra("FinalAmount", IntentFinalAmount);
                    startActivity(i);
                }

            }
        });
        binding.RLCashFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, CashfreeActivity.class);
                    i.putExtra("FinalAmount", IntentFinalAmount);
                    startActivity(i);
                }

            }
        });

        binding.RLTrakNPayPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, TrakNPayActivity.class);
                    i.putExtra("FinalAmount", IntentFinalAmount);
                    startActivity(i);
                }

            }
        });

        binding.RLPhonePePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                JSONObject paymentData = new JSONObject();
//                try {
//                    paymentData.put("user_id", sessionManager.getUser(context).getUser_id());
//                    paymentData.put("amount", IntentFinalAmount);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//                callSendPaymentDataApi(paymentData);

                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, PaymentWebviewAcitivity.class);
                    i.putExtra("Heading","PhonePe");
                    i.putExtra("URL", paymentResponseUrl);
                    i.putExtra("SuccessURL", "https://sportupx.com/paymentsuccess");
                    i.putExtra("headerColor", R.color.colorPhonePe);
                    i.putExtra("headerTextColor", R.color.white);
                    startActivity(i);
                }

            }
        });
        binding.RLWorldLinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IntentFinalAmount.equals("")) {
                    ShowToast(context, "Please Select Correct Amount");
                } else {
                    Intent i = new Intent(activity, WorldLineActivity.class);
                    i.putExtra("FinalAmount", IntentFinalAmount);
                    startActivity(i);
                }

            }
        });
    }
    private void callSendPaymentDataApi(JSONObject paymentData) {
        try {
            apiRequestManager.callAPI(SEND_PAYMENT_DATA_PHONEPE,
                    createSendPaymentDataJson(paymentData), context, activity,
                    SEND_PAYMENT_DATA_PHONEPE_TYPE, true, new ResponseManager() {

                        @Override
                        public void getResult(Context mContext, String type, String message, JSONObject result) {
                            try {
                                String url = result.getString("url");

                                if (url != null && !url.isEmpty()) {
                                    paymentResponseUrl = url;

                                    System.out.println("URL" + paymentResponseUrl);
//                                    Intent i = new Intent(activity, PaymentOptionActivity.class);
//                                    i.putExtra("FinalUrl", paymentResponseUrl);
//                                    i.putExtra("FinalAmount", IntentFinalAmount);
//
//                                    startActivity(i);
                                } else {
                                    System.out.println("Empty or missing 'url' key in response");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Context mContext, String type, String message) {
                            // Handle error if needed
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createSendPaymentDataJson(JSONObject paymentData) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", paymentData.getString("user_id"));
            jsonObject.put("amount", paymentData.getString("amount"));
            // You can add other parameters specific to the payment API
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void initViews() {
        binding.head.tvHeaderName.setText(getResources().getString(R.string.pymnt_opt));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }



    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

    }


    @Override
    public void onError(Context mContext, String type, String message) {

    }

}
