package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanMyLiveJoinedContest;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityMyJoinedResultContestListBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Config.MYJOINRESULTCONTESTLIST;
import static com.team.fantasy.APICallingPackage.Constants.MYJOINRESULTCONTESTLISTTYPE;

public class MyJoinedResultContestListActivity extends AppCompatActivity implements ResponseManager {


    MyJoinedResultContestListActivity activity;
    Context context;
    AdapterMyJoinedContestList adapterMyJoinedContestList;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    public static String IntentMatchId, IntentTime, IntenTeamsName, IntentTeamOneName, IntentTeamTwoName
            ,IntentT1Image,IntentT2Image;

    public static String ContestId, Matchid;

    SessionManager sessionManager;


    ActivityMyJoinedResultContestListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_joined_result_contest_list);

        context = activity = this;
        initViews();

        sessionManager = new SessionManager();
        IntentMatchId = getIntent().getStringExtra("MatchId");
        IntentTime = getIntent().getStringExtra("Time");
        IntenTeamsName = getIntent().getStringExtra("TeamsName");
        IntentTeamOneName = getIntent().getStringExtra("TeamsOneName");
        IntentTeamTwoName = getIntent().getStringExtra("TeamsTwoName");
        IntentT1Image = getIntent().getStringExtra("T1Image");
        IntentT2Image = getIntent().getStringExtra("T2Image");


        binding.inclVsBck.tvHeadTeamOneName.setText(IntentTeamOneName);
        binding.inclVsBck.tvHeadTeamTwoName.setText(IntentTeamTwoName);
        binding.inclVsBck.tvContestTimer.setText(IntentTime);

        Glide.with(activity).load(Config.TEAMFLAGIMAGE +IntentT1Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam1);
        Glide.with(activity).load(Config.TEAMFLAGIMAGE +IntentT2Image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.inclVsBck.imTeam2);


        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);


        binding.RvMyJoinedContestList.setHasFixedSize(true);
        binding.RvMyJoinedContestList.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RvMyJoinedContestList.setLayoutManager(mLayoutManager);
        binding.RvMyJoinedContestList.setItemAnimator(new DefaultItemAnimator());


        Validations.CountDownTimer(IntentTime, binding.inclVsBck.tvContestTimer);
        binding.inclVsBck.tvContestTimer.setText("Completed");


        binding.swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                binding.swipeRefreshLayout.setRefreshing(true);
                                                callMyJoinedContestList(false);
                                            }
                                        }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callMyJoinedContestList(false);
            }
        });

    }

    public void initViews() {


        binding.head.tvHeaderName.setText(getResources().getString(R.string.join_contest));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void callMyJoinedContestList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(MYJOINRESULTCONTESTLIST,
                    createRequestJson(), context, activity, MYJOINRESULTCONTESTLISTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("match_id", IntentMatchId);
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        try {

            binding.tvNoDataAvailable.setVisibility(View.GONE);
            binding.RvMyJoinedContestList.setVisibility(View.VISIBLE);
            binding.swipeRefreshLayout.setRefreshing(false);

            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanMyLiveJoinedContest> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanMyLiveJoinedContest>>() {
                    }.getType());
            adapterMyJoinedContestList = new AdapterMyJoinedContestList(beanContestLists, activity);
            binding.RvMyJoinedContestList.setAdapter(adapterMyJoinedContestList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterMyJoinedContestList.notifyDataSetChanged();


    }


    @Override
    public void onError(Context mContext, String type, String message) {
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RvMyJoinedContestList.setVisibility(View.GONE);
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    public class AdapterMyJoinedContestList extends RecyclerView.Adapter<AdapterMyJoinedContestList.MyViewHolder> {
        private List<BeanMyLiveJoinedContest> mListenerList;
        Context mContext;


        public AdapterMyJoinedContestList(List<BeanMyLiveJoinedContest> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_LiveContestName,tv_LiveContestDesc,tv_LiveContestFees,
                    tv_LiveJoinedWith,tv_ContestPoints
                    ,tv_ContestRank,tv_WinningAmount ;

            public MyViewHolder(View view) {
                super(view);

                tv_LiveContestName = view.findViewById(R.id.tv_LiveContestName);
                tv_LiveContestDesc = view.findViewById(R.id.tv_LiveContestDesc);
                tv_LiveContestFees = view.findViewById(R.id.tv_LiveContestFees);
                tv_LiveJoinedWith = view.findViewById(R.id.tv_LiveJoinedWith);
                tv_ContestPoints = view.findViewById(R.id.tv_ContestPoints);
                tv_ContestRank = view.findViewById(R.id.tv_ContestRank);
                tv_WinningAmount = view.findViewById(R.id.tv_WinningAmount);


            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_live_contest_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            final String contest_id = mListenerList.get(position).getContest_id();
            String contest_name= mListenerList.get(position).getContest_name();
            String contest_tag= mListenerList.get(position).getContest_tag();
            String winners= mListenerList.get(position).getWinners();

            String total_team= mListenerList.get(position).getTotal_team();
            String join_team= mListenerList.get(position).getJoin_team();
            String entry= mListenerList.get(position).getEntry();

            String contest_note1 = mListenerList.get(position).getContest_note1();
            String contest_note2= mListenerList.get(position).getContest_note2();
            String match_id= mListenerList.get(position).getMatch_id();
            String type= mListenerList.get(position).getType();
            String remaining_team= mListenerList.get(position).getRemaining_team();
            String points= mListenerList.get(position).getPoints();
            String rank= mListenerList.get(position).getRank();
            String JoinedWithTeamName = mListenerList.get(position).getTeam_name();
            String winning_amount = mListenerList.get(position).getWinning_amount();

            holder.tv_WinningAmount.setVisibility(View.VISIBLE);
            holder.tv_WinningAmount.setText("₹ "+winning_amount);

            holder.tv_LiveContestName.setText(contest_name);
            holder.tv_LiveContestDesc.setText(contest_tag);
            holder.tv_LiveContestFees.setText("₹ "+entry);
            holder.tv_ContestRank.setText(rank);
            holder.tv_ContestPoints.setText(points);
            holder.tv_LiveJoinedWith.setText(JoinedWithTeamName);



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Matchid = IntentMatchId;
                    ContestId = mListenerList.get(position).getContest_id();
                    Intent i = new Intent(activity, MyResultContestDetailsActivity.class);
                    startActivity(i);
                }
            });
        }

    }
}
