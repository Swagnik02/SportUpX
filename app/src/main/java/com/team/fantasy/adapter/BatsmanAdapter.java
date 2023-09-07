package com.team.fantasy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.R;
import com.team.fantasy.activity.Scoreboard;

import java.util.List;

public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.ViewHolder> {

    private List<Scoreboard.BatsmanItem> playerList; // List of BatsmanItem objects

    // Constructor for BatsmanAdapter
    public BatsmanAdapter(List<Scoreboard.BatsmanItem> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_batsman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Scoreboard.BatsmanItem batsmanItem = playerList.get(position);

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
            batsmanName = itemView.findViewById(R.id.im_batsman_name);
            runs = itemView.findViewById(R.id.im_batsman_runs);
            ballsPlayed = itemView.findViewById(R.id.im_batsman_balls_played);
            fours = itemView.findViewById(R.id.im_batsman_fours);
            sixes = itemView.findViewById(R.id.im_batsman_sixes);
            strikeRate = itemView.findViewById(R.id.im_batsman_StrikeRate);
        }
    }
}
