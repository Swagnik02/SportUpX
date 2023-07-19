package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Config.APKURL;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityInviteFriendsBinding;
import com.team.fantasy.utils.SessionManager;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class InviteFriendsActivity extends AppCompatActivity implements ResponseManager {
    InviteFriendsActivity  activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    SessionManager sessionManager;
    String ReferralCode,UserName;
    ActivityInviteFriendsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        binding=DataBindingUtil.setContentView(this, R.layout.activity_invite_friends);

        context = activity = this;
        initViews();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);
        sessionManager = new SessionManager();
        ReferralCode = sessionManager.getUser(context).getReferral_code();
        UserName = sessionManager.getUser(context).getName();

        binding.tvUniqueCode.setText(ReferralCode);

        binding.referalCodeLayout.setOnClickListener(view->{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("ReferralCode", ReferralCode);
            clipboard.setPrimaryClip(clip);

            Toasty.info(context, "ReferralCode Copied to Clipboard.", Toast.LENGTH_SHORT, true).show();
        });

        binding.tvInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Your friend " + UserName +" invited you to the" +
                        " " + getApplicationInfo().loadLabel(getPackageManager()).toString()+ ". " +
                        "Where you earn real money." +
                        "Download the app using this " +
                        "link:- "+APKURL+" and Enter this unique Code:- " +
                        ReferralCode+" And create your account.");

                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));
            }
        });

        binding.tvMyFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InvitedFriendListActivity.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

    }

    public void initViews(){
        binding.head.tvHeaderName.setTextColor(getResources().getColor(R.color.white));
        binding.head.tvHeaderName.setText(getResources().getString(R.string.invite_frnds));
        binding.head.imBack.setColorFilter(getResources().getColor(R.color.white));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {

    }

    @Override
    public void onError(Context mContext, String type, String message) {

    }
}
