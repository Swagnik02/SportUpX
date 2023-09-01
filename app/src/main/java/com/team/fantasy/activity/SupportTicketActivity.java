package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.SEND_SUPPORT_TICKET;
import static com.team.fantasy.APICallingPackage.Constants.SUPPORT_TICKET_TYPE;
import static com.team.fantasy.activity.HomeActivity.sessionManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SupportTicketActivity extends AppCompatActivity implements ResponseManager{
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SupportTicketActivity activity;
    Context context;
    ImageView im_back;
    TextView tv_HeaderName;
    EditText emailEditText,messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_ticket);

        initViews();
        // Initialize the spinner and populate it with support categories
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.support_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void initViews(){

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);

        tv_HeaderName.setText("Support Ticket");
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void callSendSupportTicket(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(SEND_SUPPORT_TICKET,
                    createSupportTicketJson(), context, activity, SUPPORT_TICKET_TYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createSupportTicketJson() {
        emailEditText = findViewById(R.id.emailEditText);
        messageEditText = findViewById(R.id.messageEditText);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("email", emailEditText.getText().toString());
            jsonObject.put("message", messageEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(SEND_SUPPORT_TICKET)) {
            // Check the 'message' to determine if the request was successful
            if (message.equals("success")) {
                try {
                    String successMessage = result.getString("success_message");
                    String additionalData = result.getString("additional_data");

                    ShowToast(context, "Ticket Created Successfully");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the API request was not successful
                // You can display an error message to the user or take appropriate action
            }
        }
    }


    @Override
    public void onError(Context mContext, String type, String message) {
        if (type.equals(SEND_SUPPORT_TICKET)) {
            // Handle the specific error type (SEND_SUPPORT_TICKET) here
            // You can display an error message to the user or take appropriate action
            // For example, show a toast message with the error message:
            Toast.makeText(mContext, "Error: " + message, Toast.LENGTH_SHORT).show();

            // You can also log the error for debugging purposes
            Log.e("API Error", "Error type: " + type + ", Message: " + message);
        } else {
            // Handle other error types if needed
        }
    }

}