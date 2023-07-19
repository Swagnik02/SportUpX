package com.team.fantasy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.view.View;

import com.team.fantasy.R;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityInviteInContestBinding;

public class InviteInContestActivity extends AppCompatActivity {

    InviteInContestActivity activity;
    Context context;
    String UserName,ContestCode;
    SessionManager sessionManager;

    ActivityInviteInContestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_invite_in_contest);
        context = activity = this;

        ContestCode = getIntent().getStringExtra("ContestCode");

        binding.tvUniqueCode.setText(ContestCode+"");

        binding.head.tvHeaderName.setText(getResources().getString(R.string.invite_frnds_to_contact));
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        sessionManager = new SessionManager();

        UserName = sessionManager.getUser(context).getName();


        binding.tvInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Your friend " + UserName +" invited you to join" +
                        "privcate contest in the" +
                        " 11 Dreamer Fantasy App. " +
                        "Where you earn real money." +
                        "Download the app using this " +
                        "link:- and Enter this unique Code:- " +
                        ContestCode+" And Join contest.");

                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(context, HomeActivity.class);
        startActivity(i);
        finish();

    }
}
