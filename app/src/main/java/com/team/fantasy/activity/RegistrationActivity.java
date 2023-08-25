package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.LOGIN;
import static com.team.fantasy.APICallingPackage.Config.SIGNUP;
import static com.team.fantasy.APICallingPackage.Constants.LOGINTYPE;
import static com.team.fantasy.APICallingPackage.Constants.SIGNUPTYPE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Class.Validations;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.UserDetails;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityRegitrationBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements ResponseManager, GoogleApiClient.OnConnectionFailedListener  {

    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    Context context;
    RegistrationActivity activity;

    String country_code,MobileNumber,EmailId,Password,ReferralCode;
    String Reffered;


    CallbackManager callbackManager;
    AccessToken accessTokan;


    private final String TAG = "RegistrationActivity";
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;

    String LoginType = "Normal";


    //Auto Login
    private SharedPreferences loginPreferences;
    private Boolean saveLogin;
    private SharedPreferences.Editor loginPrefsEditor;

    //SMS Permission
    String[] permissions = new String[]{
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.RECEIVE_SMS
    };

    SessionManager sessionManager;

    ActivityRegitrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        binding=DataBindingUtil.setContentView(this, R.layout.activity_regitration);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = activity = this;
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        initViews();
        sessionManager = new SessionManager();

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        Reffered = getIntent().getStringExtra("Reffered");


        if (Reffered.equals("Yes")){
            binding.inputRegRefCode.setVisibility(View.VISIBLE);
            binding.LLFaceGoogle.setVisibility(View.GONE);
        }
        else {
            binding.inputRegRefCode.setVisibility(View.GONE);
            binding.LLFaceGoogle.setVisibility(View.VISIBLE);
        }

        binding.tvRegNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryCodePicker countryCode = (CountryCodePicker) findViewById(R.id.country_code);
                country_code = countryCode.getSelectedCountryCode();

                MobileNumber = binding.etMobileNo.getText().toString();
                EmailId = binding.etEmail.getText().toString();
                Password = binding.etPassword.getText().toString();
                if (Reffered.equals("Yes")){
                    ReferralCode = binding.etReferralCode.getText().toString();
                }
                else {
                    ReferralCode = "";
                }

                if (MobileNumber.equals("")){
                    ShowToast(context,"Enter Mobile Number");
                    binding.etMobileNo.requestFocus();
                }
                else if (!MobileNumber.matches(Validations.MobilePattern)){
                    binding.etMobileNo.requestFocus();
                    ShowToast(context,"Enter Valid Mobile Number");
                }
                else if (EmailId.equals("")){
                    binding.etEmail.requestFocus();
                    ShowToast(context,"Enter Email Id");

                } else if(!Validations.isValidEmail(EmailId)){
                    binding.etEmail.requestFocus();
                    ShowToast(context,"Enter Valid Email Id");
                }
                else if (Password.equals("")){
                    binding.etPassword.requestFocus();
                    ShowToast(context,"Enter Password");
                }
                else if (Password.length()<8 && !Validations.isValidPassword(Password)){

                    ShowToast(context,"Password Pattern Not Macthed");
                }
                else {
                    LoginType = "Normal";

                    if (Build.VERSION.SDK_INT >= 23) {
                        checkPermissions();
                    } else {
                        callSignupApi(true);
                    }

                }

            }
        });


        binding.RLFBLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginType = "Email";
                //fb_login_button.performClick();
                initFbObject(context);
            }
        });

        binding.RLGmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginType = "Email";
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        binding.LLEnterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,RegistrationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Reffered","Yes");
                startActivity(i);
            }
        });

        binding.tvSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        binding.fbLoginButton.setPermissions("email");
        binding.fbLoginButton.setPermissions("public_profile");
        binding.fbLoginButton.setPermissions("user_birthday");
        binding.fbLoginButton.setPermissions("user_friends");

        //G+

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        binding.btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        binding.btnSignIn.setScopes(gso.getScopeArray());



        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        binding.tvTearmsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity, WebviewAcitivity.class);
                i.putExtra("Heading","TERMS & CONDITIONS");
                i.putExtra("URL", Config.TERMSANDCONDITIONSURL);
                startActivity(i);
            }
        });


    }

    public void initViews(){


        binding.Head.tvHeaderName.setText("REGISTER & PLAY");
        binding.Head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }



    private void callSignupApi(boolean isShowLoader) {
        try {
            ShowToast(context,"User Created !!");

            apiRequestManager.callAPI(SIGNUP,
                    createRequestJson(), context, activity, SIGNUPTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("country_code", country_code);
            jsonObject.put("mobile", MobileNumber);
            jsonObject.put("email", EmailId);
            jsonObject.put("password", Password);
            jsonObject.put("code", ReferralCode);
            jsonObject.put("type", LoginType);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void callLoginApi(boolean isShowLoader) {
        try {

            apiRequestManager.callAPI(LOGIN,
                    createRequestJsonLogin(), context, activity, LOGINTYPE,
                    isShowLoader,responseManager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    JSONObject createRequestJsonLogin() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", EmailId);
            jsonObject.put("password", Password);
            jsonObject.put("type", LoginType);
            jsonObject.put("token", FirebaseInstanceId.getInstance().getToken());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }




    @Override
    public void getResult(Context mContext, String type,String message, JSONObject result) {
        Validations.showProgress(context);
        if (type.equals(SIGNUPTYPE)) {
            Log.e("RegistrationActivity>>", "getResult: >>>" + result + "\n" + type);

            try {
                String UserId = result.getString("user_id");
                String mobile = result.getString("mobile");
                String email = result.getString("email");
                String LoginType = result.getString("type");

                if (LoginType.equals("Normal")) {
                    Validations.hideProgress();
                    Intent i = new Intent(activity, VerifyOTPActivity.class);
                    i.putExtra("Number", mobile);
                    i.putExtra("Activity", "Registration");
                    i.putExtra("UserId", UserId);
                    i.putExtra("Password", Password);
                    startActivity(i);
                } else {
                    Validations.hideProgress();
                    callLoginApi(true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (type.equals(LOGINTYPE)) {
            Validations.hideProgress();
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.commit();

            try {
                UserDetails userDetails = new UserDetails();
                userDetails.setUser_id(result.getString("user_id"));
                userDetails.setName(result.getString("name"));
                userDetails.setMobile(result.getString("mobile"));
                userDetails.setEmail(result.getString("email"));
                userDetails.setType(result.getString("type"));
                userDetails.setVerify(result.getString("verify"));
                userDetails.setReferral_code(result.getString("referral_code"));
                sessionManager.setUser(context, userDetails);
                Intent i = new Intent(activity, HomeActivity.class);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Validations.hideProgress();
        }
    }

    @Override
    public void onError(Context mContext, String type, String message) {

        if (type.equals(SIGNUPTYPE)) {
            revokeAccess();
            ShowToast(context,""+message);
        }
        else if (type.equals(LOGINTYPE)){
            ShowToast(context,"Some Error Occured While Login. Please Try Again");
        }

    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {

                                String FBName = json.getString("name");
                                String ProfileUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                String Id = json.getString("id");
                                //  String gender = json.getString("gender");
                                String Fname = json.getString("first_name");
                                String Lname = json.getString("last_name");
                                String FBEmail = json.getString("email");

                                MobileNumber = "";
                                EmailId = FBEmail;
                                Password = Id;

                                callSignupApi(true);

                                Log.e("RegistrationActivity>",json.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoginManager.getInstance().logOut();
                            final AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                            ab.setMessage("Due to your facebook privacy settings," +
                                    "Facebook is denied to provide enough data for " +
                                    "login process.You can use our other Signup process");
                            ab.setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(activity,MainActivity.class);
                                    startActivity(i);
                                }
                            });
                            ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                            ab.setCancelable(false);
                            AlertDialog alert = ab.create();
                            alert.show();


                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture,gender,first_name,last_name,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void revokeAccess() {

        try {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // updateUI(false);
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            // String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            String Id = acct.getId();

            MobileNumber = "";
            EmailId = email;
            Password = Id;
            callSignupApi(true);

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " /*personPhotoUrl*/);
        } else {
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        else {
            callSignupApi(true);
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callSignupApi(true);
            } else {
                callSignupApi(true);
            }
            return;
        }
    }

    private void initFbObject(final Context mContext) {
        //initilaize facebbok sdk
        FacebookSdk.fullyInitialize();
        FacebookSdk.setApplicationId(mContext.getResources().getString(R.string.facebook_app_id));

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions((Activity)
                mContext, Arrays.asList("public_profile", "email"));
        //Facebook
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //showProgressDialog();
                String userId = loginResult.getAccessToken().getUserId();
                accessTokan = loginResult.getAccessToken();

                try {
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject object, GraphResponse response) {
                            Log.i("LoginActivity", response.toString());
                            // Get facebook data from login

                            getFacebookData(object);

//                            LoginManager.getInstance().logOut();

                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,link,picture");
                    request.setParameters(parameters);
                    request.executeAsync();
                } catch (Exception e) {
                    Log.e(getClass().getName(), e.toString());
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("FacebookError", "" + error);
            }
        });
    }




    /*get facebook data*/
    private String getFacebookData(JSONObject object) {
        try {
            if (object != null) {
                Log.e("fb_response", object.toString());

                String  user_fullname = object.optString("name");
                String  user_fb_id = object.optString("id");
                String user_email = object.optString("email");
                JSONObject jsonObject = object.optJSONObject("picture");
                JSONObject jobj = jsonObject.optJSONObject("data");
                String user_profile_pic = jobj.optString("url");


                MobileNumber = "";
                EmailId = user_email;
                Password = user_fb_id;

                callSignupApi(true);

                Log.e("RegistrationActivity>",object.toString());
                LoginManager.getInstance().logOut();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

}
