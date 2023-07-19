package com.team.fantasy.fragment;


import static com.team.fantasy.APICallingPackage.Config.MYFIXTURES;
import static com.team.fantasy.APICallingPackage.Constants.MYRESULTTYPE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanMyResult;
import com.team.fantasy.R;
import com.team.fantasy.activity.HomeActivity;
import com.team.fantasy.activity.MyJoinedResultContestListActivity;
import com.team.fantasy.databinding.FragmentMyResultsBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class FragmentMyResults extends Fragment implements ResponseManager {


    HomeActivity activity;
    Context context;
    AdapterMyResultList adapterMyResultList;

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;


    FragmentMyResultsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyResultsBinding.inflate(inflater, container, false);

        context = activity = (HomeActivity) getActivity();

        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.RvMyResult.setHasFixedSize(true);
        binding.RvMyResult.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.RvMyResult.setLayoutManager(mLayoutManager);
        binding.RvMyResult.setItemAnimator(new DefaultItemAnimator());


        binding.swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                binding.swipeRefreshLayout.setRefreshing(true);
                                                callMyResult(false);
                                            }
                                        }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callMyResult(false);
            }
        });


        return binding.getRoot();
    }


    private void callMyResult(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(MYFIXTURES,
                    createRequestJson(), context, activity, MYRESULTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", "Result");
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.tvNoDataAvailable.setVisibility(View.GONE);
        binding.RvMyResult.setVisibility(View.VISIBLE);


        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanMyResult> beanHomeResult = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanMyResult>>() {
                    }.getType());
            adapterMyResultList = new AdapterMyResultList(beanHomeResult, activity);
            binding.RvMyResult.setAdapter(adapterMyResultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapterMyResultList.notifyDataSetChanged();
    }


    @Override
    public void onError(Context mContext, String type, String message) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RvMyResult.setVisibility(View.GONE);
    }

    public class AdapterMyResultList extends RecyclerView.Adapter<AdapterMyResultList.MyViewHolder> {
        private List<BeanMyResult> mListenerList;
        Context mContext;

        public AdapterMyResultList(List<BeanMyResult> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_TeamOneName,tv_TeamsName,tv_TimeRemained,tv_TeamTwoName,tv_JoinedContestCount,tv_TeamOneScore,tv_TeamTwoScore,
                    tv_TeamOneOver,tv_TeamTwoOver,tv_MatchResult;
            LinearLayout linearLayout;
            ImageView im_Team1,im_Team2;


            public MyViewHolder(View view) {
                super(view);

                im_Team1 = view.findViewById(R.id.im_Team1);
                tv_TeamOneName = view.findViewById(R.id.tv_TeamOneName);
                tv_TeamsName = view.findViewById(R.id.tv_TeamsName);
                tv_TimeRemained = view.findViewById(R.id.tv_TimeRemained);
                im_Team2 = view.findViewById(R.id.im_Team2);
                tv_TeamTwoName = view.findViewById(R.id.tv_TeamTwoName);
                tv_JoinedContestCount = view.findViewById(R.id.tv_JoinedContestCount);
                tv_TeamOneScore=view.findViewById(R.id.tv_TeamOneScore);
                tv_TeamTwoScore=view.findViewById(R.id.tv_TeamTwoScore);
                tv_TeamOneOver=view.findViewById(R.id.tv_TeamOneOver);
                tv_TeamTwoOver=view.findViewById(R.id.tv_TeamTwoOver);
                tv_MatchResult=view.findViewById(R.id.tv_MatchResult);
                linearLayout=view.findViewById(R.id.linearlayout2);
                linearLayout.setVisibility(View.GONE);
                tv_MatchResult.setVisibility(View.GONE);

            }
        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_my_match_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final String match_id = mListenerList.get(position).getMatch_id();
            String teamid1 = mListenerList.get(position).getTeamid1();
            String match_status = mListenerList.get(position).getMatch_status();
            String type = mListenerList.get(position).getType();
            final int time = mListenerList.get(position).getTime();
            String teamid2 = mListenerList.get(position).getTeamid2();
            String team_name1 = mListenerList.get(position).getTeam_name1();
            final String team_image1 = mListenerList.get(position).getTeam_image1();
            final String team_short_name1 = mListenerList.get(position).getTeam_short_name1();
            String team_name2 = mListenerList.get(position).getTeam_name2();
            final String team_image2 = mListenerList.get(position).getTeam_image2();
            final String team_short_name2 = mListenerList.get(position).getTeam_short_name2();


            String contest_count = mListenerList.get(position).getContest_count();
            final String team_one_score=mListenerList.get(position).getTeam1Score();
            final String team_two_score=mListenerList.get(position).getTeam2Score();
            final String team_one_over=mListenerList.get(position).getTeam1Over();
            final  String team_two_over=mListenerList.get(position).getTeam2Over();
            final String match_status_note=mListenerList.get(position).getMatch_status_note();

            holder.tv_JoinedContestCount.setText(contest_count+" Contest Joined");

            holder.tv_TeamOneName.setText(team_short_name1);
            holder.tv_TeamTwoName.setText(team_short_name2);
            holder.tv_TeamsName.setText(type);
            Glide.with(getActivity()).load(Config.TEAMFLAGIMAGE+team_image1)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.im_Team1);
            Glide.with(getActivity()).load(Config.TEAMFLAGIMAGE+team_image2)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.im_Team2);

            if (match_status.equals("Result")){
                holder.tv_TimeRemained.setText("Completed");

            }

            holder.tv_TeamOneScore.setText("Score:-"+team_one_score);
            holder.tv_TeamTwoScore.setText("Score:-"+team_two_score);
            holder.tv_TeamOneOver.setText("Over:-"+team_one_over);
            holder.tv_TeamTwoOver.setText("Over:-"+team_two_over);
            holder.tv_MatchResult.setText(match_status_note);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent k = new Intent(activity, MyJoinedResultContestListActivity.class);
                    k.putExtra("MatchId",match_id);
                    k.putExtra("Time",time+"");
                    k.putExtra("TeamsName", holder.tv_TeamsName.getText().toString());
                    k.putExtra("TeamsOneName", team_short_name1);
                    k.putExtra("TeamsTwoName", team_short_name2);
                    k.putExtra("T1Image", team_image1);
                    k.putExtra("T2Image", team_image2);
                    startActivity(k);
                }
            });
        }
    }

}
