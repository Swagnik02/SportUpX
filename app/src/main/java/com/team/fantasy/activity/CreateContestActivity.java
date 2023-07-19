package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.WindowManager;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityCreateContestBinding;

import org.json.JSONObject;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;

public class CreateContestActivity extends AppCompatActivity implements ResponseManager {



    CreateContestActivity activity;
    Context context;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;


    String ContestName;
    int WinningAmount,ContestSize;
    double EntryFees;
    String StringWinningAmount,StringContestSize;

    String MatchId;
    ActivityCreateContestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_create_contest);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        MatchId = getIntent().getStringExtra("MatchId");

        binding.RL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContestName = binding.etContestName.getText().toString();
                StringWinningAmount = binding.etWinningAmount.getText().toString();
                StringContestSize = binding.etContestSize.getText().toString();

                if (StringWinningAmount.equals("")){

                    ShowToast(context,"Enter Winning Amount");

                }else if (StringContestSize.equals("")){

                    ShowToast(context,"Enter Contest Size");

                }else {
                    WinningAmount = Integer.parseInt(StringWinningAmount);
                    ContestSize = Integer.parseInt(StringContestSize);

                    if (WinningAmount==0||WinningAmount>10000){
                        ShowToast(context,"Winning Amount Should be between 0 to 10,000");
                    }else if (ContestSize<2||ContestSize>100){
                        ShowToast(context,"Contest Size Must be between 2 to 100");
                    }
                    else {
                        double amount = WinningAmount;
                        double TwentyPercentAmount = (amount / 100.0f) * 20;
                        double FinalAmount = amount+TwentyPercentAmount;
                        EntryFees = FinalAmount/ContestSize;
                        binding.tvEntryFees.setText("Entry Fees - "+EntryFees);
                        if (EntryFees<5){
                            ShowToast(context,"Entry Fees cannot be less than 5 Rs.");

                        }
                    }


                }

            }
        });
        binding.RLBottomCreateMyContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContestName = binding.etContestName.getText().toString();
                StringWinningAmount = binding.etWinningAmount.getText().toString();
                StringContestSize = binding.etContestSize.getText().toString();

                if (StringWinningAmount.equals("")){

                    ShowToast(context,"Enter Winning Amount");

                }else if (StringContestSize.equals("")){

                    ShowToast(context,"Enter Contest Size");

                }else {
                    WinningAmount = Integer.parseInt(StringWinningAmount);
                    ContestSize = Integer.parseInt(StringContestSize);

                    if (WinningAmount==0||WinningAmount>10000){
                        ShowToast(context,"Winning Amount Should be between 0 to 10,000");
                    }else if (ContestSize<2||ContestSize>100){
                        ShowToast(context,"Contest Size Must be between 2 to 100");
                    }
                    else {
                        double amount = WinningAmount;
                        double TwentyPercentAmount = (amount / 100.0f) * 20;
                        double FinalAmount = amount+TwentyPercentAmount;
                        EntryFees = FinalAmount/ContestSize;
                        binding.tvEntryFees.setText("Entry Fees - "+EntryFees);
                        if (EntryFees<5){
                            ShowToast(context,"Entry Fees cannot be less than 5 Rs.");
                        }
                        else {
                            Intent i = new Intent(activity,SelectPrizeCreateActivity.class);
                            i.putExtra("ContestName",ContestName);
                            i.putExtra("ContestSize",ContestSize);
                            i.putExtra("ContestWinningAmount",WinningAmount);
                            i.putExtra("EntryFees",EntryFees);
                            i.putExtra("MatchId",MatchId);
                            startActivity(i);
                        }
                    }
                }
            }
        });
    }
    public void initViews(){
        binding.head.tvHeaderName.setText("Make Your Own Contest");
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
