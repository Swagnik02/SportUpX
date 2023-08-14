package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.MYFIXTURELEADERBOARD;
import static com.team.fantasy.APICallingPackage.Config.MYFIXTURELEADERBOARDTEAM;
import static com.team.fantasy.APICallingPackage.Constants.MYFIXTURELEADERBORADTEAMTYPE;
import static com.team.fantasy.APICallingPackage.Constants.MYFIXTURELEADERBORADTYPE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanMyFixLeaderboard;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityMyFixtureContestDetailsBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyFixtureContestDetailsActivity extends AppCompatActivity implements ResponseManager {


    MyFixtureContestDetailsActivity activity;
    Context context;

    AdapterLeaderboard adapterLeaderboard;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;


    boolean Add_View = true;
    String UserTeamId;
    BottomSheetDialog dialogGroundView = null;
    String match_status, ApiUserId;
    SessionManager sessionManager;

    ActivityMyFixtureContestDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_fixture_contest_details);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();


        binding.inclVsBck.tvContestTimer.setText(MyJoinedFixtureContestListActivity.IntentTime);

        binding.inclVsBck.tvHeadTeamOneName.setText(MyJoinedFixtureContestListActivity.IntentTeamOneName);
        binding.inclVsBck.tvHeadTeamTwoName.setText(MyJoinedFixtureContestListActivity.IntentTeamTwoName);

        Glide.with(activity).load(Config.TEAMFLAGIMAGE +MyJoinedFixtureContestListActivity.IntentT1Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam1);
        Glide.with(activity).load(Config.TEAMFLAGIMAGE +MyJoinedFixtureContestListActivity.IntentT2Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam2);

        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);


        binding.RvMyFixLeaderboard.setHasFixedSize(true);
        binding.RvMyFixLeaderboard.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RvMyFixLeaderboard.setLayoutManager(mLayoutManager);
        binding.RvMyFixLeaderboard.setItemAnimator(new DefaultItemAnimator());

        callLeaderboardList(true);

        Validations.CountDownTimer(MyJoinedFixtureContestListActivity.IntentTime, binding.inclVsBck.tvContestTimer);


    }

    public void initViews() {


        binding.head.tvHeaderName.setText("CONTESTS");
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }


    private void callLeaderboardList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(MYFIXTURELEADERBOARD,
                    createRequestJson(), context, activity, MYFIXTURELEADERBORADTYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contest_id", MyJoinedFixtureContestListActivity.ContestId);
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void callLeaderboardTeam(boolean isShowLoader) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("team_id", UserTeamId);
            jsonObject.put("match_id", MyJoinedFixtureContestListActivity.Matchid);

            apiRequestManager.callAPI(MYFIXTURELEADERBOARDTEAM,
                    jsonObject, context, activity, MYFIXTURELEADERBORADTEAMTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(MYFIXTURELEADERBORADTEAMTYPE)) {

            DialogGroundView(result);
        } else {

            try {
                String prize_pool = result.getString("prize_pool");
                String entry = result.getString("entry");
                String all_team_count = result.getString("all_team_count");
                String user_team_count = result.getString("user_team_count");
                match_status = result.getString("match_status");

                binding.tvEntryFess.setText("₹ " + entry);
                binding.tvTotalWinning.setText("₹ " + prize_pool);
                binding.tvTotalJoinedTeamCount.setText(all_team_count + " Teams");
                binding.tvJoinedWithTeam.setText(user_team_count + " Teams");


                JSONArray jsonArray = result.getJSONArray("leaderboard");
                List<BeanMyFixLeaderboard> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                        new TypeToken<List<BeanMyFixLeaderboard>>() {
                        }.getType());
                adapterLeaderboard = new AdapterLeaderboard(beanContestLists, activity);
                binding.RvMyFixLeaderboard.setAdapter(adapterLeaderboard);

            } catch (Exception e) {
                e.printStackTrace();
            }
            adapterLeaderboard.notifyDataSetChanged();
        }
    }


    @Override
    public void onError(Context mContext, String type, String message) {
    }

    public class AdapterLeaderboard extends RecyclerView.Adapter<AdapterLeaderboard.MyViewHolder> {
        private List<BeanMyFixLeaderboard> mListenerList;
        Context mContext;


        public AdapterLeaderboard(List<BeanMyFixLeaderboard> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_LeaderboardPlayerTeamName,tv_LeaderboardPlayerRank;
            ImageView im_LeaderboardPlayerAvtar;
            RelativeLayout RL_LeaderboardMain;

            public MyViewHolder(View view) {
                super(view);

                im_LeaderboardPlayerAvtar = view.findViewById(R.id.im_LeaderboardPlayerAvtar);
                tv_LeaderboardPlayerTeamName = view.findViewById(R.id.tv_LeaderboardPlayerTeamName);
                tv_LeaderboardPlayerRank = view.findViewById(R.id.tv_LeaderboardPlayerRank);
                RL_LeaderboardMain = view.findViewById(R.id.RL_LeaderboardMain);
            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_leaderboard_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            ApiUserId = mListenerList.get(position).getUser_id();
            String name= mListenerList.get(position).getName();
            String rank= mListenerList.get(position).getRank();
            String id= mListenerList.get(position).getId();
            String Image= mListenerList.get(position).getImage();
            String Team= mListenerList.get(position).getTeam();


            holder.tv_LeaderboardPlayerTeamName.setText(name+"(T"+Team+")");
            holder.tv_LeaderboardPlayerRank.setText(rank);

            Glide.with(activity).load(Config.ProfileIMAGEBASEURL+Image)
                    .crossFade()
                    .error(R.drawable.bats_icon_hvr).placeholder(R.drawable.bats_icon_hvr)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.im_LeaderboardPlayerAvtar);


            if (ApiUserId.equals(sessionManager.getUser(activity).getUser_id())){
                holder.RL_LeaderboardMain.setBackgroundResource(R.drawable.leaderboard_dark_rectangle);
                holder.tv_LeaderboardPlayerTeamName.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_LeaderboardPlayerRank.setTextColor(Color.parseColor("#ffffff"));
            }
            else {
                holder.RL_LeaderboardMain.setBackgroundResource(R.drawable.white_rectangle);
                holder.tv_LeaderboardPlayerTeamName.setTextColor(Color.parseColor("#1e1e1e"));
                holder.tv_LeaderboardPlayerRank.setTextColor(Color.parseColor("#1e1e1e"));
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserTeamId = mListenerList.get(position).getTeamid();
                    ApiUserId = mListenerList.get(position).getUser_id();
                    if (ApiUserId.equals(sessionManager.getUser(activity).getUser_id())){
                        callLeaderboardTeam(true);
                    }
                    else {
                        if (match_status.equals("1")){
                            callLeaderboardTeam(true);
                        }
                        else {
                            ShowToast(context,"Please Wait! Match has not started yet.");
                        }
                    }
                }
            });
        }

    }

    public void DialogGroundView(JSONObject result) {
        if (dialogGroundView == null) {
            dialogGroundView = new BottomSheetDialog(activity);
            dialogGroundView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogGroundView.setContentView(R.layout.dialog_ground_view); // initiate it the way you need
            dialogGroundView.show();

        } else if (!dialogGroundView.isShowing()) {
            dialogGroundView.show();
        }


        final LinearLayout LL_GroundWK = dialogGroundView.findViewById(R.id.LL_GroundWK);
        final LinearLayout LL_GroundBAT = dialogGroundView.findViewById(R.id.LL_GroundBAT);
        final LinearLayout LL_GroundAR = dialogGroundView.findViewById(R.id.LL_GroundAR);
        final LinearLayout LL_GroundBOWL = dialogGroundView.findViewById(R.id.LL_GroundBOWL);
        ImageView im_CloseIcon = dialogGroundView.findViewById(R.id.im_CloseIcon);
        im_CloseIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogGroundView.dismiss();
                        dialogGroundView = null;
                        Add_View = true;

                    }
                });
        dialogGroundView.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub

                dialog.dismiss();
                dialogGroundView = null;
                Add_View = true;

            }
        });
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            Log.e("jsonArray", jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);

                String PlayerId = userData.getString("player_id");
                String IsSelected = userData.getString("is_select");
                String Role = userData.getString("short_term");
                String player_shortname = userData.getString("player_shortname");
                String PlayerImage = userData.getString("image");
                String PlayerCredit = userData.getString("credit_points");
                String is_captain = userData.getString("is_captain");
                String is_vicecaptain = userData.getString("is_vicecaptain");
                if (is_captain == null) {
                    is_captain = "0";
                }
                if (is_vicecaptain == null) {
                    is_vicecaptain = "0";
                }

                if (IsSelected.equals("1")) {
                    if (Role.equals("WK")) {
                        View to_add = LayoutInflater.from(context).inflate(R.layout.item_ground_player,
                                LL_GroundWK, false);
                        ImageView im_GroundPlayerImage = to_add.findViewById(R.id.im_GroundPlayerImage);
                        TextView tv_GroundPlayerName = to_add.findViewById(R.id.tv_GroundPlayerName);
                        TextView tv_GroundPlayerCredit = to_add.findViewById(R.id.tv_GroundPlayerCredit);
                        TextView tv_CorVC = to_add.findViewById(R.id.tv_CorVC);

                        Glide.with(activity).load(Config.PLAYERIMAGE + PlayerImage)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(im_GroundPlayerImage);
                        tv_GroundPlayerName.setText(player_shortname);
                        tv_GroundPlayerCredit.setText(PlayerCredit + " Cr");

                        if (is_captain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText("VC");
                        }
                        // LL_GroundWK.addView(to_add);
                        if (Add_View) {
                            // Add padding to the player view
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            int marginInPixels = getResources().getDimensionPixelSize(R.dimen.player_view_margin);
                            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                            to_add.setLayoutParams(layoutParams);

                            LL_GroundWK.addView(to_add);
                        }

                    } else if (Role.equals("BAT")) {
                        View to_add = LayoutInflater.from(context).inflate(R.layout.item_ground_player,
                                LL_GroundBAT, false);
                        ImageView im_GroundPlayerImage = to_add.findViewById(R.id.im_GroundPlayerImage);
                        TextView tv_GroundPlayerName = to_add.findViewById(R.id.tv_GroundPlayerName);
                        TextView tv_GroundPlayerCredit = to_add.findViewById(R.id.tv_GroundPlayerCredit);
                        TextView tv_CorVC = to_add.findViewById(R.id.tv_CorVC);

                        Glide.with(activity).load(Config.PLAYERIMAGE + PlayerImage)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(im_GroundPlayerImage);

                        tv_GroundPlayerName.setText(player_shortname);
                        tv_GroundPlayerCredit.setText(PlayerCredit + " Cr");



                        if (is_captain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText("VC");
                        }
                        //LL_GroundBAT.addView(to_add);
                        if (Add_View) {
                            // Add padding to the player view
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            int marginInPixels = getResources().getDimensionPixelSize(R.dimen.player_view_margin);
                            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                            to_add.setLayoutParams(layoutParams);

                            LL_GroundBAT.addView(to_add);
                        }

                        // Add padding to the player view
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        int marginInPixels = getResources().getDimensionPixelSize(R.dimen.player_view_margin);
                        layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                        to_add.setLayoutParams(layoutParams);

                    } else if (Role.equals("AR")) {
                        View to_add = LayoutInflater.from(context).inflate(R.layout.item_ground_player,
                                LL_GroundAR, false);
                        ImageView im_GroundPlayerImage = to_add.findViewById(R.id.im_GroundPlayerImage);
                        TextView tv_GroundPlayerName = to_add.findViewById(R.id.tv_GroundPlayerName);
                        TextView tv_GroundPlayerCredit = to_add.findViewById(R.id.tv_GroundPlayerCredit);
                        TextView tv_CorVC = to_add.findViewById(R.id.tv_CorVC);

                        Glide.with(activity).load(Config.PLAYERIMAGE + PlayerImage)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(im_GroundPlayerImage);
                        tv_GroundPlayerName.setText(player_shortname);
                        tv_GroundPlayerCredit.setText(PlayerCredit + " Cr");

                        if (is_captain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText("VC");
                        }
                        // LL_GroundAR.addView(to_add);
                        if (Add_View) {

                            // Add padding to the player view
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            int marginInPixels = getResources().getDimensionPixelSize(R.dimen.player_view_margin);
                            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                            to_add.setLayoutParams(layoutParams);

                            LL_GroundAR.addView(to_add);
                        }
                    } else if (Role.equals("BOWL")) {
                        View to_add = LayoutInflater.from(context).inflate(R.layout.item_ground_player,
                                LL_GroundBOWL, false);
                        ImageView im_GroundPlayerImage = to_add.findViewById(R.id.im_GroundPlayerImage);
                        TextView tv_GroundPlayerName = to_add.findViewById(R.id.tv_GroundPlayerName);
                        TextView tv_GroundPlayerCredit = to_add.findViewById(R.id.tv_GroundPlayerCredit);
                        TextView tv_CorVC = to_add.findViewById(R.id.tv_CorVC);

                        Glide.with(activity).load(Config.PLAYERIMAGE + PlayerImage)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(im_GroundPlayerImage);
                        tv_GroundPlayerName.setText(player_shortname);
                        tv_GroundPlayerCredit.setText(PlayerCredit + " Cr");

                        if (is_captain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")) {
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText("VC");
                        }
                        if (Add_View) {
                            // Add padding to the player view
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            int marginInPixels = getResources().getDimensionPixelSize(R.dimen.player_view_margin);
                            layoutParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
                            to_add.setLayoutParams(layoutParams);

                            LL_GroundBOWL.addView(to_add);

                        }
                    }
                }

            }
            Add_View = false;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
