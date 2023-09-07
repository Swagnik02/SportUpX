package com.team.fantasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.Bean.BeanBatterStats;
import com.team.fantasy.R;

import java.util.List;

public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.BatsmanViewHolder> {
    private static final int VIEW_TYPE_BATSMAN = 1;
    private Context context;
    private List<BeanBatterStats> batsmanStats;

    public BatsmanAdapter(Context context, List<BeanBatterStats> batsmanStats) {
        this.context = context;
        this.batsmanStats = batsmanStats;
    }

    @NonNull
    @Override
    public BatsmanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View batsmanView = inflater.inflate(R.layout.item_batsman, parent, false);
        return new BatsmanViewHolder(batsmanView);
    }

    @Override
    public int getItemCount() {
        return batsmanStats.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BatsmanViewHolder holder, int position) {
        BatsmanViewHolder batsmanViewHolder = (BatsmanViewHolder) holder;
        BeanBatterStats batsman = batsmanStats.get(position);

        // Bind data to the batsman view
        batsmanViewHolder.batsmanName.setText(batsman.getBatterName());
        batsmanViewHolder.batsmanRuns.setText(batsman.getRuns());
        batsmanViewHolder.batsmanBallsPlayed.setText(batsman.getBallsPlayed());
        batsmanViewHolder.batsmanFours.setText(batsman.getFours());
        batsmanViewHolder.batsmansixes.setText(batsman.getSixes());
        batsmanViewHolder.batsmanStrikeRate.setText(batsman.getStrikeRate());
        // Bind other batting statistics here
    }

    static class BatsmanViewHolder extends RecyclerView.ViewHolder {
        TextView batsmanName, batsmanRuns, batsmanBallsPlayed, batsmanFours, batsmansixes, batsmanStrikeRate;

        // Other batting TextViews

        BatsmanViewHolder(View itemView) {
            super(itemView);
            batsmanName = itemView.findViewById(R.id.im_batsman_name);
            batsmanRuns = itemView.findViewById(R.id.im_batsman_runs);
            batsmanBallsPlayed = itemView.findViewById(R.id.im_batsman_balls_played);
            batsmanFours = itemView.findViewById(R.id.im_batsman_fours);
            batsmansixes = itemView.findViewById(R.id.im_batsman_sixes);
            batsmanStrikeRate = itemView.findViewById(R.id.im_batsman_StrikeRate);

            // Initialize other batting TextViews
        }
    }
}
