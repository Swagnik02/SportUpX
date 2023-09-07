package com.team.fantasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.Bean.BeanBatterStats;
import com.team.fantasy.Bean.BeanBowlerStats;
import com.team.fantasy.R;

import java.util.List;

public class ScoreboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_BATSMAN = 1;
    private static final int VIEW_TYPE_BOWLER = 2;

    private Context context;
    private List<BeanBatterStats> batsmanStats;
    private List<BeanBowlerStats> beanBowlerStats;

    public ScoreboardAdapter(Context context, List<BeanBatterStats> batsmanStats, List<BeanBowlerStats> beanBowlerStats) {
        this.context = context;
        this.batsmanStats = batsmanStats;
        this.beanBowlerStats = beanBowlerStats;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < batsmanStats.size()) {
            return VIEW_TYPE_BATSMAN;
        } else {
            return VIEW_TYPE_BOWLER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEW_TYPE_BATSMAN) {
            View batsmanView = inflater.inflate(R.layout.item_batsman, parent, false);
            return new BatsmanViewHolder(batsmanView);
        } else {
            View bowlerView = inflater.inflate(R.layout.item_bowlers, parent, false);
            return new BowlerViewHolder(bowlerView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BatsmanViewHolder) {
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
        } else if (holder instanceof BowlerViewHolder) {
            BowlerViewHolder bowlerViewHolder = (BowlerViewHolder) holder;
            BeanBowlerStats bowler = beanBowlerStats.get(position - batsmanStats.size());

            // Bind data to the bowler view
            bowlerViewHolder.bowlerName.setText(bowler.getBowlerName());
            bowlerViewHolder.bowlerOvers.setText(bowler.getOvers());
            bowlerViewHolder.bowlerMaidenOvers.setText(bowler.getMaidenOvers());
            bowlerViewHolder.bowlerRuns.setText(bowler.getRuns());
            bowlerViewHolder.bowlerWickets.setText(bowler.getWickets());
            bowlerViewHolder.bowlerEconomy.setText(bowler.getEconomy());
            // Bind other bowling statistics here
        }
    }

    @Override
    public int getItemCount() {
        return batsmanStats.size() + beanBowlerStats.size();
    }

    static class BatsmanViewHolder extends RecyclerView.ViewHolder {
        TextView batsmanName,batsmanRuns,batsmanBallsPlayed,batsmanFours,batsmansixes,batsmanStrikeRate;
        // Other batting TextViews

        BatsmanViewHolder(View itemView) {
            super(itemView);
                    batsmanName= itemView.findViewById(R.id.im_batsman_name);
                    batsmanRuns = itemView.findViewById(R.id.im_batsman_runs);
                    batsmanBallsPlayed= itemView.findViewById(R.id.im_batsman_balls_played);
                    batsmanFours= itemView.findViewById(R.id.im_batsman_fours);
                    batsmansixes= itemView.findViewById(R.id.im_batsman_sixes);
                    batsmanStrikeRate= itemView.findViewById(R.id.im_batsman_StrikeRate);

            // Initialize other batting TextViews
        }
    }

    static class BowlerViewHolder extends RecyclerView.ViewHolder {
        TextView bowlerName,bowlerOvers,bowlerMaidenOvers,bowlerRuns,bowlerWickets,bowlerEconomy;
        // Other bowling TextViews

        BowlerViewHolder(View itemView) {
            super(itemView);
            bowlerName = itemView.findViewById(R.id.im_bowlers_name);
            bowlerOvers = itemView.findViewById(R.id.im_bowlers_overs);
            bowlerMaidenOvers= itemView.findViewById(R.id.im_bowlers_maidenovers);
            bowlerRuns= itemView.findViewById(R.id.im_bowlers_runs);
            bowlerWickets= itemView.findViewById(R.id.im_bowlers_wickets);
            bowlerEconomy= itemView.findViewById(R.id.im_bowlers_economy);
            // Initialize other bowling TextViews
        }
    }

}
