package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.LIVE_MATCH_COMMENTARY;
import static com.team.fantasy.APICallingPackage.Config.NOTIFICATIONLIST;
import static com.team.fantasy.APICallingPackage.Constants.LIVE_MATCH_COMMENTARY_TYPE;
import static com.team.fantasy.APICallingPackage.Constants.NOTIFICATIONTYPE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.team.fantasy.adapter.BatsmanAdapter;
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
    String match_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.acitivty_commentary);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        match_id = getIntent().getStringExtra("Match_ID");

        ShowToast(context,match_id);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);

        binding.recyclerViewCommentary.setLayoutManager(new LinearLayoutManager(this));

//        simulateApiResponse();
        callAdapterCommentaryList(false);

        binding.tvComRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                simulateApiResponse();
//                callAdapterCommentaryList(false);
//                ShowToast(context, "Refreshed");
            }
        });


    }

    public void initViews() {
        binding.imCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                onBackPressed();
            }
        });
    }

    private void simulateApiResponse() {
        ShowToast(context, "simulateApiCalled");

        try {
            String dummyJson = "{\n" +
                    "  \"data\": [\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.1\",\n" +
                    "      \"Batsman\": \"Player A\",\n" +
                    "      \"Bowler\": \"Player X\",\n" +
                    "      \"Runs\": \"FOUR\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.2\",\n" +
                    "      \"Batsman\": \"Player A\",\n" +
                    "      \"Bowler\": \"Player X\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.3\",\n" +
                    "      \"Batsman\": \"Player A\",\n" +
                    "      \"Bowler\": \"Player X\",\n" +
                    "      \"Runs\": \"No Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.4\",\n" +
                    "      \"Batsman\": \"Player A\",\n" +
                    "      \"Bowler\": \"Player X\",\n" +
                    "      \"Runs\": \"2 Runs\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.5\",\n" +
                    "      \"Batsman\": \"Player A\",\n" +
                    "      \"Bowler\": \"Player X\",\n" +
                    "      \"Runs\": \"Wicket\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"1.6\",\n" +
                    "      \"Batsman\": \"Player B\",\n" +
                    "      \"Bowler\": \"Player Y\",\n" +
                    "      \"Runs\": \"SIX\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"2.1\",\n" +
                    "      \"Batsman\": \"Player B\",\n" +
                    "      \"Bowler\": \"Player Y\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"2.2\",\n" +
                    "      \"Batsman\": \"Player B\",\n" +
                    "      \"Bowler\": \"Player Y\",\n" +
                    "      \"Runs\": \"0\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"2.3\",\n" +
                    "      \"Batsman\": \"Player B\",\n" +
                    "      \"Bowler\": \"Player Y\",\n" +
                    "      \"Runs\": \"4\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"2.4\",\n" +
                    "      \"Batsman\": \"Player B\",\n" +
                    "      \"Bowler\": \"Player Y\",\n" +
                    "      \"Runs\": \"Wicket\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"2.5\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"SIX\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"2.6\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.1\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"4\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.2\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.3\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"2 Runs\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.4\",\n" +
                    "      \"Batsman\": \"Player C\",\n" +
                    "      \"Bowler\": \"Player Z\",\n" +
                    "      \"Runs\": \"SIX\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.5\",\n" +
                    "      \"Batsman\": \"Player D\",\n" +
                    "      \"Bowler\": \"Player A\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"1\",\n" +
                    "      \"Over\": \"3.6\",\n" +
                    "      \"Batsman\": \"Player D\",\n" +
                    "      \"Bowler\": \"Player A\",\n" +
                    "      \"Runs\": \"4\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"4.1\",\n" +
                    "      \"Batsman\": \"Player D\",\n" +
                    "      \"Bowler\": \"Player A\",\n" +
                    "      \"Runs\": \"0\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"Inning\": \"2\",\n" +
                    "      \"Over\": \"4.2\",\n" +
                    "      \"Batsman\": \"Player D\",\n" +
                    "      \"Bowler\": \"Player A\",\n" +
                    "      \"Runs\": \"1 Run\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";
            JSONObject result = new JSONObject(dummyJson);

            // Call your getResult method with the dummy data
            getResult(context, LIVE_MATCH_COMMENTARY_TYPE, "Success", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callAdapterCommentaryList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(LIVE_MATCH_COMMENTARY,
                    createRequestJson(), context, activity, LIVE_MATCH_COMMENTARY_TYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("match_id", "36");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        try {

            JSONArray data = result.getJSONArray("data");

            List<BeanCommentary> commentaryList = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);

                String inning = item.getString("Inning");
                String overs = item.getString("Over");
                String batsman = item.getString("Batsman");
                String bowler = item.getString("Bowler");
                String runs = item.getString("Runs");
                String intRuns = runs;

                if (runs.equals("FOUR")) {
                    intRuns = "4";
                } else if (runs.equals("SIX")) {
                    intRuns = "6";
                } else if (runs.equals("No Run")) {
                    intRuns = "0";
                } else if (runs.equals("1 Run")) {
                    intRuns = "1";
                } else if (runs.equals("2 Runs")) {
                    intRuns = "2";
                } else if (runs.equals("Wicket")) {
                    intRuns = "W";
                } else{

                }
                String commentary = bowler + " to " + batsman + ", " + runs + "!";

                BeanCommentary commentaryB = new BeanCommentary(inning, overs, batsman, bowler, intRuns, commentary);
                commentaryList.add(commentaryB);
            }
            adapterCommentaryList = new AdapterCommentaryList(this, commentaryList);
            binding.recyclerViewCommentary.setAdapter(adapterCommentaryList);
            adapterCommentaryList.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onError(Context mContext, String type, String message) {
//        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
//        binding.RVNotification.setVisibility(View.GONE);
//        binding.swipeRefreshLayout.setRefreshing(false);
    }


    public class AdapterCommentaryList extends RecyclerView.Adapter<AdapterCommentaryList.MyViewHolder> {
        private final List<BeanCommentary> mListenerList;
        Context mContext;
        int currentInning = 1;

        public AdapterCommentaryList(Context context, List<BeanCommentary> mListenerList) {
            mContext = context;
            this.mListenerList = mListenerList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvOvers, tvRuns, tvCommentary, tvInning;
            CardView comHead;

            public MyViewHolder(View view) {
                super(view);

                tvOvers = view.findViewById(R.id.im_overs);
                tvRuns = view.findViewById(R.id.im_runs);
                tvCommentary = view.findViewById(R.id.im_com_resp);
                tvInning = view.findViewById(R.id.im_inning);
                comHead=view.findViewById(R.id.im_com_head);
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
            final String inning = mListenerList.get(position).getInning();

            System.out.println(inning);

            if (runs.equals("4") || runs.equals("6")) {
                holder.tvRuns.setBackgroundResource(R.drawable.circle_score_4_6);

            } else if (runs.equals("0") || runs.equals("1") || runs.equals("2")) {
                holder.tvRuns.setBackgroundResource(R.drawable.circle_score_0_1_2);

            } else if (runs.equals("W")) {
                holder.tvRuns.setBackgroundResource(R.drawable.circle_score_w);
            }

            if (currentInning == Integer.valueOf(inning) && currentInning == 1){
                holder.tvInning.setText(inning+ "st Innings");
                currentInning = 2;
//                System.out.println("IF 1");

            } else if (currentInning == Integer.valueOf(inning) && currentInning == 2) {
                holder.tvInning.setText(inning + "nd Innings");
                currentInning = 1;
//                System.out.println("IF 2");

            } else {
                holder.tvInning.setVisibility(View.GONE);
                holder.comHead.setVisibility(View.GONE);
//                System.out.println("IF 3");
            }

            holder.tvOvers.setText(overs);
            holder.tvRuns.setText(runs);
            holder.tvCommentary.setText(commentary);
        }
    }
}
