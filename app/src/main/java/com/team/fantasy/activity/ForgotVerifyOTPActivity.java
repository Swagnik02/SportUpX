package com.team.fantasy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityVerifyOtpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.FORGOTPASSWORD;
import static com.team.fantasy.APICallingPackage.Config.VERIFYFORGOTPASSWORD;
import static com.team.fantasy.APICallingPackage.Constants.FORGOTPASSWORDTYPE;
import static com.team.fantasy.APICallingPackage.Constants.VERIFYFORGOTPASSWORDTYPE;

public class ForgotVerifyOTPActivity extends AppCompatActivity implements ResponseManager {


    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    Context context;
    ForgotVerifyOTPActivity activity;
    String OTP;
    String IntentType,IntentEmailorMobile;
    private static CountDownTimer countDownTimer;
    ActivityVerifyOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_verify_otp);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = activity = this;
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        initView();
        Intent o = getIntent();
        IntentType = o.getStringExtra("type");
        IntentEmailorMobile = o.getStringExtra("EmailorMobile");
        binding.tvOtpSendTo.setText("OTP sent to "+IntentEmailorMobile);
        callForgotPasswordApi(true);
        binding.etOtp1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etOtp1.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.etOtp2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.etOtp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etOtp2.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.etOtp3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.etOtp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etOtp3.getText().toString().length() == 1)     //size as per your requirement
                {
                    binding.etOtp4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        binding.etOtp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.etOtp4.getText().toString().length() == 1)     //size as per your requirement
                {
                   OTP = GetOTP();
                   callVerifyOTPApi(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


        binding.tvVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OTP  = GetOTP();
                callVerifyOTPApi(true);
                countDownTimer.cancel();

            }
        });

        binding.tvOtpResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForgotPasswordApi(true);
                binding.tvOtpTimer.setVisibility(View.VISIBLE);
                binding.tvOtpResend.setVisibility(View.GONE);

            }
        });

    }

    public void initView(){


        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    countDownTimer.cancel();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });
        binding.head.tvHeaderName.setText(getResources().getString(R.string.verify_otp));


    }

    public String GetOTP(){
        String GETOTP = "";
        String Otp1 = binding.etOtp1.getText().toString();
        String Otp2 = binding.etOtp2.getText().toString();
        String Otp3 = binding.etOtp3.getText().toString();
        String Otp4 = binding.etOtp4.getText().toString();

        if (Otp1.equals("")){
            ShowToast(context,"Enter OTP");
        }
        else if (Otp2.equals("")){
            ShowToast(context,"Enter OTP");
        }else if (Otp3.equals("")){
            ShowToast(context,"Enter OTP");
        }else if (Otp4.equals("")){
            ShowToast(context,"Enter OTP");
        }
        else {
            GETOTP = Otp1+Otp2+Otp3+Otp4;
        }

        return GETOTP;
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

                char o1 = message.charAt(0);
                char o2 = message.charAt(1);
                char o3 = message.charAt(2);
                char o4 = message.charAt(3);


                binding.etOtp1.setText(o1+"");
                binding.etOtp2.setText(o2+"");
                binding.etOtp3.setText(o3+"");
                binding.etOtp4.setText(o4+"");

                GetOTP();
                callVerifyOTPApi(true);
                countDownTimer.cancel();

            }
        }
    };

    private void callVerifyOTPApi(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(VERIFYFORGOTPASSWORD,
                    createRequestJson(), context, activity, VERIFYFORGOTPASSWORDTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("EmailorNumber", IntentEmailorMobile);
            jsonObject.put("otp", OTP);
            jsonObject.put("type", IntentType);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    private void callForgotPasswordApi(boolean isShowLoader) {
        try {

            countDownTimer =  new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //tv_Timer.setText("Resend OTP in: " + millisUntilFinished / 1000);
                    binding.tvOtpTimer.setText("Didn't receive the OTP? Request for a new one in "+ String.format("%d:%d sec",
                            TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                public void onFinish() {
                    binding.tvOtpResend.setVisibility(View.VISIBLE);
                    binding.tvOtpTimer.setVisibility(View.GONE);
                }

            }.start();


            apiRequestManager.callAPI(FORGOTPASSWORD,
                    createRequestJsonForgotPassword(), context, activity, FORGOTPASSWORDTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJsonForgotPassword() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("EmailorNumber", IntentEmailorMobile);
            jsonObject.put("type", IntentType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    @Override
    public void onResume() {
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {


        if (type.equals(VERIFYFORGOTPASSWORDTYPE)) {
            ShowToast(context,message);

            try {
                Intent i = new Intent(activity, NewPasswordActivity.class);
                i.putExtra("UserId", result.getString("user_id"));
                i.putExtra("IntentActivity", "ForgotPassword");

                startActivity(i);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (type.equals(FORGOTPASSWORDTYPE)){
            ShowToast(context,message);
        }

    }



    @Override
    public void onError(Context mContext, String type, String message) {
        if (type.equals(VERIFYFORGOTPASSWORDTYPE)){
            ShowToast(context,"Invalid OTP");
        }
        else if (type.equals(FORGOTPASSWORDTYPE)){
            ShowToast(context,message);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(activity,LoginActivity.class);
        startActivity(i);
    }
}
