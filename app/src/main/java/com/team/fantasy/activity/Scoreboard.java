package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.R;
import com.team.fantasy.adapter.BatsmanAdapter;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends AppCompatActivity {

    private RecyclerView BattingView,TotalScoreView;
    private BatsmanAdapter adapter;
    TextView totalScoreTextView,totalWicketsTextView,totalOversTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Initialize the RecyclerView
        BattingView = findViewById(R.id.recyclerViewBatsman);
        BattingView.setLayoutManager(new LinearLayoutManager(this));

//        TotalScoreView = findViewById(R.id.recyclerViewTotalScore);
        TotalScoreView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of BatsmanItem objects
        List<BatsmanItem> playerList = new ArrayList<>();
        playerList.add(new BatsmanItem("Player 1", "5000", "300", "50", "20", "166.67"));
        playerList.add(new BatsmanItem("Player 2", "30", "25", "2", "1", "120.0"));
        playerList.add(new BatsmanItem("Player 3", "20", "15", "1", "0", "133.33"));
        playerList.add(new BatsmanItem("Player 4", "40", "35", "3", "1", "114.29"));
        playerList.add(new BatsmanItem("Player 5", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 6", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 7", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 8", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 9", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 10", "60", "40", "7", "3", "150.0"));
        playerList.add(new BatsmanItem("Player 11", "60", "40", "7", "3", "150.0"));

        // Create an adapter and set it to the RecyclerView
        adapter = new BatsmanAdapter(playerList);
        BattingView.setAdapter(adapter);

        // Initialize the TextViews for total score, total wickets, and total overs
        totalScoreTextView = findViewById(R.id.im_t1_total_score);
        totalWicketsTextView = findViewById(R.id.im_t1_wickets);
        totalOversTextView = findViewById(R.id.im_t1_total_overs);

        // ...

        // Assume you have fetched the API data and parsed it
        String totalScore = "500"; // Replace with the actual total score obtained from the API
        String totalWickets = "5"; // Replace with the actual total wickets obtained from the API
        String totalOvers = "50.0"; // Replace with the actual total overs obtained from the API

        // Update the TextViews with API data
        totalScoreTextView.setText(totalScore);
        totalWicketsTextView.setText(totalWickets);
        totalOversTextView.setText("("+totalOvers+")");


        ImageView closeButton = findViewById(R.id.im_CloseIcon);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Define a BatsmanItem class to represent player information
    public static class BatsmanItem {
        private String batsmanName;
        private String runs;
        private String ballsPlayed;
        private String fours;
        private String sixes;
        private String strikeRate;

        public BatsmanItem(String batsmanName, String runs, String ballsPlayed, String fours, String sixes, String strikeRate) {
            this.batsmanName = batsmanName;
            this.runs = runs;
            this.ballsPlayed = ballsPlayed;
            this.fours = fours;
            this.sixes = sixes;
            this.strikeRate = strikeRate;
        }

        public String getBatsmanName() {
            return batsmanName;
        }

        public String getRuns() {
            return runs;
        }

        public String getBallsPlayed() {
            return ballsPlayed;
        }

        public String getFours() {
            return fours;
        }

        public String getSixes() {
            return sixes;
        }

        public String getStrikeRate() {
            return strikeRate;
        }
    }



}
