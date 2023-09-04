package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.SEND_SUPPORT_TICKET;
import static com.team.fantasy.APICallingPackage.Constants.SUPPORT_TICKET_TYPE;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SupportTicketActivity extends AppCompatActivity implements ResponseManager{
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SupportTicketActivity activity;
    SessionManager sessionManager;
    Context context;
    ImageView im_back;
    TextView tv_HeaderName,suggestion_account_delete;
    EditText emailEditText;
    EditText messageEditText;
    Spinner spinner;
    Button submitButton;

    int isSuccess = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_ticket);

        context = activity = this;
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        initViews();

        // Initialize views
        Spinner spinner = findViewById(R.id.spinner);
        emailEditText = findViewById(R.id.emailEditText);
        suggestion_account_delete = findViewById(R.id.im_suggestionDeleteAccnt);
        messageEditText = findViewById(R.id.messageEditText);
        submitButton = findViewById(R.id.submitButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.support_categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callSendSupportTicket(true);
            }
        });
        suggestion_account_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageEditText.setText("ACCOUNT DELETION REQUEST");
                callSendSupportTicket(true);
            }
        });

    }


    public void initViews(){

        im_back = findViewById(R.id.im_back);
        tv_HeaderName = findViewById(R.id.tv_HeaderName);
        emailEditText = findViewById(R.id.emailEditText);

        tv_HeaderName.setText("Support Ticket");
        emailEditText.setText(sessionManager.getUser(context).getEmail());
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
                    isShowLoader, this );

            // In your onClick method or any test scenario
            responseManager.getResult(context, SUPPORT_TICKET_TYPE, "Status", new JSONObject());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createSupportTicketJson() {

        spinner = findViewById(R.id.spinner);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("email", emailEditText.getText().toString());
            jsonObject.put("topic", spinner.getSelectedItem().toString());
            jsonObject.put("query", messageEditText.getText().toString());

            isSuccess=1;

        } catch (JSONException e) {
            isSuccess=0;
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        LayoutInflater li = getLayoutInflater();
        // Getting the View object as defined in the customtoast.xml file
        View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        // Debugging: Check if layout inflation is successful
        Log.d("CustomToast", "Layout Inflation Success: " + (layout != null));
        TextView textView = (TextView) layout.findViewById(R.id.custom_toast_message);

        if (isSuccess==1) {
            message = "Ticket Generated";

            // Creating the Toast object
            textView.setText(message);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout); // setting the view of custom toast layout
            toast.show();
            Intent i = new Intent(activity, HomeActivity.class);
            startActivity(i);
            finish();

        }
        else {
            message = "Error";

            // Creating the Toast object
            textView.setText(message);
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout); // setting the view of custom toast layout
            toast.show();
        }
    }

//
//    @Override
//    public void getResult(Context mContext, String type, String message, JSONObject result) {
//
//
//
//        if (type.equals(SUPPORT_TICKET_TYPE)) {
//
//            try {
//                String status = result.getString("status");
//                if (status.equals("success")) {
//
//                    ShowToast(context,"DONE");
//                    LayoutInflater li = getLayoutInflater();
//                    // Getting the View object as defined in the customtoast.xml file
//                    View layout = li.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
//
//                    // Debugging: Check if layout inflation is successful
//                    Log.d("CustomToast", "Layout Inflation Success: " + (layout != null));
//
//                    TextView textView = (TextView) layout.findViewById(R.id.custom_toast_message);
//                    // Creating the Toast object
//                    textView.setText(message);
//                    Toast toast = new Toast(getApplicationContext());
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setView(layout); // setting the view of custom toast layout
//                    toast.show();
//                    Intent i = new Intent(activity, HomeActivity.class);
//                    startActivity(i);
//                    finish();
//                } else {
//                    // Handle failure case if needed
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public void onError(Context mContext, String type, String message) {
        if (type.equals(SUPPORT_TICKET_TYPE)) {
            if (message.equals("Unable to make support ticket at this time")) {
                Toast.makeText(mContext, "Unable to create a support ticket at this time.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            Log.e("API Error", "Error type: " + type + ", Message: " + message);
        } else {
            // Handle other error types if needed
        }
    }




}