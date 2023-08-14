package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.MYLIVELEADERBOARD;
import static com.team.fantasy.APICallingPackage.Config.MYLIVELEADERBOARDTEAM;
import static com.team.fantasy.APICallingPackage.Config.WINNINGINFOLIST;
import static com.team.fantasy.APICallingPackage.Constants.MYLIVELEADERBORADTEAMTYPE;
import static com.team.fantasy.APICallingPackage.Constants.MYLIVELEADERBORADTYPE;
import static com.team.fantasy.APICallingPackage.Constants.WINNINGINFOLISTTYPE;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.team.fantasy.Bean.BeanWiningInfoList;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityMyLiveContestDetailsBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyLiveContestDetailsActivity extends AppCompatActivity implements ResponseManager {
    MyLiveContestDetailsActivity activity;
    Context context;
    AdapterLeaderboard adapterLeaderboard;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    String UserTeamId;
    BottomSheetDialog dialogGroundView=null;
    SessionManager sessionManager;
    String match_status,ApiUserId;
    List<BeanWiningInfoList> beanWinningLists;
    BottomSheetDialog dialog;
    String prize_pool,contest_description;
    Handler mHandler;
    boolean Add_View=true;
    ActivityMyLiveContestDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_live_contest_details);

        context = activity = this;
        initViews();
        sessionManager = new SessionManager();


        binding.inclVsBck.tvContestTimer.setText(MyJoinedLiveContestListActivity.IntentTime);
        binding.inclVsBck.tvHeadTeamOneName.setText(MyJoinedLiveContestListActivity.IntentTeamOneName);
        binding.inclVsBck.tvHeadTeamTwoName.setText(MyJoinedLiveContestListActivity.IntentTeamTwoName);

        Glide.with(activity).load(Config.TEAMFLAGIMAGE +MyJoinedLiveContestListActivity.IntentT1Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam1);
        Glide.with(activity).load(Config.TEAMFLAGIMAGE +MyJoinedLiveContestListActivity.IntentT2Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam2);

        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);


        binding.RvMyLiveLeaderboard.setHasFixedSize(true);
        binding.RvMyLiveLeaderboard.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RvMyLiveLeaderboard.setLayoutManager(mLayoutManager);
        binding.RvMyLiveLeaderboard.setItemAnimator(new DefaultItemAnimator());


        Validations.CountDownTimer(MyJoinedLiveContestListActivity.IntentTime,binding.inclVsBck.tvContestTimer);
        binding.inclVsBck.tvContestTimer.setText("In Progress");

        binding.tvTeamOneName.setText(MyJoinedLiveContestListActivity.IntentTeamOneName);
        binding.tvTeamTwoName.setText(MyJoinedLiveContestListActivity.IntentTeamTwoName);
        binding.tvWinnersCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callWinningInfoList(true);

            }
        });

        binding.swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    binding.swipeRefreshLayout.setRefreshing(true);
                                    callLeaderboardList(false);
                                }
                            }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callLeaderboardList(false);
            }
        });

    }
    public void initViews(){

        binding.head.tvHeaderName.setText(getResources().getString(R.string.contest));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }


    private void callWinningInfoList(boolean isShowLoader) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contest_id", MyJoinedLiveContestListActivity.ContestId);
            apiRequestManager.callAPI(WINNINGINFOLIST,
                    jsonObject, context, activity, WINNINGINFOLISTTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void callLeaderboardList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(MYLIVELEADERBOARD,
                    createRequestJson(), context, activity, MYLIVELEADERBORADTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contest_id", MyJoinedLiveContestListActivity.ContestId);
            jsonObject.put("match_id", MyJoinedLiveContestListActivity.IntentMatchId);
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
            jsonObject.put("match_id", MyJoinedLiveContestListActivity.Matchid);


            apiRequestManager.callAPI(MYLIVELEADERBOARDTEAM,
                   jsonObject, context, activity, MYLIVELEADERBORADTEAMTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(MYLIVELEADERBORADTEAMTYPE)){

            DialogGroundView(result);
        }
        else
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
                tv_DTotalWinning.setText("₹ "+prize_pool);
                tv_DBottomNote.setText("Note: "+contest_description);
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

        }
        

        else {

            binding.swipeRefreshLayout.setRefreshing(false);

            try {

                 prize_pool = result.getString("prize_pool");
                String entry = result.getString("entry");
                String all_team_count = result.getString("all_team_count");
                String user_team_count = result.getString("user_team_count");
                String winners = result.getString("winners");
                 contest_description = result.getString("contest_description");

                match_status = result.getString("match_status");
                String team_one_score=result.getString("team1Score");
                String team_two_score=result.getString("team2Score");
                String team_one_over=result.getString("team1Over");
                String team_two_over=result.getString("team2Over");

                String team1Score_secondInning=result.getString("team1Score_secondInning");
                String team1Over_secondInning=result.getString("team1Over_secondInning");
                String team2Score_secondInning=result.getString("team2Score_secondInning");
                String team2Over_secondInning=result.getString("team2Over_secondInning");

                String match_status_note=result.getString("match_status_note");

                binding.tvEntryFees.setText("₹ " + entry);
                binding.tvTotalPrice.setText("₹ " + prize_pool);
                binding.tvWinnersCount.setText(winners);
                binding.tvTotalJoinedTeamCount.setText(all_team_count +" Teams");
                binding.tvJoinedWithTeamTop.setText("Joined with "+all_team_count +" Teams");

                binding.tvTeamOneName.setText(MyJoinedLiveContestListActivity.IntentTeamOneName.trim());
                binding.tvTeamTwoName.setText(MyJoinedLiveContestListActivity.IntentTeamTwoName.trim());
                if (!team_one_over.equals("")){
                    binding.tvTeamOneScore.setText(""+team_one_score);
                    binding.tvTeamOneOver.setText("("+team_one_over+")");
                }
                if (!team_two_over.equals("")) {
                    binding.tvTeamTwoScore.setText("" + team_two_score);
                    binding.tvTeamTwoOver.setText("(" + team_two_over + ")");
                }

                if (!team_one_over.equals("")&&!team1Over_secondInning.equals("")){
                    binding.tvTeamOneScore.setText(team_one_score+" ("+team_one_over+")\n"+
                            team1Score_secondInning+" ("+team1Over_secondInning+")");
                    binding.tvTeamOneOver.setText("");
                }
                if (!team_two_over.equals("")&&!team2Over_secondInning.equals("")) {
                    binding.tvTeamTwoScore.setText(team_two_score+" (" + team_two_over + ")\n"+
                            team2Score_secondInning+" ("+team2Over_secondInning+")");
                    binding.tvTeamTwoOver.setText("");
                }




                binding.tvStatusNote.setText(match_status_note.trim());


                JSONArray jsonArray = result.getJSONArray("leaderboard");
                List<BeanMyFixLeaderboard> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                        new TypeToken<List<BeanMyFixLeaderboard>>() {
                        }.getType());
                adapterLeaderboard = new AdapterLeaderboard(beanContestLists, activity);
                binding.RvMyLiveLeaderboard.setAdapter(adapterLeaderboard);

            } catch (Exception e) {
                e.printStackTrace();
            }
            adapterLeaderboard.notifyDataSetChanged();
        }
    }


    @Override
    public void onError(Context mContext, String type, String message) {
        binding.swipeRefreshLayout.setRefreshing(false);

    }

    public class AdapterLeaderboard extends RecyclerView.Adapter<AdapterLeaderboard.MyViewHolder> {
        private List<BeanMyFixLeaderboard> mListenerList;
        Context mContext;


        public AdapterLeaderboard(List<BeanMyFixLeaderboard> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_LeaderboardPlayerTeamName,tv_LeaderboardPlayerRank,tv_LeaderboardPlayerPoints
                    ,tv_LeaderboardPlayerTeamCount;
            ImageView im_LeaderboardPlayerAvtar;
            RelativeLayout RL_LeaderboardMain;


            public MyViewHolder(View view) {
                super(view);

                im_LeaderboardPlayerAvtar = view.findViewById(R.id.im_LeaderboardPlayerAvtar);
                tv_LeaderboardPlayerTeamName = view.findViewById(R.id.tv_LeaderboardPlayerTeamName);
                tv_LeaderboardPlayerRank = view.findViewById(R.id.tv_LeaderboardPlayerRank);
                tv_LeaderboardPlayerPoints = view.findViewById(R.id.tv_LeaderboardPlayerPoints);
                RL_LeaderboardMain = view.findViewById(R.id.RL_LeaderboardMain);
                tv_LeaderboardPlayerTeamCount = view.findViewById(R.id.tv_LeaderboardPlayerTeamCount);
            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_live_leaderboard_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            ApiUserId = mListenerList.get(position).getUser_id();
            String name= mListenerList.get(position).getName();
            String rank= mListenerList.get(position).getRank();
            String id= mListenerList.get(position).getId();
            String Image= mListenerList.get(position).getImage();
            String Team= mListenerList.get(position).getTeam();
            String Points= mListenerList.get(position).getPoints();

            holder.tv_LeaderboardPlayerTeamName.setText(name+"");
            holder.tv_LeaderboardPlayerTeamCount.setText("(T"+Team+")");
            holder.tv_LeaderboardPlayerRank.setText(rank);
            holder.tv_LeaderboardPlayerPoints.setText(Points+" Points");

            Glide.with(activity).load(Config.ProfileIMAGEBASEURL+Image)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.bats_icon_hvr).placeholder(R.drawable.bats_icon_hvr)
                    .into(holder.im_LeaderboardPlayerAvtar);

            if (ApiUserId.equals(sessionManager.getUser(activity).getUser_id())){
                holder.RL_LeaderboardMain.setBackgroundResource(R.drawable.leaderboard_dark_rectangle);
                holder.tv_LeaderboardPlayerTeamName.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_LeaderboardPlayerPoints.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_LeaderboardPlayerRank.setTextColor(Color.parseColor("#ffffff"));
            }
            else {
                holder.RL_LeaderboardMain.setBackgroundResource(R.drawable.white_rectangle);
                holder.tv_LeaderboardPlayerTeamName.setTextColor(Color.parseColor("#1d2441"));
                holder.tv_LeaderboardPlayerPoints.setTextColor(Color.parseColor("#848484"));
                holder.tv_LeaderboardPlayerRank.setTextColor(Color.parseColor("#1d2441"));
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

    public void DialogGroundView(JSONObject result){
        if (dialogGroundView == null){
            dialogGroundView = new BottomSheetDialog(activity);
            dialogGroundView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogGroundView.setContentView(R.layout.dialog_ground_view); // initiate it the way you need
            dialogGroundView.show();

        }
        else if (!dialogGroundView.isShowing()){
            dialogGroundView.show();
        }


        final LinearLayout LL_GroundWK = dialogGroundView.findViewById(R.id.LL_GroundWK);
        final LinearLayout LL_GroundBAT = dialogGroundView.findViewById(R.id.LL_GroundBAT);
        final LinearLayout LL_GroundAR = dialogGroundView.findViewById(R.id.LL_GroundAR);
        final LinearLayout LL_GroundBOWL = dialogGroundView.findViewById(R.id.LL_GroundBOWL);
        ImageView im_CloseIcon = dialogGroundView.findViewById(R.id.im_CloseIcon);
        im_CloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogGroundView.dismiss();
                dialogGroundView=null;
                Add_View=true;
            }
        });

        dialogGroundView.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                // TODO Auto-generated method stub

                dialog.dismiss();
                dialogGroundView=null;
                Add_View=true;

            }
        });
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);

                String PlayerId = userData.getString("player_id");
                String IsSelected = userData.getString("is_select");
                String Role = userData.getString("short_term");
                String player_shortname = userData.getString("player_shortname");
                String PlayerImage = userData.getString("image");
                String PlayerCredit = userData.getString("credit_points");
                String PlayerPoint = userData.getString("points");
                String is_captain = userData.getString("is_captain");
                String is_vicecaptain = userData.getString("is_vicecaptain");
                if (is_captain==null){
                    is_captain = "0";
                }
                if (is_vicecaptain==null){
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
                        tv_GroundPlayerCredit.setText(PlayerPoint + " Pt");

                        if (is_captain.equals("1")){
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")){
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
                        tv_GroundPlayerCredit.setText(PlayerPoint + " Pt");
                        if (is_captain.equals("1")){
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")){
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
                            LL_GroundBAT.addView(to_add);
                        }
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
                        tv_GroundPlayerCredit.setText(PlayerPoint + " Pt");
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
                        tv_GroundPlayerCredit.setText(PlayerPoint + " Pt");
                        if (is_captain.equals("1")){
                            tv_CorVC.setVisibility(View.VISIBLE);
                            tv_CorVC.setText(" C ");
                        }
                        if (is_vicecaptain.equals("1")){
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
            Add_View=false;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
