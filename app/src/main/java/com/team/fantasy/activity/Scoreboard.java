package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.Bean.BeanBatterStats;
import com.team.fantasy.Bean.BeanBowlerStats;
import com.team.fantasy.R;
import com.team.fantasy.adapter.BatsmanAdapter;
import com.team.fantasy.adapter.BowlerAdapter;
import com.team.fantasy.adapter.ScoreboardAdapter;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends AppCompatActivity {

    private RecyclerView battingRecyclerView, bowlingRecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


        ImageView closeButton = findViewById(R.id.im_CloseIcon);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Initialize batting and bowling data (replace this with your actual data)
        List<BeanBatterStats> battingStats = getBattingStats();
        List<BeanBowlerStats> bowlingStats = getBowlingStats();

        // Initialize batting RecyclerView
        battingRecyclerView = findViewById(R.id.recyclerViewBatsman);
        BatsmanAdapter batsmanAdapter = new BatsmanAdapter(this, battingStats);
        battingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        battingRecyclerView.setAdapter(batsmanAdapter);

        // Initialize bowling RecyclerView
        bowlingRecyclerView = findViewById(R.id.recyclerViewBowlers);
        BowlerAdapter bowlerAdapter = new BowlerAdapter(this, bowlingStats);
        bowlingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bowlingRecyclerView.setAdapter(bowlerAdapter);

    }

    // Replace this with your actual data retrieval method
    private List<BeanBatterStats> getBattingStats() {
        List<BeanBatterStats> battingStats = new ArrayList<>();
        battingStats.add(new BeanBatterStats("Mohammad Naim", "20", "25", "4", "0", "80.0"));
        battingStats.add(new BeanBatterStats("Mehidy Hasan", "0", "1", "0", "0", "0.0"));
        battingStats.add(new BeanBatterStats("Litton Das", "16", "13", "4", "0", "123.1"));
        battingStats.add(new BeanBatterStats("Shakib Al Hasan (c)", "53", "57", "7", "0", "93.0"));
        battingStats.add(new BeanBatterStats("Towhid Hridoy", "2", "9", "0", "0", "22.2"));
        battingStats.add(new BeanBatterStats("Mushfiqur Rahim (wk)", "64", "87", "5", "0", "73.6"));
        battingStats.add(new BeanBatterStats("Shamim Hossain", "16", "23", "0", "1", "69.6"));
        battingStats.add(new BeanBatterStats("Afif Hossain", "12", "11", "0", "1", "109.1"));
        battingStats.add(new BeanBatterStats("Taskin Ahmed", "0", "1", "0", "0", "0.0"));
        battingStats.add(new BeanBatterStats("Shoriful Islam", "1", "3", "0", "0", "33.3"));
        battingStats.add(new BeanBatterStats("Hasan Mahmud", "1", "2", "0", "0", "50.0"));
        return battingStats;
    }

    // Replace this with your actual data retrieval method
    private List<BeanBowlerStats> getBowlingStats() {
        List<BeanBowlerStats> bowlingStats = new ArrayList<>();
        bowlingStats.add(new BeanBowlerStats("Shaheen Afridi", "7.0", "1", "42", "1", "6.0"));
        bowlingStats.add(new BeanBowlerStats("Naseem Shah", "5.4", "0", "34", "3", "6.0"));
        bowlingStats.add(new BeanBowlerStats("Haris Rauf", "6.0", "0", "19", "4", "3.2"));
        bowlingStats.add(new BeanBowlerStats("Faheem Ashraf", "7.0", "0", "27", "1", "3.9"));
        bowlingStats.add(new BeanBowlerStats("Shadab Khan", "7.0", "0", "35", "0", "5.0"));
        bowlingStats.add(new BeanBowlerStats("Agha Salman", "1.0", "0", "11", "0", "11.0"));
        bowlingStats.add(new BeanBowlerStats("Iftikhar Ahmed", "5.0", "0", "20", "1", "4.0"));

        return bowlingStats;
    }

}
