package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.team.fantasy.databinding.ActivityEnterInviteCodeContestBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanWiningInfoList;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.CREATEOWNCONTESTLIST;
import static com.team.fantasy.APICallingPackage.Config.WINNINGINFOLIST;
import static com.team.fantasy.APICallingPackage.Constants.CREATEOWNCONTESTLISTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.WINNINGINFOLISTTYPE;

public class EnterInviteCodeContestActivity extends AppCompatActivity implements ResponseManager {

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    EnterInviteCodeContestActivity activity;
    Context context;
    String Code;
    String EntryFees,MyContestCode,  userWinners;
    List<BeanWiningInfoList> beanWinningLists;
    BottomSheetDialog dialog;
    String MatchId;
    ActivityEnterInviteCodeContestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_enter_invite_code_contest);

        context = activity = this;
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        MatchId = getIntent().getStringExtra("MatchId");

        binding.head.tvHeaderName.setText(getResources().getString(R.string.join_contest));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.tvJoinThisContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = binding.etInviteCodeforContest.getText().toString().trim();
                if (Code.equals("")){
                    ShowToast(context,"Enter Contest Invite Code");
                }
                else {
                    callLoadContest(true);
                }
            }
        });

        binding.inclAdapter.tvJoinContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContestListActivity.MyTeamEditorSave = "Save";

                binding.inclAdapter.tvJoinContest.setTextColor(Color.WHITE);
                binding.inclAdapter.tvJoinContest.setBackgroundResource(R.drawable.joinbutton_hover);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.inclAdapter.tvJoinContest.setTextColor(Color.parseColor("#1890b2"));
                        binding.inclAdapter.tvJoinContest.setBackgroundResource(R.drawable.joinbutton_back);
                    }
                }, 400);

                Intent i = new Intent(activity,JoinContestActivity.class);
                i.putExtra("EntryFee",EntryFees);
                i.putExtra("ContestCode",MyContestCode);
                startActivity(i);
            }
        });

        binding.inclAdapter.tvWinnersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWinningInfoList(true);
            }
        });

    }


    private void callWinningInfoList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(WINNINGINFOLIST,
                    createRequestJsonWin(), context, activity, WINNINGINFOLISTTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJsonWin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contest_id",ContestListActivity.ContestId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void callLoadContest(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(CREATEOWNCONTESTLIST,
                    createRequestJson2(), context, activity, CREATEOWNCONTESTLISTTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("unique_code", Code);
            jsonObject.put("userMatchid", MatchId);
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(WINNINGINFOLISTTYPE)){
            try {
                JSONArray jsonArray = result.getJSONArray("data");

                beanWinningLists = new Gson().fromJson(jsonArray.toString(),
                        new TypeToken<List<BeanWiningInfoList>>() {
                        }.getType());

                dialog = new BottomSheetDialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_winning_breakups);
                final TextView tv_DClose = dialog.findViewById(R.id.tv_DClose);
                final TextView tv_DTotalWinning =dialog.findViewById(R.id.tv_DTotalWinning);
                final TextView tv_DBottomNote =dialog.findViewById(R.id.tv_DBottomNote);
                final LinearLayout LL_WinningBreackupList=dialog.findViewById(R.id.LL_WinningBreackupList);
                dialog.show();
                tv_DTotalWinning.setText("₹ "+userWinners);
                tv_DBottomNote.setVisibility(View.GONE);
                tv_DClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                for (int i = 0; i <beanWinningLists .size(); i++) {

                    View to_add = LayoutInflater.from(context).inflate(R.layout.item_winning_breakup,
                            LL_WinningBreackupList,false);
                    TextView tv_Rank = to_add.findViewById(R.id.tv_Rank);
                    TextView tv_Price = to_add.findViewById(R.id.tv_Price);

                    tv_Rank.setText("Rank: "+beanWinningLists.get(i).getRank());
                    tv_Price.setText("₹ "+beanWinningLists.get(i).getPrice());

                    LL_WinningBreackupList.addView(to_add);
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }




        }else{
        try {
            binding.RLContestList.setVisibility(View.VISIBLE);
            ContestListActivity.ContestId = result.getString("user_Contestid");
            String userContestName = result.getString("userContestName");
             userWinners = result.getString("userWinners");
            String userTotalteam = result.getString("userTotalteam");
            String userJoinTeam = result.getString("userJoinTeam");
            EntryFees = result.getString("userEntry");
            String userMatchid = result.getString("userMatchid");
            MyContestCode = result.getString("unique_code");
            String Totalwinners = result.getString("userTotalWinners");


            binding.inclAdapter.tvTotalPrice.setText("₹ " + userWinners);
            binding.inclAdapter.tvWinnersCount.setText(Totalwinners);
            binding.inclAdapter.tvEntryFees.setText("₹ " + EntryFees);

            binding.inclAdapter.tvTeamLeftCount.setText(userJoinTeam + " Spots Left");
            binding.inclAdapter.tvTotalTeamCount.setText(userTotalteam + " Teams");
            binding.inclAdapter.PBEntryProgress.setMax(Integer.parseInt(userTotalteam));
            binding.inclAdapter.PBEntryProgress.setProgress(Integer.parseInt(userTotalteam) - Integer.parseInt(userJoinTeam));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    }



    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(context,message);
        binding.RLContestList.setVisibility(View.GONE);

    }
}
