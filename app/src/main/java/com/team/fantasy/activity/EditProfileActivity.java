package com.team.fantasy.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;



import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.UserDetails;
import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityEditProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.EDITPROFILE;
import static com.team.fantasy.APICallingPackage.Config.VIEWPROFILE;
import static com.team.fantasy.APICallingPackage.Constants.EDITPROFILETYPE;
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

    // Initialize the city list HashMap
    HashMap<String, List<String>> cityMap;
    List<String> stateList;

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


        // Initialize the state list with data from the resource file
        stateList = Arrays.asList(getResources().getStringArray(R.array.state_list));

        // Initialize the city list HashMap with data from the resource files
        cityMap = new HashMap<>();
        cityMap.put(stateList.get(0), Arrays.asList(getResources().getStringArray(R.array.city_list_andaman_and_nicobar)));
        cityMap.put(stateList.get(1), Arrays.asList(getResources().getStringArray(R.array.city_list_andhra_pradesh)));
        cityMap.put(stateList.get(2), Arrays.asList(getResources().getStringArray(R.array.city_list_arunachal_pradesh)));
        cityMap.put(stateList.get(3), Arrays.asList(getResources().getStringArray(R.array.city_list_assam)));
        cityMap.put(stateList.get(4), Arrays.asList(getResources().getStringArray(R.array.city_list_bihar)));
        cityMap.put(stateList.get(5), Arrays.asList(getResources().getStringArray(R.array.city_list_chandigarh)));
        cityMap.put(stateList.get(6), Arrays.asList(getResources().getStringArray(R.array.city_list_chhattisgarh)));
        cityMap.put(stateList.get(7), Arrays.asList(getResources().getStringArray(R.array.city_list_dadra_and_nagar_haveli)));
        cityMap.put(stateList.get(8), Arrays.asList(getResources().getStringArray(R.array.city_list_daman_and_diu)));
        cityMap.put(stateList.get(9), Arrays.asList(getResources().getStringArray(R.array.city_list_delhi)));
        cityMap.put(stateList.get(10), Arrays.asList(getResources().getStringArray(R.array.city_list_goa)));
        cityMap.put(stateList.get(11), Arrays.asList(getResources().getStringArray(R.array.city_list_gujarat)));
        cityMap.put(stateList.get(12), Arrays.asList(getResources().getStringArray(R.array.city_list_haryana)));
        cityMap.put(stateList.get(13), Arrays.asList(getResources().getStringArray(R.array.city_list_himachal_pradesh)));
        cityMap.put(stateList.get(14), Arrays.asList(getResources().getStringArray(R.array.city_list_jammu_and_kashmir)));
        cityMap.put(stateList.get(15), Arrays.asList(getResources().getStringArray(R.array.city_list_jharkhand)));
        cityMap.put(stateList.get(16), Arrays.asList(getResources().getStringArray(R.array.city_list_karnataka)));
        cityMap.put(stateList.get(17), Arrays.asList(getResources().getStringArray(R.array.city_list_kerala)));
        cityMap.put(stateList.get(18), Arrays.asList(getResources().getStringArray(R.array.city_list_lakshadweep)));
        cityMap.put(stateList.get(19), Arrays.asList(getResources().getStringArray(R.array.city_list_madhya_pradesh)));
        cityMap.put(stateList.get(20), Arrays.asList(getResources().getStringArray(R.array.city_list_maharashtra)));
        cityMap.put(stateList.get(21), Arrays.asList(getResources().getStringArray(R.array.city_list_manipur)));
        cityMap.put(stateList.get(22), Arrays.asList(getResources().getStringArray(R.array.city_list_meghalaya)));
        cityMap.put(stateList.get(23), Arrays.asList(getResources().getStringArray(R.array.city_list_mizoram)));
        cityMap.put(stateList.get(24), Arrays.asList(getResources().getStringArray(R.array.city_list_nagaland)));
        cityMap.put(stateList.get(25), Arrays.asList(getResources().getStringArray(R.array.city_list_odisha)));
        cityMap.put(stateList.get(26), Arrays.asList(getResources().getStringArray(R.array.city_list_puducherry)));
        cityMap.put(stateList.get(27), Arrays.asList(getResources().getStringArray(R.array.city_list_punjab)));
        cityMap.put(stateList.get(28), Arrays.asList(getResources().getStringArray(R.array.city_list_rajasthan)));
        cityMap.put(stateList.get(29), Arrays.asList(getResources().getStringArray(R.array.city_list_sikkim)));
        cityMap.put(stateList.get(30), Arrays.asList(getResources().getStringArray(R.array.city_list_tamil_nadu)));
        cityMap.put(stateList.get(31), Arrays.asList(getResources().getStringArray(R.array.city_list_telangana)));
        cityMap.put(stateList.get(32), Arrays.asList(getResources().getStringArray(R.array.city_list_tripura)));
        cityMap.put(stateList.get(33), Arrays.asList(getResources().getStringArray(R.array.city_list_uttar_pradesh)));
        cityMap.put(stateList.get(34), Arrays.asList(getResources().getStringArray(R.array.city_list_uttarakhand)));
        cityMap.put(stateList.get(35), Arrays.asList(getResources().getStringArray(R.array.city_list_west_bengal)));



        binding.etEditState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStateListDialog();
            }
        });

        binding.etEditCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if a state is selected before showing the city dialog
                if (!binding.etEditState.getText().toString().isEmpty()) {
                    showCityListDialog(binding.etEditState.getText().toString());
                } else {
                    // Show a message to the user to select a state first
                    ShowToast(context, "Please select a state first.");
                }
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
    }

    // Method to show the state list dialog
    private void showStateListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select State");
        builder.setItems(stateList.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Set the selected state in the EditText
                binding.etEditState.setText(stateList.get(which));
                // Show the city list dialog based on the selected state
                showCityListDialog(stateList.get(which));

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to show the city list dialog based on the selected state
    private void showCityListDialog(String selectedState) {
        List<String> cityList = cityMap.get(selectedState);
        if (cityList != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select City");
            builder.setItems(cityList.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Set the selected city in the EditText
                    binding.etEditCity.setText(cityList.get(which));
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
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
        ShowToast(context, "ViewAPi");
//        try {
//
//            apiRequestManager.callAPI(VIEWPROFILE,
//                    createRequestJson(), context, activity, VIEWPROFILETYPE,
//                    isShowLoader,responseManager);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
        ShowToast(context, "EditAPi");
//        try {
//
//            apiRequestManager.callAPI(EDITPROFILE,
//                    createEditProfileJson(), context, activity, EDITPROFILETYPE,
//                    isShowLoader,responseManager);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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

        if (type.equals(EDITPROFILETYPE)){

            ShowToast(context,message);
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
