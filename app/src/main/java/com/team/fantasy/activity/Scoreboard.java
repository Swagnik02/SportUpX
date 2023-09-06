package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityScoreboardBinding;

public class Scoreboard extends AppCompatActivity {

    private ActivityScoreboardBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);

        binding.imTeam1.setText("India");

        binding.imT1BatsmanName.setText("Indian Player1");

        binding.imCloseIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }

        });



    }
}
