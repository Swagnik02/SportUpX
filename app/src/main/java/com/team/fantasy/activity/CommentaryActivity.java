package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.NOTIFICATIONLIST;
import static com.team.fantasy.APICallingPackage.Constants.NOTIFICATIONTYPE;

import android.content.Context;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanBatterStats;
import com.team.fantasy.Bean.BeanCommentary;
import com.team.fantasy.R;
import com.team.fantasy.databinding.AcitivtyCommentaryBinding;
import com.team.fantasy.databinding.ActivityNotificationBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentaryActivity extends AppCompatActivity implements ResponseManager {
    CommentaryActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    AdapterCommentaryList adapterCommentaryList;
    AcitivtyCommentaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.acitivty_commentary);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

//        binding.RVNotification.setHasFixedSize(true);
//        binding.RVNotification.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
//        binding.RVNotification.setLayoutManager(mLayoutManager);
//        binding.RVNotification.setItemAnimator(new DefaultItemAnimator());


//        binding.swipeRefreshLayout.post(new Runnable() {
//        @Override
//        public void run() {
//        binding.swipeRefreshLayout.setRefreshing(true);
//        callAdapterCommentaryList(false);
//                }
//            }
//        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                callAdapterCommentaryList(false);
                ShowToast(context,"Refresh");
            }
        });


    }

    public void initViews(){
        binding.imCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void callAdapterCommentaryList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(NOTIFICATIONLIST,
                    createRequestJson(), context, activity, NOTIFICATIONTYPE,
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
//        binding.tvNoDataAvailable.setVisibility(View.GONE);
//        binding.RVNotification.setVisibility(View.VISIBLE);
//        binding.swipeRefreshLayout.setRefreshing(false);
//
        try {

            JSONArray data = result.getJSONArray("data");

            // Separate batsmen and bowlers data
            List<BeanCommentary> commentaryList = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);

                String inning = item.getString("Inning");
                String overs = item.getString("Over");
                String batsman = item.getString("Batsman");
                String bowler = item.getString("Bowler");
                String runs = item.getString("Runs");
                String intRuns = runs;

                if (runs == "FOUR") {
                    intRuns = "4";
                } else if (runs == "SIX") {
                    intRuns = "6";
                } else if (runs == "No Run") {
                    intRuns = "0";
                } else if (runs == "1 Run") {
                    intRuns = "1";
                } else if (runs == "2 Runs") {
                    intRuns = "2";
                }
                String commentary = bowler + " to" + batsman +", "+ runs +"!";

                BeanCommentary  commentaryB = new BeanCommentary (inning, overs, batsman, bowler, intRuns, commentary);
                commentaryList.add(commentaryB);
            }

            } catch (Exception e) {
            e.printStackTrace();
        }
//
//        adapterAdapterCommentaryList.notifyDataSetChanged();
//

    }



    @Override
    public void onError(Context mContext, String type, String message) {
//        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
//        binding.RVNotification.setVisibility(View.GONE);
//        binding.swipeRefreshLayout.setRefreshing(false);
    }


    public class AdapterCommentaryList extends RecyclerView.Adapter<AdapterCommentaryList.MyViewHolder> {
        private List<BeanCommentary> mListenerList;
        Context mContext;

        public AdapterCommentaryList(List<BeanCommentary> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvOvers, tvRuns, tvCommentary;

            public MyViewHolder(View view) {
                super(view);

                tvOvers = view.findViewById(R.id.im_overs);
                tvRuns = view.findViewById(R.id.im_runs);
                tvCommentary = view.findViewById(R.id.im_com_resp);
            }
        }

        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_commentry_response, parent, false); // Replace with your new layout XML file

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final String overs = mListenerList.get(position).getOver(); // Assuming you have a method getOvers()
            final String runs = mListenerList.get(position).getRuns(); // Assuming you have a method getRuns()
            final String commentary = mListenerList.get(position).getCommentary(); // Assuming you have a method getCommentary()

            holder.tvOvers.setText(overs);
            holder.tvRuns.setText(runs);
            holder.tvCommentary.setText(commentary);
        }
    }

}
