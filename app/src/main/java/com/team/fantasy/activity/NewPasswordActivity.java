package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.team.fantasy.databinding.ActivityNewPasswordBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.CHANGEPASSWORD;
import static com.team.fantasy.APICallingPackage.Config.UPDATENEWPASSWORD;
import static com.team.fantasy.APICallingPackage.Constants.CHANGEPASSWORDTPYE;
import static com.team.fantasy.APICallingPackage.Constants.UPDATENEWPASSWORDTPYE;

public class NewPasswordActivity extends AppCompatActivity implements ResponseManager {

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    Context context;
    NewPasswordActivity activity;



    String UserId,IntentActivity;
    String NewPassword,ConfirmNewPassword,OldPassword;

    ActivityNewPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_password);
        context = activity = this;
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        initViews();



    }

    public void initViews(){

        binding.head.tvHeaderName.setText(getString(R.string.new_pass));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        UserId = getIntent().getStringExtra("UserId");
        IntentActivity = getIntent().getStringExtra("IntentActivity");

        if (IntentActivity.equals("ForgotPassword")){
            binding.inputOldPassword.setVisibility(View.GONE);
        }
        else if (IntentActivity.equals("ChangePassword")){
            binding.inputOldPassword.setVisibility(View.VISIBLE);
        }


        binding.tvSubmitNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPassword = binding.etNewPassword.getText().toString();
                ConfirmNewPassword = binding.etConfirmNewPassword.getText().toString();

                if (IntentActivity.equals("ChangePassword")){
                    OldPassword = binding.etOldPassword.getText().toString();
                    if (OldPassword.equals("")){
                        ShowToast(context,"Enter Old Password");
                    }else if (OldPassword.length()<8&& !Validations.isValidPassword(OldPassword)){

                        ShowToast(context,"Password Pattern Not Macthed");
                    }

                    else if (NewPassword.equals("")){
                        ShowToast(context,"Enter New Password");
                    }
                    else if (NewPassword.length()<8&& !Validations.isValidPassword(NewPassword)){

                        ShowToast(context,"Password Pattern Not Macthed");
                    }
                    else if (ConfirmNewPassword.equals("")){
                        ShowToast(context,"Enter Confirm New Password");
                    }
                    else if (!NewPassword.equals(ConfirmNewPassword)){
                        ShowToast(context,"Confirm Password Not Match");
                    }
                    else {
                        callChangePasswordApi(true);
                    }
                }
                else {

                    if (NewPassword.equals("")) {
                        ShowToast(context, "Enter New Password");
                    }else if (NewPassword.length()<8&& !Validations.isValidPassword(NewPassword)){

                        ShowToast(context,"Password Pattern Not Macthed");
                    }
                    else if (ConfirmNewPassword.equals("")) {
                        ShowToast(context, "Enter Confirm New Password");
                    } else if (!NewPassword.equals(ConfirmNewPassword)) {
                        ShowToast(context, "Confirm Password Not Match");
                    } else {
                        callUpdatePasswordApi(true);
                    }
                }

            }
        });

    }

    private void callChangePasswordApi(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(CHANGEPASSWORD,
                    createRequestJsonForgotPassword(), context, activity, CHANGEPASSWORDTPYE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callUpdatePasswordApi(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(UPDATENEWPASSWORD,
                    createRequestJsonForgotPassword(), context, activity, UPDATENEWPASSWORDTPYE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJsonForgotPassword() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", NewPassword);
            jsonObject.put("user_id", UserId);
            if (IntentActivity.equals("ChangePassword")) {
                jsonObject.put("old_password", OldPassword);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (IntentActivity.equals("ChangePassword")){
            ShowToast(context,message);
            Intent i = new Intent(activity, HomeActivity.class);
            startActivity(i);
            finish();
        }
        else {
            ShowToast(context,message);
            Intent i = new Intent(activity, LoginActivity.class);
            startActivity(i);
        }

    }



    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(context,message);
    }

    @Override
    public void onBackPressed() {

        if (IntentActivity.equals("ForgotPassword")){
            Intent i = new Intent(activity,LoginActivity.class);
            startActivity(i);
        }
        else {
            super.onBackPressed();
        }
    }
}
