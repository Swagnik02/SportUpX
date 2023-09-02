package com.team.fantasy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.team.fantasy.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior bottomSheetBehavior;

    public BottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetLayout));

        // Set the initial state (collapsed or expanded)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        // Dummy values for image, username, and wallet balance
        ImageView userImage = view.findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.user_icon1);

        EditText usernameTextField = view.findViewById(R.id.usernameTextField);
        usernameTextField.setText("JohnDoe");

        EditText walletBalanceTextField = view.findViewById(R.id.walletBalanceTextField);
        walletBalanceTextField.setText("$500.00");
    }
}
