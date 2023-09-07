package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.R;
import com.team.fantasy.adapter.BatsmanAdapter;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BatsmanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        recyclerView.setAdapter(adapter);

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
