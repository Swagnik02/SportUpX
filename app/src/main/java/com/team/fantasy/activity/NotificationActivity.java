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
import com.team.fantasy.databinding.ActivityNotificationBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanNotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Config.NOTIFICATIONLIST;
import static com.team.fantasy.APICallingPackage.Constants.NOTIFICATIONTYPE;

public class NotificationActivity extends AppCompatActivity implements ResponseManager {
    NotificationActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    AdapterNotificationList adapterNotificationList;
    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.RVNotification.setHasFixedSize(true);
        binding.RVNotification.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RVNotification.setLayoutManager(mLayoutManager);
        binding.RVNotification.setItemAnimator(new DefaultItemAnimator());


        binding.swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    binding.swipeRefreshLayout.setRefreshing(true);
                                    callNotificationList(false);
                                }
                            }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callNotificationList(false);
            }
        });


    }

    public void initViews(){


        binding.head.tvHeaderName.setText("NOTIFICATIONS");
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void callNotificationList(boolean isShowLoader) {
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
        binding.tvNoDataAvailable.setVisibility(View.GONE);
        binding.RVNotification.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(false);

        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanNotification> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanNotification>>() {
                    }.getType());
            adapterNotificationList = new AdapterNotificationList(beanContestLists, activity);
            binding.RVNotification.setAdapter(adapterNotificationList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterNotificationList.notifyDataSetChanged();


    }



    @Override
    public void onError(Context mContext, String type, String message) {
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RVNotification.setVisibility(View.GONE);
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    public class AdapterNotificationList extends RecyclerView.Adapter<AdapterNotificationList.MyViewHolder> {
        private List<BeanNotification> mListenerList;
        Context mContext;


        public AdapterNotificationList(List<BeanNotification> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_NotificationTitle,tv_NotificationContest,tv_NotificationMessage, tv_NotificationDescription  ;

            public MyViewHolder(View view) {
                super(view);

                tv_NotificationTitle = view.findViewById(R.id.tv_NotificationTitle);
                tv_NotificationContest = view.findViewById(R.id.tv_NotificationContest);
                tv_NotificationMessage = view.findViewById(R.id.tv_NotificationMessage);
                tv_NotificationDescription = view.findViewById(R.id.tv_NotificationDescription);

            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_notification_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            final String ContestName = mListenerList.get(position).getContest_name();
            String Title= mListenerList.get(position).getTitle();
            String Message= mListenerList.get(position).getMessage();
            String Description= mListenerList.get(position).getDescription();

            holder.tv_NotificationTitle.setText(Title);
            holder.tv_NotificationContest.setText(ContestName);
            holder.tv_NotificationMessage.setText(Message);
            holder.tv_NotificationDescription.setText(Description);



        }

    }
}
