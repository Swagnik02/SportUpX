package com.team.fantasy.fragment;


import static com.team.fantasy.APICallingPackage.Config.MYFIXTURES;
import static com.team.fantasy.APICallingPackage.Constants.MYFIXTURESTYPE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.team.fantasy.Bean.BeanMyFixtures;
import com.team.fantasy.R;
import com.team.fantasy.activity.ContestListActivity;
import com.team.fantasy.activity.HomeActivity;
import com.team.fantasy.activity.MyJoinedFixtureContestListActivity;
import com.team.fantasy.databinding.FragmentMyFixturesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class FragmentMyFixtures extends Fragment implements ResponseManager {
    HomeActivity activity;
    Context context;
    AdapterFixturesList adapterFixturesList;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    FragmentMyFixturesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyFixturesBinding.inflate(inflater, container, false);
        context = activity = (HomeActivity) getActivity();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        binding.RvMyFixtures.setHasFixedSize(true);
        binding.RvMyFixtures.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.RvMyFixtures.setLayoutManager(mLayoutManager);
        binding.RvMyFixtures.setItemAnimator(new DefaultItemAnimator());

        binding.swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                binding.swipeRefreshLayout.setRefreshing(true);
                                                callMyFixtures(false);
                                            }
                                        }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callMyFixtures(false);
            }
        });


        return binding.getRoot();
    }


    private void callMyFixtures(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(MYFIXTURES,
                    createRequestJson(), context, activity, MYFIXTURESTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", "Fixture");
            jsonObject.put("user_id", HomeActivity.sessionManager.getUser(context).getUser_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        binding.swipeRefreshLayout.setRefreshing(false);
        binding.tvNoDataAvailable.setVisibility(View.GONE);
        binding.RvMyFixtures.setVisibility(View.VISIBLE);

        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanMyFixtures> beanHomeFixtures = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanMyFixtures>>() {
                    }.getType());
            adapterFixturesList = new AdapterFixturesList(beanHomeFixtures, activity);
            binding.RvMyFixtures.setAdapter(adapterFixturesList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterFixturesList.notifyDataSetChanged();

    }


    @Override
    public void onError(Context mContext, String type, String message) {
        binding.swipeRefreshLayout.setRefreshing(false);
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RvMyFixtures.setVisibility(View.GONE);

    }

    public class AdapterFixturesList extends RecyclerView.Adapter<AdapterFixturesList.MyViewHolder> {
        private List<BeanMyFixtures> mListenerList;
        Context mContext;


        public AdapterFixturesList(List<BeanMyFixtures> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_TeamOneName, tv_TeamsName, tv_TimeRemained, tv_TeamTwoName, tv_JoinedContestCount, tv_MatchTime;
            LinearLayout linearLayout;
            ImageView im_Team1, im_Team2;
            CountDownTimer countDownTimer;

            public MyViewHolder(View view) {
                super(view);

                im_Team1 = view.findViewById(R.id.im_Team1);
                tv_TeamOneName = view.findViewById(R.id.tv_TeamOneName);
                tv_TeamsName = view.findViewById(R.id.tv_TeamsName);
                tv_TimeRemained = view.findViewById(R.id.tv_TimeRemained);
                im_Team2 = view.findViewById(R.id.im_Team2);
                tv_TeamTwoName = view.findViewById(R.id.tv_TeamTwoName);
                tv_JoinedContestCount = view.findViewById(R.id.tv_JoinedContestCount);
                tv_MatchTime = view.findViewById(R.id.tv_MatchTime);
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
            final String eleven_out = mListenerList.get(position).getEleven_out();

            if (eleven_out.equals("1")) {
                holder.tv_MatchTime.setVisibility(View.VISIBLE);
                holder.tv_MatchTime.setText("Lineup Out");
            } else {
                holder.tv_MatchTime.setVisibility(View.GONE);
            }

            holder.tv_JoinedContestCount.setText("Joined with " + contest_count + " Team");
            holder.tv_TeamOneName.setText(team_short_name1);
            holder.tv_TeamTwoName.setText(team_short_name2);
            holder.tv_TeamsName.setText(type);

            Glide.with(getActivity()).load(Config.TEAMFLAGIMAGE + team_image1)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.im_Team1);
            Glide.with(getActivity()).load(Config.TEAMFLAGIMAGE + team_image2)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.im_Team2);

            if (match_status.equals("Fixture")) {
                holder.tv_TimeRemained.setCompoundDrawablesWithIntrinsicBounds(R.drawable.watch_icon, 0, 0, 0);
                holder.tv_TimeRemained.setText(time + "");


                if (holder.countDownTimer != null) {
                    holder.countDownTimer.cancel();
                }

                try {

                    int FlashCount = time;
                    long FlashMiliSec = FlashCount * 1000;

                    holder.countDownTimer = new CountDownTimer(FlashMiliSec, 1000) {

                        public void onTick(long millisUntilFinished) {

                            long Days = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                            long Hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                            long Minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                            long Seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                            String format = "%1$02d";
                            holder.tv_TimeRemained.setText(String.format(format, Days) + ":" + String.format(format, Hours) + ":" + String.format(format, Minutes) + ":" + String.format(format, Seconds));

                        }

                        public void onFinish() {
                            holder.tv_TimeRemained.setText("Entry Over!");
                        }

                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent k = new Intent(activity, MyJoinedFixtureContestListActivity.class);
                        k.putExtra("MatchId", match_id);
                        k.putExtra("Time", time + "");
                        ContestListActivity.IntentTime = String.valueOf(time);
                        k.putExtra("TeamsName", holder.tv_TeamsName.getText().toString());
                        ContestListActivity.IntenTeamsName = holder.tv_TeamsName.getText().toString();
                        k.putExtra("TeamsOneName", team_short_name1);
                        ContestListActivity.IntentTeamOneName = team_short_name1;
                        k.putExtra("TeamsTwoName", team_short_name2);
                        ContestListActivity.IntentTeamTwoName = team_short_name2;
                        k.putExtra("T1Image", team_image1);
                        k.putExtra("T2Image", team_image2);
                        startActivity(k);
                    }
                });


            }

        }
    }

}
