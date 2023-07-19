package com.team.fantasy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.WindowManager;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityWithdrawAmountBinding;

import org.json.JSONException;
import org.json.JSONObject;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.WITHDRAWAMOUNTUSERDATA;
import static com.team.fantasy.APICallingPackage.Config.WITHDRAWLREQUEST;
import static com.team.fantasy.APICallingPackage.Constants.SUBMITWITHDRAWLREQUESTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.WITHDRAWAMOUNTUSERDATATYPE;

public class WithdrawAmountActivity extends AppCompatActivity implements ResponseManager {
    WithdrawAmountActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    String Amount, Name, Number, AccountNumber, BankName,
            IFSCCode, BranchAddress, PanCardNumber, AdhaarCardNumber;
    String AvailableBalance;
    ActivityWithdrawAmountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw_amount);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        AvailableBalance = getIntent().getStringExtra("AvailableBalance");

        binding.tvWithdrawAvailabeBalance.setText("Available Amount for Withdrawl\n₹" + AvailableBalance);

        binding.tvSubmitWithdrawRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amount = binding.etWithdrawEnterAmount.getText().toString();
                Name = binding.etWithdrawName.getText().toString();
                Number = binding.etWithdrawMobile.getText().toString();
                AccountNumber = binding.etWithdrawAccountNumber.getText().toString();
                BankName = binding.etWithdrawBankName.getText().toString();
                IFSCCode = binding.etWithdrawIFSCCode.getText().toString();
                BranchAddress = binding.etWithdrawBranchAddress.getText().toString();
                PanCardNumber = binding.etWithdrawPAN.getText().toString();
                AdhaarCardNumber = binding.etWithdrawAdhaar.getText().toString();

                if (Amount.equals("") || Name.equals("") || Number.equals("") ||
                        AccountNumber.equals("") || BankName.equals("") || IFSCCode.equals("") ||
                        BranchAddress.equals("") || PanCardNumber.equals("") ||
                        AdhaarCardNumber.equals("")) {
                    ShowToast(context, "Field Can't Be Empty");

                } else {
                    ConfirmationDialog(Amount);
                }
            }
        });


        callLoadAccountData(true);

    }

    public void initViews() {

        binding.Head.tvHeaderName.setText(getResources().getString(R.string.withdraw_amt));
        binding.Head.tvHeaderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void callLoadAccountData(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(WITHDRAWAMOUNTUSERDATA,
                    createRequestJson(), context, activity, WITHDRAWAMOUNTUSERDATATYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void callSubmitWithdrawlRequest(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(WITHDRAWLREQUEST,
                    createWithdrawlRequestJson(), context, activity, SUBMITWITHDRAWLREQUESTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createWithdrawlRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("amount", Amount);
            jsonObject.put("user_name", Name);
            jsonObject.put("user_mobile", Number);
            jsonObject.put("acc_no", AccountNumber);
            jsonObject.put("bank_name", BankName);
            jsonObject.put("Ifsc_code", IFSCCode);
            jsonObject.put("branch_address", BranchAddress);
            jsonObject.put("pan_number", PanCardNumber);
            jsonObject.put("aadhar", AdhaarCardNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(SUBMITWITHDRAWLREQUESTTYPE)) {

            ShowToast(context, message + "");
            onBackPressed();
            finish();

        } else {
            try {
                Name = result.getString("user_acc_name");
                Number = result.getString("user_mobile");
                AccountNumber = result.getString("acc_no");
                BankName = result.getString("bank_name");
                IFSCCode = result.getString("ifsc_code");
                BranchAddress = result.getString("branch_address");
                PanCardNumber = result.getString("pan_number");
                AdhaarCardNumber = result.getString("aadhar");

                binding.etWithdrawName.setText(Name);
                binding.etWithdrawMobile.setText(Number);
                binding.etWithdrawAccountNumber.setText(AccountNumber);
                binding.etWithdrawBankName.setText(BankName);
                binding.etWithdrawIFSCCode.setText(IFSCCode);
                binding.etWithdrawBranchAddress.setText(BranchAddress);
                binding.etWithdrawPAN.setText(PanCardNumber);
                binding.etWithdrawAdhaar.setText(AdhaarCardNumber);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void ConfirmationDialog(String Amount) {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("Confirm your withdrawl request of ₹" + Amount + " ?");
        ab.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callSubmitWithdrawlRequest(true);
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

    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(context, message);

    }

}
