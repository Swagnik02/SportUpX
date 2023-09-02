package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.SEND_SUPPORT_TICKET;
import static com.team.fantasy.APICallingPackage.Constants.SUPPORT_TICKET_TYPE;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    TextView tv_HeaderName;
    EditText emailEditText;
    EditText messageEditText;
    Spinner spinner;
    Button submitButton;

    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_ticket);

        context = activity = this;
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
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

        // Assign the OnClickListener to the submit button
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the API when the submit button is clicked
                callSendSupportTicket(true); // Pass true to show a loader if needed
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
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createSupportTicketJson() {
        emailEditText = findViewById(R.id.emailEditText);
        messageEditText = findViewById(R.id.messageEditText);
        spinner = findViewById(R.id.spinner);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("email", emailEditText.getText().toString());
            jsonObject.put("topic", spinner.getSelectedItem().toString());
            jsonObject.put("query", messageEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(SEND_SUPPORT_TICKET)) {
            // Check the 'status' field in the JSON response to determine success
            try {
                String status = result.getString("status");
                if (status.equals("success")) {
                    String successMessage = result.getString("message");
                    ShowToast(context, successMessage);
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Context mContext, String type, String message) {
        if (type.equals(SEND_SUPPORT_TICKET)) {
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