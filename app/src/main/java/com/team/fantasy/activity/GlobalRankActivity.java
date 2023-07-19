package com.team.fantasy.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityGlobalRankBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanGlobalRankingList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Config.GLOBALRANKINGLIST;
import static com.team.fantasy.APICallingPackage.Constants.GLOBALRANKINGTYPE;

public class GlobalRankActivity extends AppCompatActivity implements ResponseManager {


    GlobalRankActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    SessionManager sessionManager;
    AdapterGlobalRankList adapterGlobalRankList;

    ActivityGlobalRankBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_global_rank);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.RVGlobalRankList.setHasFixedSize(true);
        binding.RVGlobalRankList.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RVGlobalRankList.setLayoutManager(mLayoutManager);
        binding.RVGlobalRankList.setItemAnimator(new DefaultItemAnimator());
        binding.swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.swipeRefreshLayout.setRefreshing(true);
                                        callGlobalRanking(false);
                                    }
                                }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callGlobalRanking(false);
            }
        });

    }

    public void initViews(){


        binding.head.tvHeaderName.setText(getResources().getString(R.string.ur_global_rank));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void callGlobalRanking(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(GLOBALRANKINGLIST,
                    createRequestJson(), context, activity, GLOBALRANKINGTYPE,
                    isShowLoader,responseManager);

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

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        binding.tvNoDataAvailable.setVisibility(View.GONE);
        binding.RVGlobalRankList.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(false);

        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanGlobalRankingList> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanGlobalRankingList>>() {
                    }.getType());
            adapterGlobalRankList = new AdapterGlobalRankList(beanContestLists, activity);
            binding.RVGlobalRankList.setAdapter(adapterGlobalRankList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterGlobalRankList.notifyDataSetChanged();


    }



    @Override
    public void onError(Context mContext, String type, String message) {
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RVGlobalRankList.setVisibility(View.GONE);
        binding.swipeRefreshLayout.setRefreshing(false);
    }
    public class AdapterGlobalRankList extends RecyclerView.Adapter<AdapterGlobalRankList.MyViewHolder> {
        private List<BeanGlobalRankingList> mListenerList;
        Context mContext;


        public AdapterGlobalRankList(List<BeanGlobalRankingList> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_RankTeamName,tv_RankRank,tv_RankPoints ;


            public MyViewHolder(View view) {
                super(view);

                tv_RankTeamName = view.findViewById(R.id.tv_RankTeamName);
                tv_RankRank = view.findViewById(R.id.tv_RankRank);
                tv_RankPoints = view.findViewById(R.id.tv_RankPoints);

            }
        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_global_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final String TeamName = mListenerList.get(position).getName();
            String Points = mListenerList.get(position).getPoints();
            String Rank = mListenerList.get(position).getRank();


            holder.tv_RankTeamName.setText(TeamName);
            holder.tv_RankRank.setText("#"+Rank);
            holder.tv_RankPoints.setText(Points+" Points");
        }

    }
}
