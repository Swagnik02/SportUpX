package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityContestListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanContestList;
import com.team.fantasy.Bean.BeanWiningInfoList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.CONTESTLIST;
import static com.team.fantasy.APICallingPackage.Config.MYTEAMLIST;
import static com.team.fantasy.APICallingPackage.Config.WINNINGINFOLIST;
import static com.team.fantasy.APICallingPackage.Constants.CONTESTLISTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.MYTEAMLISTTYPE;
import static com.team.fantasy.APICallingPackage.Constants.WINNINGINFOLISTTYPE;

public class ContestListActivity extends AppCompatActivity implements ResponseManager {


    public static String IntentMatchId, IntentTime, IntenTeamsName, IntentTeamOneName, IntentTeamTwoName, MyTeamEditorSave, MyContestCode = "",
            ContestBonusPercent;
    public static String IntentTeamOneImg="",IntentTeamTwoImg="";

    public static String JoinMyTeamId;
    public static String ContestId, EntryFee;
    ContestListActivity activity;
    Context context;
    AdapterContestList adapterContestList;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    //Dialog dialog;
    BottomSheetDialog dialog;
    List<BeanWiningInfoList> beanWinningLists;
    String prize_pool, contest_description;

    SessionManager sessionManager;
    int TeamCount = 0;

    ActivityContestListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contest_list);

        context = activity = this;
        initViews();
        try {
            IntentMatchId = getIntent().getStringExtra("MatchId");
            IntentTime = getIntent().getStringExtra("Time");
            IntenTeamsName = getIntent().getStringExtra("TeamsName");
            IntentTeamOneName = getIntent().getStringExtra("TeamsOneName");
            IntentTeamTwoName = getIntent().getStringExtra("TeamsTwoName");
            IntentTeamOneImg=getIntent().getStringExtra("TeamsOneImg");
            IntentTeamTwoImg=getIntent().getStringExtra("TeamsTwoImg");

            Glide.with(activity).load(Config.TEAMFLAGIMAGE + IntentTeamOneImg)

                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.inclVsBck.imTeam1);
            Glide.with(activity).load(Config.TEAMFLAGIMAGE + IntentTeamTwoImg)
                    //.signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.inclVsBck.imTeam2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.inclVsBck.tvHeadTeamOneName.setText(IntentTeamOneName);
        binding.inclVsBck.tvHeadTeamTwoName.setText(IntentTeamTwoName);


        binding.inclVsBck.tvContestTimer.setText(ContestListActivity.IntentTime);


        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();


        binding.RvContestList.setHasFixedSize(true);
        binding.RvContestList.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RvContestList.setLayoutManager(mLayoutManager);
        binding.RvContestList.setItemAnimator(new DefaultItemAnimator());



        binding.swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.swipeRefreshLayout.setRefreshing(true);
                                        callContestList(false);
                                    }
                                }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callContestList(false);
            }
        });


        Validations.CountDownTimer(IntentTime, binding.inclVsBck.tvContestTimer);

        binding.tvMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTeamEditorSave = "New";
                callMyTeamActivity();

            }
        });

        binding.tvCreateContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMyTeamList(true);
            }
        });

        binding.tvEnterContestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, EnterInviteCodeContestActivity.class);
                i.putExtra("MatchId", IntentMatchId);
                startActivity(i);
            }
        });

    }
    private void callMyTeamActivity(){
        Intent i = new Intent(activity, MyTeamListActivity.class);
        i.putExtra("TeamsName", ContestListActivity.IntenTeamsName);
        i.putExtra("Time", ContestListActivity.IntentTime);
        i.putExtra("MatchId", ContestListActivity.IntentMatchId);
        i.putExtra("TeamsOneName", ContestListActivity.IntentTeamOneName);
        i.putExtra("TeamsTwoName", ContestListActivity.IntentTeamTwoName);
        i.putExtra("TeamsOneImg", ContestListActivity.IntentTeamOneImg);
        i.putExtra("TeamsTwoImg", ContestListActivity.IntentTeamTwoImg);
        startActivity(i);
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


    private void callMyTeamList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(MYTEAMLIST,
                    createRequestJson12(), context, activity, MYTEAMLISTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson12() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("match_id", IntentMatchId);
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void callContestList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(CONTESTLIST,
                    createRequestJson(), context, activity, CONTESTLISTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("match_id", IntentMatchId);
            jsonObject.put("type", "All");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void callWinningInfoList(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(WINNINGINFOLIST,
                    createRequestJsonWin(), context, activity, WINNINGINFOLISTTYPE,
                    isShowLoader, responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJsonWin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contest_id", ContestId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(WINNINGINFOLISTTYPE)) {
            try {
                JSONArray jsonArray = result.getJSONArray("data");
                // Log.e("Data",jsonArray.toString());
                beanWinningLists = new Gson().fromJson(jsonArray.toString(),
                        new TypeToken<List<BeanWiningInfoList>>() {
                        }.getType());

                dialog = new BottomSheetDialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_winning_breakups);
                final TextView tv_DClose = dialog.findViewById(R.id.tv_DClose);
                final TextView tv_DTotalWinning = dialog.findViewById(R.id.tv_DTotalWinning);
                final TextView tv_DBottomNote = dialog.findViewById(R.id.tv_DBottomNote);
                final LinearLayout LL_WinningBreackupList = dialog.findViewById(R.id.LL_WinningBreackupList);
                dialog.show();
                tv_DTotalWinning.setText("₹ " + prize_pool);
                tv_DBottomNote.setText("Note: " + contest_description);
                tv_DClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                for (int i = 0; i < beanWinningLists.size(); i++) {

                    View to_add = LayoutInflater.from(context).inflate(R.layout.item_winning_breakup,
                            LL_WinningBreackupList, false);
                    TextView tv_Rank = to_add.findViewById(R.id.tv_Rank);
                    TextView tv_Price = to_add.findViewById(R.id.tv_Price);

                    tv_Rank.setText("Rank: " + beanWinningLists.get(i).getRank());
                    tv_Price.setText("₹ " + beanWinningLists.get(i).getPrice());

                    LL_WinningBreackupList.addView(to_add);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (type.equals(MYTEAMLISTTYPE)) {
            try {
                JSONArray jsonArray = result.getJSONArray("data");
                Log.e("Data ", jsonArray.toString());
                TeamCount = jsonArray.length();
                if (TeamCount == 0) {
                    ShowToast(context, "For Creating Contest, You have to Create Team First.");
                    MyTeamEditorSave = "New";
                    callMyTeamActivity();
                } else {
                    Intent i = new Intent(activity, CreateContestActivity.class);
                    i.putExtra("MatchId", IntentMatchId);
                    startActivity(i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            binding.swipeRefreshLayout.setRefreshing(false);
            binding.tvNoDataAvailable.setVisibility(View.GONE);
            binding.RvContestList.setVisibility(View.VISIBLE);
            try {
                JSONArray jsonArray = result.getJSONArray("data");
                List<BeanContestList> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                        new TypeToken<List<BeanContestList>>() {
                        }.getType());
                adapterContestList = new AdapterContestList(beanContestLists, activity);
                binding.RvContestList.setAdapter(adapterContestList);

            } catch (Exception e) {
                e.printStackTrace();
            }

            adapterContestList.notifyDataSetChanged();

        }
    }


    @Override
    public void onError(Context mContext, String type, String message) {
        //ShowToast(context,message);
        if (type.equals(CONTESTLISTTYPE)) {
            binding.swipeRefreshLayout.setRefreshing(false);
            binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
            binding.RvContestList.setVisibility(View.GONE);
        } else if (type.equals(MYTEAMLISTTYPE)) {

            ShowToast(context, "For Creating Contest, You have to Create Team First.");
            MyTeamEditorSave = "New";
           callMyTeamActivity();
        }
    }

    public class AdapterContestList extends RecyclerView.Adapter<AdapterContestList.MyViewHolder> {
        private List<BeanContestList> mListenerList;
        Context mContext;


        public AdapterContestList(List<BeanContestList> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_LiveContestName,tv_LiveContestDesc,tv_TotalPrice,tv_WinnersCount,tv_EntryFees,tv_TeamLeftCount,tv_TotalTeamCount
                    ,tv_JoinContest,tv_Contest_bonus_msg;

            ProgressBar PB_EntryProgress;


            public MyViewHolder(View view) {
                super(view);
                tv_LiveContestName = view.findViewById(R.id.tv_LiveContestName);
                tv_LiveContestDesc = view.findViewById(R.id.tv_LiveContestDesc);
                tv_TotalPrice = view.findViewById(R.id.tv_TotalPrice);
                tv_WinnersCount = view.findViewById(R.id.tv_WinnersCount);
                tv_EntryFees = view.findViewById(R.id.tv_EntryFees);
                tv_TeamLeftCount = view.findViewById(R.id.tv_TeamLeftCount);
                tv_TotalTeamCount = view.findViewById(R.id.tv_TotalTeamCount);
                PB_EntryProgress = view.findViewById(R.id.PB_EntryProgress);
                tv_JoinContest = view.findViewById(R.id.tv_JoinContest);
                tv_Contest_bonus_msg=view.findViewById(R.id.tv_Contest_bonus_msg);



            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_contest_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            final String contest_id = mListenerList.get(position).getContest_id();
            String contest_name= mListenerList.get(position).getContest_name();
            String contest_tag= mListenerList.get(position).getContest_tag();
            String winners= mListenerList.get(position).getWinners();
            prize_pool= mListenerList.get(position).getPrize_pool();
            String total_team= mListenerList.get(position).getTotal_team();
            String join_team= mListenerList.get(position).getJoin_team();
            final String entry= mListenerList.get(position).getEntry();

            String contest_note1 = mListenerList.get(position).getContest_note1();
            String contest_note2= mListenerList.get(position).getContest_note2();
            String match_id= mListenerList.get(position).getMatch_id();
            String type= mListenerList.get(position).getType();
            String remaining_team= mListenerList.get(position).getRemaining_team();
            try {
                ContestBonusPercent = mListenerList.get(position).getBonus_percentage();
                if (!ContestBonusPercent.equals("0")) {
                    holder.tv_Contest_bonus_msg.setText("Join This Contest and use " + ContestBonusPercent + "% Bonus Amount");
                } else {
                    holder.tv_Contest_bonus_msg.setVisibility(View.GONE);
                }
            }catch (Exception ex){
                holder.tv_Contest_bonus_msg.setVisibility(View.GONE);
            }

            holder.tv_LiveContestName.setText(contest_name);
            holder.tv_LiveContestDesc.setText(contest_tag);
            holder.tv_TotalPrice.setText("₹ "+prize_pool);
            holder.tv_WinnersCount.setText(winners);
            holder.tv_EntryFees.setText("₹ "+entry);
            holder.tv_TeamLeftCount.setText(remaining_team+" Spots Left");
            holder.tv_TotalTeamCount.setText(total_team+" Teams");
            holder.PB_EntryProgress.setMax(Integer.parseInt(total_team));
            holder.PB_EntryProgress.setProgress(Integer.parseInt(join_team));


            holder.tv_WinnersCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContestId = mListenerList.get(position).getContest_id();
                    contest_description= mListenerList.get(position).getContest_description();
                    prize_pool= mListenerList.get(position).getPrize_pool();

                    callWinningInfoList(true);
                }
            });

            holder.tv_JoinContest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Spot_left=mListenerList.get(position).getRemaining_team();
                    if(Spot_left.equals("0"))
                    {
                        ShowToast(context,"Contest Full");
                    }
                    else {
                        MyTeamEditorSave = "Save";
                        MyContestCode = "";
                        ContestId = mListenerList.get(position).getContest_id();
                        ContestBonusPercent=mListenerList.get(position).getBonus_percentage();
                        EntryFee=mListenerList.get(position).getEntry();
                        holder.tv_JoinContest.setTextColor(Color.WHITE);
                        holder.tv_JoinContest.setBackgroundResource(R.drawable.joinbutton_hover);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.tv_JoinContest.setTextColor(Color.parseColor("#CE404D"));
                                holder.tv_JoinContest.setBackgroundResource(R.drawable.roundbutton_my_team);
                            }
                        }, 400);

                        Intent i = new Intent(activity, JoinContestActivity.class);
                        i.putExtra("EntryFee",entry);
                        i.putExtra("ContestCode",MyContestCode);
                        startActivity(i);}


                }
            });

        }

    }

}
