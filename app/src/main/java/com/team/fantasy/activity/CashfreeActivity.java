package com.team.fantasy.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityCashfreeBinding;
import com.gocashfree.cashfreesdk.CFClientInterface;
import com.gocashfree.cashfreesdk.CFPaymentService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.ADDAMOUNT;
import static com.team.fantasy.APICallingPackage.Config.JOINCONTEST;
import static com.team.fantasy.APICallingPackage.Config.MYACCOUNT;
import static com.team.fantasy.APICallingPackage.Constants.ADDAMOUNTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.JOINCONTESTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.MYACCOUNTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.RETURNTOKENTYPE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;

public class CashfreeActivity extends AppCompatActivity implements ResponseManager, CFClientInterface {

    CashfreeActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    SessionManager sessionManager;



    private String orderID = "";
    private String customerID = "";
    private String PayAmount = "0.0";
    double TotalAmount,Deposited,Winnings,Bonus,FinaltoPayAmount;
    String TName,TEmail,TNumber,TCity,TState,TZipCode;

    String FPaymentId,FTransactionId,FPaymentMode,FTransactionStatus,FAmount,FUserName,
            FEmail,FPhone,FMessage;
    JSONObject TrakNPayResponse;
    String FinalToPayAmount;
    Integer BonusPercentage;


    ActivityCashfreeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_cashfree);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PayAmount = getIntent().getStringExtra("FinalAmount");
        customerID = sessionManager.getUser(context).getUser_id();
        orderID = "OrderID" + System.currentTimeMillis()+"-"+customerID+"-"+PayAmount;
        if(sessionManager.getUser(context).getCity()!=null)
        {
            binding.etCheckCity.setText(sessionManager.getUser(context).getCity()+"");
        }
        if(sessionManager.getUser(context).getPincode()!=null)
        {
            binding.etCheckZipCode.setText(sessionManager.getUser(context).getPincode()+"");
        }
        if(sessionManager.getUser(context).getState()!=null)
        {
            binding.etCheckState.setText(sessionManager.getUser(context).getState()+"");
        }
        if(sessionManager.getUser(context).getName()!=null)
        {
            binding.etCheckName.setText(sessionManager.getUser(context).getName()+"");
        }
        if(sessionManager.getUser(context).getMobile()!=null)
        {
            binding.etCheckNumber.setText(sessionManager.getUser(context).getMobile()+"");
        }
        binding.etCheckEmail.setText(sessionManager.getUser(context).getEmail()+"");
        //et_CheckNumber.setText(sessionManager.getUser(context).getMobile()+"");
        binding.tvTotalamount.setText("₹ "+getIntent().getStringExtra("FinalAmount"));





        binding.tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TName = binding.etCheckName.getText().toString().trim();
                TEmail = binding.etCheckEmail.getText().toString().trim();
                TNumber = binding.etCheckNumber.getText().toString().trim();

                if (TName.equals("")){
                    ShowToast(context,"Enter Name");
                }else if (TEmail.equals("")){
                    ShowToast(context,"Enter Email");
                }else if(!Validations.isValidEmail(TEmail)){
                    ShowToast(context,"Enter Valid Email Id");
                }
                else if (TNumber.equals("")){
                    ShowToast(context,"Enter Number");
                }else if (!TNumber.matches(Validations.MobilePattern)){
                    ShowToast(context,"Enter Valid Mobile Number");
                }
                else {
                    callReturnToken(true);
                }
            }
        });


    }
    public void initViews() {

        binding.head.tvHeaderName.setText(getResources().getString(R.string.pymnt_options));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void callMyAccountDetails(boolean isShowLoader) {

        try {
            apiRequestManager.callAPI(MYACCOUNT,
                    createRequestJsonWin(), context, activity, MYACCOUNTTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    JSONObject createRequestJsonWin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", HomeActivity.sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    private void callAddAmount(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(ADDAMOUNT,
                    createRequestJson(), context, activity, ADDAMOUNTTYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("amount", PayAmount);
            jsonObject.put("mode", "Cashfree");
            jsonObject.put("transection_detail", TrakNPayResponse);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void callJoinContest(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(JOINCONTEST,
                    createRequestJsonJoin(), context, activity, JOINCONTESTTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    JSONObject createRequestJsonJoin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("match_id", ContestListActivity.IntentMatchId);
            jsonObject.put("my_team_id",ContestListActivity.JoinMyTeamId );
            jsonObject.put("contest_id", ContestListActivity.ContestId);
            jsonObject.put("contest_amount", FinaltoPayAmount+"");

            //if contest is private then value is 1 else 0
            if (JoinContestActivity.MyContestCode.equals("")) {
                jsonObject.put("private", "0");
            }
            else {
                jsonObject.put("private", "1");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void
    callReturnToken(boolean isShowLoader) {
        LoadAffiliate();
    }
    JSONObject createRequestReturnToken() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId",orderID);
            jsonObject.put("amount",PayAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(RETURNTOKENTYPE)) {

            triggerPayment(false,"");

        }
        else
        if(type.equals(ADDAMOUNTTYPE)) {
            try {
                String Status = result.getString("transaction_status");
                Dialog(FTransactionStatus, FTransactionId, orderID, FAmount);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            if(type.equals(MYACCOUNTTYPE))
            {

                try {
                    TotalAmount = Double.parseDouble((result.getString("total_amount")));
                    Deposited = Double.valueOf(result.getString("credit_amount"));
                    Winnings = Double.valueOf(result.getString("winning_amount"));
                    BonusPercentage=Integer.parseInt(ContestListActivity.ContestBonusPercent);
                    Bonus = Double.valueOf(result.getString("bonous_amount"));

                    double After10PercentBonus = Double.parseDouble(JoinContestActivity.EntryFee)*BonusPercentage/100;
                    if (Bonus>=After10PercentBonus){

                        FinaltoPayAmount = Double.parseDouble(JoinContestActivity.EntryFee)-After10PercentBonus;
                    }
                    else if (Bonus<After10PercentBonus){
                        double pay = After10PercentBonus-Bonus;

                        if (Bonus==0.0){
                            pay = Double.parseDouble(JoinContestActivity.EntryFee);
                        }
                        if (TotalAmount<pay){
                            FinaltoPayAmount = pay;

                        }
                        else {

                            FinaltoPayAmount = pay;
                        }

                    }
                    else {

                        FinaltoPayAmount = Double.parseDouble(JoinContestActivity.EntryFee);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else  if (type.equals(JOINCONTESTTYPE)) {
                if (JoinContestActivity.MyContestCode.equals("")) {
                    LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                    TextView textView = (TextView) layout.findViewById(R.id.custom_toast_message);
                    //Creating the Toast object
                    textView.setText(message);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);//setting the view of custom toast layout
                    toast.show();
                    Intent i = new Intent(activity, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }


    }

    @Override
    public void onError(Context mContext, String type, String message) {
        PaymentFailedDialog(FTransactionStatus);

    }

    public void Dialog(String Status, String TxId, String OrderId, String TxAmount){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dialog);

        final TextView tv_DStatus = dialog.findViewById(R.id.tv_DStatus);
        final TextView tv_DTransactionId = dialog.findViewById(R.id.tv_DTransactionId);
        final TextView tv_DOrderId = dialog.findViewById(R.id.tv_DOrderId);
        final TextView tv_DTxAmount = dialog.findViewById(R.id.tv_DTxAmount);
        final TextView tv_TxDone = dialog.findViewById(R.id.tv_TxDone);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        tv_DStatus.setText(Status);
        tv_DTransactionId.setText(TxId);
        tv_DOrderId.setText(OrderId);
        tv_DTxAmount.setText("₹ "+TxAmount);


        tv_TxDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(TextUtils.isEmpty(AddCashActivity.Activity)&&AddCashActivity.Activity.equals("")){
                    Intent i = new Intent(activity, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    if (TotalAmount>=FinaltoPayAmount) {
                        callJoinContest(true);
                    }
                    else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                        ab.setMessage("Not enough balance in your account to join contest.");

                        ab.setPositiveButton("Add Amount", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(activity,AddCashActivity.class);
                                i.putExtra("Activity","PaymentConfirmationActivity");
                                i.putExtra("EntryFee",JoinContestActivity.EntryFee);
                                startActivity(i);
                            }
                        });
                        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = ab.create();
                        alert.show();
                    }
                }

            }

        });
    }

    public void PaymentFailedDialog(String message){

        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setCancelable(false);
        ab.setMessage(message);

        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (AddCashActivity.Activity.equals("")) {
                    Intent i = new Intent(activity, HomeActivity.class);
                    startActivity(i);
                    finish();

                }
                else {

                    Intent i = new Intent(activity, PaymentConfirmationActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        AlertDialog alert = ab.create();
        alert.show();

    }

    private void triggerPayment(boolean isUpiIntent,String Token) {

        String stage = "PROD";
        //
        //TEST
       // String appId = "YOUR APP TEST ID";
        //PROD
        String appId = "YOUR APP ID";
        String orderId = orderID;
        String orderAmount = PayAmount;
        String orderNote = orderID;
        String customerName = TName;
        String customerPhone = TNumber;
        String customerEmail = TEmail;

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, appId);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL,customerEmail);


        for(Map.Entry entry : params.entrySet()) {
            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
        }

        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);

        if (isUpiIntent) {
            cfPaymentService.upiPayment(this, params, Token, this, stage);
        }
        else {
            cfPaymentService.doPayment(this, params, Token, this, stage);
        }

    }


    @Override
    public void onSuccess(Map<String, String> map) {

        try{
            if(map.equals("null")){
                System.out.println("Transaction Error!");
                ShowToast(context,"Transaction Failed");
            }else{
                JSONObject response = new JSONObject(map);
                TrakNPayResponse = response;
                Log.e("TrakNPay", "onActivityResult: "+response);
                FPaymentId = response.getString("referenceId");
                FTransactionId = response.getString("referenceId");
                FPaymentMode = response.getString("paymentMode");
                FTransactionStatus = response.getString("txMsg");
                FAmount = response.getString("orderAmount");
                FMessage = response.getString("txMsg");

                Log.e("TrakNPay", "onActivityResult: "+response);
                System.out.print(AddCashActivity.Activity);
                callAddAmount(true);
                if(AddCashActivity.Activity.equals("PaymentConfirmationActivity")){
                    callMyAccountDetails(true);}

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void LoadAffiliate() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.RETURNTOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");
                            JSONObject jarr = jsonObject.getJSONObject("data");
                            Log.e("Data",jarr.toString());
                            if (status.equals("success")) {


                                String token = jarr.getString("token");
                                String orderId = jarr.getString("orderId");
                                triggerPayment(false, token);

                            }
                            else {
                                Toast.makeText(CashfreeActivity.this, ""+status, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("orderId", orderID);
                params.put("amount",PayAmount );
                Log.e("params",params.toString());
                System.out.print("params......"+params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CashfreeActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(true);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onFailure(Map<String, String> map) {

        Log.e("Map",String.valueOf(map));
        FPaymentId = map.get("referenceId");
        FTransactionId = map.get("referenceId");
        FPaymentMode = map.get("paymentMode");
        FTransactionStatus = map.get("txMsg");
        FAmount = map.get("orderAmount");
        FMessage = map.get("txMsg");

        PaymentFailedDialog(FTransactionStatus);

    }

    @Override
    public void onNavigateBack() {

    }
}
