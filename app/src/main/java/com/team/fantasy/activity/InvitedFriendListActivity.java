package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Config.INVITEDFRIENDSLIST;
import static com.team.fantasy.APICallingPackage.Constants.INVITEDFRIENDSLISTTYPE;

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
import com.team.fantasy.Bean.BeanFriendsList;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityInvitedFriendListBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class InvitedFriendListActivity extends AppCompatActivity implements ResponseManager{
    InvitedFriendListActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    AdapterFriendsList adapterFriendsList;
    ActivityInvitedFriendListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this, R.layout.activity_invited_friend_list);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.RVFriendsList.setHasFixedSize(true);
        binding.RVFriendsList.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RVFriendsList.setLayoutManager(mLayoutManager);
        binding.RVFriendsList.setItemAnimator(new DefaultItemAnimator());



        binding.swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    binding.swipeRefreshLayout.setRefreshing(true);
                                    callFriendsList(false);
                                }
                            }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callFriendsList(false);
            }
        });

    }

    public void initViews(){

        binding.head.tvHeaderName.setText(getResources().getString(R.string.ur_invited_frnds));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void callFriendsList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(INVITEDFRIENDSLIST,
                    createRequestJson(), context, activity, INVITEDFRIENDSLISTTYPE,
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
        binding.RVFriendsList.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(false);

        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanFriendsList> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanFriendsList>>() {
                    }.getType());
            adapterFriendsList = new AdapterFriendsList(beanContestLists, activity);
            binding.RVFriendsList.setAdapter(adapterFriendsList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterFriendsList.notifyDataSetChanged();


    }



    @Override
    public void onError(Context mContext, String type, String message) {
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RVFriendsList.setVisibility(View.GONE);
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    public class AdapterFriendsList extends RecyclerView.Adapter<AdapterFriendsList.MyViewHolder> {
        private List<BeanFriendsList> mListenerList;
        Context mContext;


        public AdapterFriendsList(List<BeanFriendsList> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_FriendName,tv_CreditedAmount ;


            public MyViewHolder(View view) {
                super(view);

                tv_FriendName = view.findViewById(R.id.tv_FriendName);
                tv_CreditedAmount = view.findViewById(R.id.tv_CreditedAmount);
            }
        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_friends_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final String FriendEmail = mListenerList.get(position).getEmail();
            String BonusEarned= mListenerList.get(position).getBonus();

            holder.tv_FriendName.setText(FriendEmail);
            holder.tv_CreditedAmount.setText("Earning through friend - ₹"+BonusEarned);
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition( R.anim.slide_out_down, R.anim.slide_in_down );
    }
}
