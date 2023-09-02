package com.team.fantasy.fragment;

import android.content.Context;
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
import com.team.fantasy.utils.SessionManager;

public class MiniUserProfileFragment extends BottomSheetDialogFragment {
    MiniUserProfileFragment activity;
    MiniUserProfileFragment context;
    String imageUrl;
    String username = "John Doe"; // Set the default username here
    String walletBalance = "$100"; // Set the default wallet balance here
    int profilePhotoResId = R.drawable.user_icon1;

    public MiniUserProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_user_profile, container, false);

        Context context = getActivity();
        imageUrl = Config.ProfileIMAGEBASEURL + HomeActivity.sessionManager.getUser(context).getImage();
        username = HomeActivity.sessionManager.getUser(context).getName();

        // Initialize UI components and set data
        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        TextView walletBalanceTextView = view.findViewById(R.id.walletBalanceTextView);

        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.user_icon1) // Placeholder image
                    .error(R.drawable.user_icon1) // Error image if the URL is invalid
                    .into(profileImageView);
        } else {
            profileImageView.setImageResource(profilePhotoResId);
        }

        // Set data to UI components
        usernameTextView.setText(username);
        walletBalanceTextView.setText(walletBalance);

        // Close Button Click Listener
//        Button closeButton = view.findViewById(R.id.closeButton);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss(); // Close the bottom sheet on button click
//            }
//        });

        if (context != null) {
            // Access resources or perform other operations using 'context' here
        }

        return view;
    }
}
