package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanAddCashOfferList;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityAddCashBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.ADDAMOUNTOFFER;
import static com.team.fantasy.APICallingPackage.Config.SEND_PAYMENT_DATA_PHONEPE;
import static com.team.fantasy.APICallingPackage.Constants.ADDAMOUNTOFFERTYPE;
import static com.team.fantasy.APICallingPackage.Constants.SEND_PAYMENT_DATA_PHONEPE_TYPE;


public class AddCashActivity extends AppCompatActivity implements ResponseManager {

    AddCashActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
   String FinalAmountToAdd;
    String EntryFee;
    AdapterAddCashOffertList adapterAddCashOfferList;
    JSONObject paymentData;

    public static String Activity = "";
    private String paymentResponseUrl;

    ActivityAddCashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_cash);
        context = activity = this;
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);


        binding.head.tvHeaderName.setText(getResources().getString(R.string.add_cash_head));

        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.RVAddCashOffer.setHasFixedSize(true);
        binding.RVAddCashOffer.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        binding.RVAddCashOffer.setLayoutManager(mLayoutManager);
        //Rv_PlayerList.setItemAnimator(new DefaultItemAnimator());
        binding.RVAddCashOffer.setItemAnimator(null);


        try {
            EntryFee = getIntent().getStringExtra("EntryFee");

            if (EntryFee != null) {
                binding.etAddCashEnterAmount.setText(String.valueOf(EntryFee));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Activity = getIntent().getStringExtra("Activity");
            System.out.print(Activity);
            if (Activity == null) {
                Activity = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.tvAddCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinalAmountToAdd = binding.etAddCashEnterAmount.getText().toString();

                if (FinalAmountToAdd.equals("")) {
                    ShowToast(context, getResources().getString(R.string.enter_valid_amt));
                    FinalAmountToAdd = "0";
                } else if (Integer.parseInt(FinalAmountToAdd) < 10) {
                    ShowToast(context, getResources().getString(R.string.enter_min_amt));
                } else {
                    JSONObject paymentData = new JSONObject();
                    try {
                        paymentData.put("user_id", sessionManager.getUser(context).getUser_id());
                        paymentData.put("amount", FinalAmountToAdd);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Call the method to send payment data to the server
//                    callSendPaymentDataApi(paymentData);

//                    // Start the PaymentOptionActivity with the provided amount
                    Intent i = new Intent(activity, PaymentOptionActivity.class);
                    i.putExtra("FinalAmount", FinalAmountToAdd);

                    startActivity(i);
                }
            }
        });

        binding.tvOneHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etAddCashEnterAmount.setText(getResources().getString(R.string.hundered));
            }
        });
        binding.tvTwoHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etAddCashEnterAmount.setText(getResources().getString(R.string.two_hundred));
            }
        });
        binding.tvFiveHundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etAddCashEnterAmount.setText(getResources().getString(R.string.five_hundred));
            }
        });

        CallAddAmountOffer(true);

    }


    private void callSendPaymentDataApi(JSONObject paymentData) {
        try {
            apiRequestManager.callAPI(SEND_PAYMENT_DATA_PHONEPE,
                    createSendPaymentDataJson(paymentData), context, activity,
                    SEND_PAYMENT_DATA_PHONEPE_TYPE, true, new ResponseManager() {

                        @Override
                        public void getResult(Context mContext, String type, String message, JSONObject result) {
                            try {
                                String url = result.getString("url");

                                if (url != null && !url.isEmpty()) {
                                    paymentResponseUrl = url;

                                    Intent i = new Intent(activity, PaymentOptionActivity.class);
                                    i.putExtra("FinalUrl", paymentResponseUrl);
                                    i.putExtra("FinalAmount", FinalAmountToAdd);

                                    startActivity(i);
                                } else {
                                    System.out.println("Empty or missing 'url' key in response");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Context mContext, String type, String message) {
                            // Handle error if needed
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private JSONObject createSendPaymentDataJson(JSONObject paymentData) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", paymentData.getString("user_id"));
            jsonObject.put("amount", paymentData.getString("amount"));
            // You can add other parameters specific to the payment API
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private void CallAddAmountOffer(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(ADDAMOUNTOFFER,
                    createRequestJson(), context, activity, ADDAMOUNTOFFERTYPE,
                    isShowLoader, responseManager);

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
        binding.RVAddCashOffer.setVisibility(View.VISIBLE);
        binding.LLAddCashOffer.setVisibility(View.VISIBLE);
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            //Log.e("Data",jsonArray.toString());
            List<BeanAddCashOfferList> beanContestLists = new Gson().fromJson(jsonArray.toString(),
                    new TypeToken<List<BeanAddCashOfferList>>() {
                    }.getType());
            adapterAddCashOfferList = new AdapterAddCashOffertList(beanContestLists, activity);
            binding.RVAddCashOffer.setAdapter(adapterAddCashOfferList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapterAddCashOfferList.notifyDataSetChanged();
    }


    @Override
    public void onError(Context mContext, String type, String message) {

        binding.RVAddCashOffer.setVisibility(View.GONE);
        binding.LLAddCashOffer.setVisibility(View.GONE);

    }

    public class AdapterAddCashOffertList extends RecyclerView.Adapter<AdapterAddCashOffertList.MyViewHolder> {
        private List<BeanAddCashOfferList> mListenerList;
        Context mContext;


        public AdapterAddCashOffertList(List<BeanAddCashOfferList> mListenerList, Context context) {
            mContext = context;
            this.mListenerList = mListenerList;

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_BonusCashLimit,tv_BonusOfferAmount;
            public MyViewHolder(View view) {
                super(view);
                tv_BonusCashLimit = view.findViewById(R.id.tv_BonusCashLimit);
                tv_BonusOfferAmount=view.findViewById(R.id.tv_BonusOfferAmount);
            }

        }
        @Override
        public int getItemCount() {
            return mListenerList.size();
        }

        @Override
        public AdapterAddCashOffertList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_add_cash_offer, parent, false);

            return new AdapterAddCashOffertList.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final AdapterAddCashOffertList.MyViewHolder holder, final int position) {


            final String max_range = mListenerList.get(position).getMax_range();
            final  String min_range=mListenerList.get(position).getMin_range();
            final  String amount=mListenerList.get(position).getAmount();
            if (!max_range.equals(""))
            {
                holder.tv_BonusCashLimit.setText("Add Cash "+min_range+" ₹ to "+max_range+" ₹");
                holder.tv_BonusOfferAmount.setText("Get "+amount+ " ₹ Bonus ");
            }

        }

    }
}
