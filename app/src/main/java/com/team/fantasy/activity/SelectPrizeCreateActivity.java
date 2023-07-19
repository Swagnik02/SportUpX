package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.fantasy.databinding.ActivitySelectPrizeCreateBinding;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanRank;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.CREATECONTESTRANK;
import static com.team.fantasy.APICallingPackage.Config.CREATEOWNCONTEST;
import static com.team.fantasy.APICallingPackage.Constants.CREATEOWNCONTESTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.RANKLISTTYPE;

public class SelectPrizeCreateActivity extends AppCompatActivity implements ResponseManager {

    String ContestName, ContestSize, ContestWinningAmount, EntryFees, MatchId;

    SelectPrizeCreateActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    AdapterRankList adapterRankList;
    SessionManager sessionManager;
    String WinningBreakupID = "";

    ActivitySelectPrizeCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_prize_create);

        Intent i = getIntent();
        ContestName = i.getStringExtra("ContestName");
        ContestSize = String.valueOf(i.getIntExtra("ContestSize", 0));
        ContestWinningAmount = String.valueOf(i.getIntExtra("ContestWinningAmount", 0));
        EntryFees = String.valueOf(i.getDoubleExtra("EntryFees", 0));
        MatchId = i.getStringExtra("MatchId");

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.tvCEntryFees.setText("₹" + EntryFees);
        binding.tvCPrizePool.setText("₹" + ContestWinningAmount);
        binding.tvCSize.setText("" + ContestSize);
        callRankList(true);

        binding.RLBottomFinalCreateMyContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WinningBreakupID.equals("")) {
                    ShowToast(activity, getResources().getString(R.string.select_winning_breakup) +
                            "");
                } else {
                    callCreateContest(true);
                }
            }
        });

    }

    public void initViews() {


        binding.head.tvHeaderName.setText("" + ContestName);
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.RvRankList.setHasFixedSize(true);
        binding.RvRankList.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RvRankList.setLayoutManager(mLayoutManager);
        binding.RvRankList.setItemAnimator(new DefaultItemAnimator());


    }

    private void callRankList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(CREATECONTESTRANK,
                    createRequestJson1(), context, activity, RANKLISTTYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson1() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("team_size", ContestSize);
            jsonObject.put("price", ContestWinningAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void callCreateContest(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(CREATEOWNCONTEST,
                    createRequestJson2(), context, activity, CREATEOWNCONTESTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("userContestName", ContestName);
            jsonObject.put("userWinners", ContestWinningAmount);
            jsonObject.put("userTotalteam", ContestSize);
            jsonObject.put("userEntry", EntryFees);
            jsonObject.put("userMatchid", MatchId);
            jsonObject.put("breakupId", WinningBreakupID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(CREATEOWNCONTESTTYPE)) {
            try {
                ContestListActivity.ContestId = result.getString("user_Contestid");
                ContestListActivity.MyContestCode = result.getString("unique_code");
                Intent i = new Intent(activity, JoinContestActivity.class);
                i.putExtra("EntryFee", EntryFees);
                i.putExtra("ContestCode", ContestListActivity.MyContestCode);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONArray jsonArray = result.getJSONArray("data");

                ArrayList<BeanRank> arr_beapb = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userData = jsonArray.getJSONObject(i);
                    String Id = userData.getString("id");
                    String winners_count = userData.getString("winners_count");
                    JSONArray rankingArray = userData.getJSONArray("ranking");

                    BeanRank b = new BeanRank();
                    b.setId(Id);
                    b.setWinners_count(winners_count);
                    b.setRanking(rankingArray);
                    arr_beapb.add(b);

                    adapterRankList = new AdapterRankList(arr_beapb, activity);
                    binding.RvRankList.setAdapter(adapterRankList);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            adapterRankList.notifyDataSetChanged();

        }


    }

    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(context, message);
    }


    public class AdapterRankList extends RecyclerView.Adapter<AdapterRankList.MyViewHolder> {
        private List<BeanRank> mListenerList;
        Context mContext;
        int selectedPosition=-1;
        int K= 0;

        public AdapterRankList(List<BeanRank> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_AdaWinnersCount;
            LinearLayout LL_AdawinnersList,LL_Item;

            public MyViewHolder(View view) {
                super(view);
                tv_AdaWinnersCount = view.findViewById(R.id.tv_AdaWinnersCount);
                LL_AdawinnersList = view.findViewById(R.id.LL_AdawinnersList);
                LL_Item = view.findViewById(R.id.LL_Item);
            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_user_contest, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            String Id = mListenerList.get(position).getId();
            String  WinnersCount = mListenerList.get(position).getWinners_count();
            JSONArray  RankingArray = mListenerList.get(position).getRanking();

            holder.tv_AdaWinnersCount.setText(WinnersCount+" Winners");

            if (K==0) {
                if (RankingArray.length() > 0) {
                    for (int i = 0; i < RankingArray.length(); i++) {

                        View to_add = LayoutInflater.from(context).inflate(R.layout.item_own_rank,
                                holder.LL_AdawinnersList, false);
                        TextView tv_Rank = to_add.findViewById(R.id.tv_Rank);
                        TextView tv_Percent = to_add.findViewById(R.id.tv_Percent);
                        TextView tv_Price = to_add.findViewById(R.id.tv_Price);
                        try {
                            JSONObject userData = RankingArray.getJSONObject(i);
                            String RankId = userData.getString("id");
                            String rank = userData.getString("rank");
                            String poolprice = userData.getString("poolprice");
                            String price_percentage = userData.getString("price_percentage");
                            tv_Rank.setText("Rank: " + rank);
                            tv_Price.setText("₹ " + poolprice);
                            tv_Percent.setText(price_percentage + "%");
                            holder.LL_AdawinnersList.addView(to_add);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            if(selectedPosition==position)
                holder.LL_Item.setBackgroundResource(R.drawable.winning_breakup_selected);
            else {
                holder.LL_Item.setBackgroundResource(R.drawable.edittext_back);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WinningBreakupID = mListenerList.get(position).getId();
                    selectedPosition=position;
                    K=1;
                    notifyDataSetChanged();
                }
            });



        }

    }
}
