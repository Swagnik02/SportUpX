package com.team.fantasy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team.fantasy.R;

public class MiniUserProfileFragment extends BottomSheetDialogFragment {

    public MiniUserProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mini_user_profile, container, false);

        // Retrieve data from arguments (you can customize this part)
        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username");
            String walletBalance = args.getString("walletBalance");
            int profilePhotoResId = args.getInt("profilePhotoResId");

            // Initialize UI components and set data
            ImageView profileImageView = view.findViewById(R.id.profileImageView);
            TextView usernameTextView = view.findViewById(R.id.usernameTextView);
            TextView walletBalanceTextView = view.findViewById(R.id.walletBalanceTextView);

            // Set data to UI components
            profileImageView.setImageResource(profilePhotoResId);
            usernameTextView.setText(username);
            walletBalanceTextView.setText(walletBalance);
        }

        // Close Button Click Listener
        Button closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Close the bottom sheet on button click
            }
        });

        return view;
    }
}
