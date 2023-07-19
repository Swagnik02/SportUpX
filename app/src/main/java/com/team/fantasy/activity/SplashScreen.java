package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;

import com.team.fantasy.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.team.fantasy.APICallingPackage.Constants.SPLASH_TIME_OUT;


public class SplashScreen extends AppCompatActivity {

    SplashScreen activity;
    Context context;

    private SharedPreferences loginPreferences;
    private Boolean saveLogin;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_splash_screen);
        context = activity = this;
        printHashKey(context);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (saveLogin == true) {
                    HomeScreen();
                } else {
                    LoginScreen();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    public void LoginScreen() {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
    }

    public void HomeScreen() {
        Intent i = new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(i);
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }
}
