package com.team.fantasy.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;


import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.UserDetails;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityEditProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.EDITPROFILE;
import static com.team.fantasy.APICallingPackage.Config.VIEWPROFILE;
import static com.team.fantasy.APICallingPackage.Constants.EDITPROFILETYPE;
import static com.team.fantasy.APICallingPackage.Constants.GETCITYTYPE;
import static com.team.fantasy.APICallingPackage.Constants.VIEWPROFILETYPE;

public class EditProfileActivity extends AppCompatActivity implements ResponseManager {

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    Context context;
    EditProfileActivity activity;
    SessionManager sessionManager;
    String name,mobile,email,image,teamName,favriteTeam,dob,gender
            ,address,city,pincode,state,country;
    private SharedPreferences loginPreferences;
    private Boolean saveLogin;
    private SharedPreferences.Editor loginPrefsEditor;

    ActivityEditProfileBinding binding;

    FetchCity fetchCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        binding=DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        callViewProfile(true);

        binding.etEditDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(activity,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new mdateListner(), year, month, day);

                // Set the maximum date to today's date minus 18 years
                calendar.add(Calendar.YEAR, -18);
                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.show();
            }
        });


        binding.tvEditMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
                binding.tvEditMale.setBackgroundResource(R.drawable.roundbutton);
                binding.tvEditFeMale.setBackgroundResource(R.drawable.roundbutton_hover_back);
            }
        });
        binding.tvEditFeMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
                binding.tvEditFeMale.setBackgroundResource(R.drawable.roundbutton);
                binding.tvEditMale.setBackgroundResource(R.drawable.roundbutton_hover_back);
            }
        });

        binding.tvEditUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callEditProfile(true);
            }
        });
        fetchCity = new FetchCity();
        String stateId = "41";
        fetchCity.callFetchCities(this, stateId, true, this);
    }


    public void initViews() {
        binding.head.tvHeaderName.setText(getResources().getString(R.string.personal_details));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.etEditCountry.setText(getResources().getString(R.string.India));
        binding.etEditCountry.setEnabled(false);
        binding.etEditCountry.setFocusable(false);
    }

    private void callViewProfile(boolean isShowLoader) {
//        ShowToast(context, "apiCalled: callViewProfile");
        try {

            apiRequestManager.callAPI(VIEWPROFILE,
                    createRequestJson(), context, activity, VIEWPROFILETYPE,
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

    private void callEditProfile(boolean isShowLoader) {
//        ShowToast(context, "apiCalled: callEditProfile");
        try {

            apiRequestManager.callAPI(EDITPROFILE,
                    createEditProfileJson(), context, activity, EDITPROFILETYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createEditProfileJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("name", binding.etEditName.getText().toString());
            jsonObject.put("mobile", binding.etEditMobile.getText().toString());
            jsonObject.put("favriteTeam", binding.etEditFavouriteTeam.getText().toString());
            jsonObject.put("dob", binding.etEditDob.getText().toString());
            jsonObject.put("gender", gender);
            jsonObject.put("address", binding.etEditAddress.getText().toString());
            jsonObject.put("city", binding.etEditCity.getText().toString());
            jsonObject.put("state", binding.etEditState.getText().toString());
            jsonObject.put("country", binding.etEditCountry.getText().toString());
            jsonObject.put("pincode", binding.etEditPincode.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

        if (type.equals(EDITPROFILETYPE)) {

            ShowToast(context, message);
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.commit();
            UserDetails userDetails = new UserDetails();
            userDetails.setUser_id(sessionManager.getUser(context).getUser_id());
            userDetails.setName(binding.etEditName.getText().toString());
            userDetails.setMobile(binding.etEditMobile.getText().toString());
            userDetails.setEmail(sessionManager.getUser(context).getEmail());
            userDetails.setType(sessionManager.getUser(context).getType());
            userDetails.setReferral_code(sessionManager.getUser(context).getReferral_code());
            userDetails.setImage(sessionManager.getUser(context).getImage());
            userDetails.setAddress(binding.etEditAddress.getText().toString());
            userDetails.setCity(binding.etEditCity.getText().toString());
            userDetails.setPincode(binding.etEditPincode.getText().toString());
            userDetails.setState(binding.etEditState.getText().toString());
            userDetails.setVerify("1");
            sessionManager.setUser(context, userDetails);
            onBackPressed();
            finish();
        }
             else {
                try {
                    name = result.getString("name");
                    mobile = result.getString("mobile");
                    email = result.getString("email");
                    image = result.getString("image");
                    teamName = result.getString("teamName");
                    favriteTeam = result.getString("favriteTeam");
                    dob = result.getString("dob");
                    gender = result.getString("gender");
                    address = result.getString("address");
                    city = result.getString("city");
                    pincode = result.getString("pincode");
                    state = result.getString("state");
                    country = result.getString("country");


                    if (name.equals("")) {

                    } else {
                        binding.etEditName.setText(name);
                    }
                    binding.etEditEmail.setText(email);
                    binding.etEditMobile.setText(mobile);
                    binding.etEditFavouriteTeam.setText(favriteTeam);
                    binding.etEditDob.setText(dob);
                    binding.etEditAddress.setText(address);
                    binding.etEditCity.setText(city);
                    binding.etEditState.setText(state);
                    binding.etEditPincode.setText(pincode);

                    if (gender.equals("male")) {
                        binding.tvEditMale.setBackgroundResource(R.drawable.roundbutton);
                        binding.tvEditFeMale.setBackgroundResource(R.drawable.roundbutton_hover_back);
                    } else if (gender.equals("female")) {
                        binding.tvEditFeMale.setBackgroundResource(R.drawable.roundbutton);
                        binding.tvEditMale.setBackgroundResource(R.drawable.roundbutton_hover_back);
                    }

                    if (!email.equals("")) {
                        binding.etEditEmail.setEnabled(false);
                        binding.etEditEmail.setFocusable(false);
                    }
                    if (!mobile.equals("")) {
                        binding.etEditMobile.setEnabled(false);
                        binding.etEditMobile.setFocusable(false);
                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }


    @Override
    public void onError(Context mContext, String type, String message) {

    }



    class mdateListner implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            {
                int actualMonth = month+1;
                Date d = new Date(year, actualMonth,dayOfMonth);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String df_medium_us_str = dateFormat.format(chosenDate);
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(year, month, dayOfMonth-1);
                String dayOfWeek = simpledateformat.format(date);
                binding.etEditDob.setText(df_medium_us_str );
            }

        }
    }
}
