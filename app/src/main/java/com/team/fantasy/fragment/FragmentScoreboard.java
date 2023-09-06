package com.team.fantasy.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.team.fantasy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentScoreboard extends BottomSheetDialogFragment {

    private List<Player> team1Players;
    private List<Player> team2Players;

    TextView Team1Name,Team1batsmanName,Team1batsmanRuns,Team1batsmanBallsPlayed,Team1batsmanFours,Team1batsmanSixes,Team1batsmanStrikeRate;
    TextView Team2Name,Team2batsmanName,Team2batsmanRuns,Team2batsmanBallsPlayed,Team2batsmanFours,Team2batsmanSixes,Team2batsmanStrikeRate;
    public FragmentScoreboard() {
        // Initialize player lists for both teams
        team1Players = generateRandomPlayers();
        team2Players = generateRandomPlayers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorecard, container, false);


        // Bind team names TextViews
        Team1Name = view.findViewById(R.id.im_team1);
        Team2Name = view.findViewById(R.id.im_team2);

        // Bind player statistics TextViews for Team 1
        Team1batsmanName = view.findViewById(R.id.im_t1_batsman_name);
        Team1batsmanRuns = view.findViewById(R.id.im_t1_batsman_runs);
        Team1batsmanBallsPlayed = view.findViewById(R.id.im_t1_batsman_balls_played);
        Team1batsmanFours = view.findViewById(R.id.im_t1_batsman_fours);
        Team1batsmanSixes = view.findViewById(R.id.im_t1_batsman_sixes);
        Team1batsmanStrikeRate = view.findViewById(R.id.im_t1_batsman_StrikeRate);

        // Bind player statistics TextViews for Team 2
        Team2batsmanName = view.findViewById(R.id.im_t2_batsman_name);
        Team2batsmanRuns = view.findViewById(R.id.im_t2_batsman_runs);
        Team2batsmanBallsPlayed = view.findViewById(R.id.im_t2_batsman_balls_played);
        Team2batsmanFours = view.findViewById(R.id.im_t2_batsman_fours);
        Team2batsmanSixes = view.findViewById(R.id.im_t2_batsman_sixes);
        Team2batsmanStrikeRate = view.findViewById(R.id.im_t2_batsman_StrikeRate);



        // Bind player data to TextViews for Team 1
        for (int i = 0; i < 5; i++) {
            Player player = team1Players.get(i);
            bindPlayerData(view, i, "im_t1_batsman_", player);
        }

        // Bind player data to TextViews for Team 2
        for (int i = 0; i < 5; i++) {
            Player player = team2Players.get(i);
            bindPlayerData(view, i, "im_t2_batsman_", player);
        }

        return view;
    }

    private List<Player> generateRandomPlayers() {
        List<Player> players = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Player player = new Player(
                    "Player " + (i + 1),
                    random.nextInt(101), // Runs (0-100)
                    random.nextInt(101), // Balls Played (0-100)
                    random.nextInt(11),  // 4s (0-10)
                    random.nextInt(11),  // 6s (0-10)
                    random.nextDouble() * 200  // Strike Rate (0-200)
            );
            players.add(player);
        }

        return players;
    }

    private void bindPlayerData(View view, int index, String prefix, Player player) {
        TextView nameTextView = view.findViewById(getResources().getIdentifier(prefix + "name", "id", getActivity().getPackageName()));
        TextView runsTextView = view.findViewById(getResources().getIdentifier(prefix + "runs", "id", getActivity().getPackageName()));
        TextView ballsTextView = view.findViewById(getResources().getIdentifier(prefix + "balls_played", "id", getActivity().getPackageName()));
        TextView foursTextView = view.findViewById(getResources().getIdentifier(prefix + "fours", "id", getActivity().getPackageName()));
        TextView sixesTextView = view.findViewById(getResources().getIdentifier(prefix + "sixes", "id", getActivity().getPackageName()));
        TextView strikeRateTextView = view.findViewById(getResources().getIdentifier(prefix + "StrikeRate", "id", getActivity().getPackageName()));

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
