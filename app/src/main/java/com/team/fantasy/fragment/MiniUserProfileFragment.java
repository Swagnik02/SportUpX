package com.team.fantasy.fragment;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team.fantasy.APICallingPackage.Config;
import com.team.fantasy.R;
import com.team.fantasy.activity.HomeActivity;
import com.team.fantasy.activity.MyAccountActivity;
import com.team.fantasy.utils.SessionManager;

public class MiniUserProfileFragment extends BottomSheetDialogFragment {
    MiniUserProfileFragment activity;
    MiniUserProfileFragment context;
    String imageUrl;
    String username = "Username";
    String walletBalance = "Balance";
    int profilePhotoResId = R.drawable.user_icon1;
    ImageView profileImageView,walletIcon;
    TextView usernameTextView,walletBalanceTextView,KYCstatus;
    int verifiedKYC;
    public MiniUserProfileFragment(String amnt, int KYCStatus) {
        // Required empty public constructor
        this.walletBalance = amnt;
        this.verifiedKYC = KYCStatus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_user_profile, container, false);

        Context context = getActivity();
        imageUrl = Config.ProfileIMAGEBASEURL + HomeActivity.sessionManager.getUser(context).getImage();
        username = HomeActivity.sessionManager.getUser(context).getName();

        // Initialize UI components and set data
        profileImageView = view.findViewById(R.id.profileImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        walletBalanceTextView = view.findViewById(R.id.walletBalanceTextView);
        walletIcon = view.findViewById(R.id.walletIcon);
        KYCstatus = view.findViewById(R.id.im_KYCverified);

        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.user_icon1) // Error image if the URL is invalid
                    .into(profileImageView);
        } else {
            profileImageView.setImageResource(profilePhotoResId);
        }

        // Set data to UI components
        if (verifiedKYC==1){
            KYCstatus.setText("Verified");
            KYCstatus.setEnabled(false);
        }
        else {
            KYCstatus.setEnabled(true);
        }
        usernameTextView.setText(username);
        walletBalanceTextView.setText("₹ "+walletBalance);

        walletIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MyAccountActivity.class);
                dismiss();
                startActivity(i);
            }
        });
        walletBalanceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MyAccountActivity.class);
                dismiss();
                startActivity(i);
            }
        });

        KYCstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MyAccountActivity.class);
                dismiss();
                startActivity(i);
            }
        });



        // Close Button Click Listener
//        Button closeButton = view.findViewById(R.id.closeButton);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss(); // Close the bottom sheet on button click
//            }
//        });

        return view;
    }

}
