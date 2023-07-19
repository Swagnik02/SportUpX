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

import com.team.fantasy.databinding.ActivityMyTransactionBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanMyTransList;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Config.MYTRANSACTIONLIST;
import static com.team.fantasy.APICallingPackage.Constants.MYTRANSACTIONTYPE;

public class MyTransactionActivity extends AppCompatActivity implements ResponseManager {

    MyTransactionActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    AdapterTransactionList adapterTransactionList;
    ActivityMyTransactionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_transaction);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();

        binding.RVMyTransactions.setHasFixedSize(true);
        binding.RVMyTransactions.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RVMyTransactions.setLayoutManager(mLayoutManager);
        binding.RVMyTransactions.setItemAnimator(new DefaultItemAnimator());


        binding.swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.swipeRefreshLayout.setRefreshing(true);
                                        callTransactionList(false);
                                    }
                                }
        );

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callTransactionList(false);
            }
        });

    }

    public void initViews(){


        binding.head.tvHeaderName.setText("RECENT TRANSACTIONS");
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    private void callTransactionList(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(MYTRANSACTIONLIST,
                    createRequestJson(), context, activity, MYTRANSACTIONTYPE,
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
        binding.RVMyTransactions.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(false);

        try {
            JSONArray jsonArray = result.getJSONArray("data");
            List<BeanMyTransList> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanMyTransList>>() {
                    }.getType());
            adapterTransactionList = new AdapterTransactionList(beanContestLists, activity);
            binding.RVMyTransactions.setAdapter(adapterTransactionList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterTransactionList.notifyDataSetChanged();


    }



    @Override
    public void onError(Context mContext, String type, String message) {
        binding.tvNoDataAvailable.setVisibility(View.VISIBLE);
        binding.RVMyTransactions.setVisibility(View.GONE);
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    public class AdapterTransactionList extends RecyclerView.Adapter<AdapterTransactionList.MyViewHolder> {
        private List<BeanMyTransList> mListenerList;
        Context mContext;

        public AdapterTransactionList(List<BeanMyTransList> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_TransactionAmount,tv_TransactionInfo,tv_TransactionStatus;

            public MyViewHolder(View view) {
                super(view);

                tv_TransactionAmount = view.findViewById(R.id.tv_TransactionAmount);
                tv_TransactionInfo = view.findViewById(R.id.tv_TransactionInfo);
                tv_TransactionStatus = view.findViewById(R.id.tv_TransactionStatus);

            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_my_transaction_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final String TransAmount = mListenerList.get(position).getAmount();
            String TransType= mListenerList.get(position).getType();
            String TransStatus= mListenerList.get(position).getTransaction_status();
            String TransNote= mListenerList.get(position).getTransection_mode();

            if (TransType.equals("credit")){
                holder.tv_TransactionAmount.setText("+ ₹ "+TransAmount);
            }
            else if (TransType.equals("debit")){
                holder.tv_TransactionAmount.setText("- ₹ "+TransAmount);
            }
            else if (TransType.equals("bonus_debit")){
                holder.tv_TransactionAmount.setText("- ₹ "+TransAmount);
            }
            else if (TransType.equals("bonus")){
                holder.tv_TransactionAmount.setText("+ ₹ "+TransAmount);
            }
            else if (TransType.equals("winning")){
                holder.tv_TransactionAmount.setText("+ ₹ "+TransAmount);
            }
            else if (TransType.equals("winning_debit")){
                holder.tv_TransactionAmount.setText("- ₹ "+TransAmount);
            }
            if (TransNote==null){
                holder.tv_TransactionInfo.setText(TransType);
            }
            else {
                holder.tv_TransactionInfo.setText(TransNote);
            }

            holder.tv_TransactionStatus.setText("Status -"+TransStatus);


        }

    }

}
