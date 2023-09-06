package com.team.fantasy.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; // Import RecyclerView
import com.team.fantasy.R;
import com.team.fantasy.databinding.ActivityScoreboardBinding;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends AppCompatActivity {

    private ActivityScoreboardBinding binding;
    private List<BatsmanItem> playerList; // List of BatsmanItem objects
    private BatsmanAdapter adapter; // Declare BatsmanAdapter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scoreboard);

        // Initialize the playerList (now using BatsmanItem)
        playerList = new ArrayList<>();

        // Create 5 BatsmanItem objects and add them to the playerList
        playerList.add(new BatsmanItem("Player 1", "50", "30", "5", "2", "166.67"));
        playerList.add(new BatsmanItem("Player 2", "30", "25", "2", "1", "120.0"));
        playerList.add(new BatsmanItem("Player 3", "20", "15", "1", "0", "133.33"));
        playerList.add(new BatsmanItem("Player 4", "40", "35", "3", "1", "114.29"));
        playerList.add(new BatsmanItem("Player 5", "60", "40", "7", "3", "150.0"));

        // Create an instance of the BatsmanAdapter
        adapter = new BatsmanAdapter();

        // Set the adapter on the RecyclerView
//        binding.recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.imCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Define a BatsmanItem class to represent player information (Inner class)
    public class BatsmanItem {
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

    // Define BatsmanAdapter as an inner class
    private class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.ViewHolder> {

        // Constructor for BatsmanAdapter
        public BatsmanAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_batsman, parent, false);
//            return new ViewHolder(view);
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BatsmanItem batsmanItem = playerList.get(position);

            // Bind data to TextViews
            holder.batsmanName.setText(batsmanItem.getBatsmanName());
            holder.runs.setText(batsmanItem.getRuns());
            holder.ballsPlayed.setText(batsmanItem.getBallsPlayed());
            holder.fours.setText(batsmanItem.getFours());
            holder.sixes.setText(batsmanItem.getSixes());
            holder.strikeRate.setText(batsmanItem.getStrikeRate());
        }

        @Override
        public int getItemCount() {
            return playerList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView batsmanName, runs, ballsPlayed, fours, sixes, strikeRate;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                batsmanName = itemView.findViewById(R.id.im_t1_batsman_name);
                runs = itemView.findViewById(R.id.im_t1_batsman_runs);
                ballsPlayed = itemView.findViewById(R.id.im_t1_batsman_balls_played);
                fours = itemView.findViewById(R.id.im_t1_batsman_fours);
                sixes = itemView.findViewById(R.id.im_t1_batsman_sixes);
                strikeRate = itemView.findViewById(R.id.im_t1_batsman_StrikeRate);
            }
        }
    }
}
