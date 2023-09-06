package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityScoreboardBinding;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends AppCompatActivity {

    private ActivityScoreboardBinding binding;
    private List<Player> playerList;

    private List<Player> team1Players;
    private List<Player> team2Players;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);

        // Retrieve or generate player data for Team 1 and Team 2
        team1Players = generateRandomPlayers();
        team2Players = generateRandomPlayers();

        // Bind player data to TextViews for Team 1
        for (int i = 0; i < 5; i++) {
            Player player = team1Players.get(i);
            bindPlayerData(binding, i, "im_t1_batsman_", player);
        }

        // Bind player data to TextViews for Team 2
        for (int i = 0; i < 5; i++) {
            Player player = team2Players.get(i);
            bindPlayerData(binding, i, "im_t2_batsman_", player);
        }

    }

    private List<Player> generateRandomPlayers() {
        // Generate or retrieve player data here
        // This method should return a list of players
        // You can use random data generation as shown in your code
    return null;
    }

    private void bindPlayerData(ActivityScoreboardBinding binding, int index, String prefix, Player player) {
        TextView nameTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "name", "id", getPackageName()));
        TextView runsTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "runs", "id", getPackageName()));
        TextView ballsTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "balls_played", "id", getPackageName()));
        TextView foursTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "fours", "id", getPackageName()));
        TextView sixesTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "sixes", "id", getPackageName()));
        TextView strikeRateTextView = binding.getRoot().findViewById(getResources().getIdentifier(prefix + "StrikeRate", "id", getPackageName()));

        nameTextView.setText(player.getName());
        runsTextView.setText(String.valueOf(player.getRuns()));
        ballsTextView.setText(String.valueOf(player.getBallsPlayed()));
        foursTextView.setText(String.valueOf(player.getFours()));
        sixesTextView.setText(String.valueOf(player.getSixes()));
        strikeRateTextView.setText(String.format("%.2f", player.getStrikeRate()));
    }

    // Define a Player class to represent player information
    private class Player {
        private String name;
        private int runs;
        private int ballsPlayed;
        private int fours;
        private int sixes;
        private double strikeRate;

        public Player(String name, int runs, int ballsPlayed, int fours, int sixes, double strikeRate) {
            this.name = name;
            this.runs = runs;
            this.ballsPlayed = ballsPlayed;
            this.fours = fours;
            this.sixes = sixes;
            this.strikeRate = strikeRate;
        }

        public String getName() {
            return name;
        }

        public int getRuns() {
            return runs;
        }

        public int getBallsPlayed() {
            return ballsPlayed;
        }

        public int getFours() {
            return fours;
        }

        public int getSixes() {
            return sixes;
        }

        public double getStrikeRate() {
            return strikeRate;
        }
    }
}
