package com.team.fantasy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityPaymentConfirmationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.JOINCONTEST;
import static com.team.fantasy.APICallingPackage.Config.MYACCOUNT;
import static com.team.fantasy.APICallingPackage.Constants.JOINCONTESTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.MYACCOUNTTYPE;

public class PaymentConfirmationActivity extends AppCompatActivity implements ResponseManager {


    PaymentConfirmationActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    SessionManager sessionManager;
    String JoinMyTeamId;
    String MyContestCode;

    double TotalAmount,Deposited,Winnings,Bonus;
    double FinaltoPayAmount,EntryFee;
    Integer BonusPercentage;
    String FinalToPayAmount;

    ActivityPaymentConfirmationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_payment_confirmation);
        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();
        final String Entryfees=JoinContestActivity.EntryFee;
        EntryFee = Double.valueOf(JoinContestActivity.EntryFee);


        JoinMyTeamId = ContestListActivity.JoinMyTeamId;
        MyContestCode= JoinContestActivity.MyContestCode;

        binding.tvConfirmationEntryFee.setText("₹ "+EntryFee);
        binding.tvConfirmationToPay.setText("₹ "+EntryFee);

        binding.tvConfirmationJoinContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TotalAmount>=FinaltoPayAmount) {
                    callJoinContest(true);
                }
                else {
                    AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                    ab.setMessage("Not enough balance in your account to join contest.");

                    ab.setPositiveButton("Add Amount", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(activity, AddCashActivity.class);
                            i.putExtra("Activity","PaymentConfirmationActivity");
                            i.putExtra("EntryFee",Entryfees);
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
        });

        callMyAccountDetails(true);
    }

    public void initViews(){
        binding.tvBonusPercentage.setText("(10% of Entry Fee)");
        binding.head.tvHeaderName.setText(getResources().getString(R.string.confirmation));
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

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(JOINCONTESTTYPE)) {
            if (JoinContestActivity.MyContestCode.equals("")) {
               LayoutInflater li = getLayoutInflater();
                //Getting the View object as defined in the customtoast.xml file
                View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom_toast_layout));
                 TextView textView= (TextView)  layout.findViewById(R.id.custom_toast_message);
                //Creating the Toast object
                textView.setText(message);
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);//setting the view of custom toast layout
                toast.show();
                Intent i = new Intent(activity, HomeActivity.class);
                startActivity(i);
                finish();
            }else {
                ShowToast(context, message);
                Intent i = new Intent(activity, InviteInContestActivity.class);
                i.putExtra("ContestCode",JoinContestActivity.MyContestCode);
                startActivity(i);
                finish();
            }


        }
        else {

            try {
                TotalAmount = Double.parseDouble((result.getString("total_amount")));
                Deposited = Double.valueOf(result.getString("credit_amount"));
                Winnings = Double.valueOf(result.getString("winning_amount"));
                Bonus = Double.valueOf(result.getString("bonous_amount"));
                binding.tvWalletBalance.setText("Unutilized Balance + Winnings = ₹"+TotalAmount);
                double After10PercentBonus = EntryFee*10/100;
                if (Bonus>=After10PercentBonus){
                    binding.tvConfirmationBonus.setText("- ₹ "+After10PercentBonus);
                    FinaltoPayAmount = EntryFee-After10PercentBonus;
                    binding.tvConfirmationToPay.setText("₹ "+FinaltoPayAmount);
                }
                else {
                    binding.tvConfirmationBonus.setText("- ₹ "+0.0);
                    FinaltoPayAmount = EntryFee;
                    binding.tvConfirmationToPay.setText("₹ "+FinaltoPayAmount);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(context,message);
    }
}
