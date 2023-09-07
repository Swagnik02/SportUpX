package com.team.fantasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.Bean.BeanBowlerStats;
import com.team.fantasy.R;

import java.util.List;

public class BowlerAdapter extends RecyclerView.Adapter<BowlerAdapter.BowlerViewHolder> {
    private Context context;
    private List<BeanBowlerStats> bowlerStats;

    public BowlerAdapter(Context context, List<BeanBowlerStats> bowlerStats) {
        this.context = context;
        this.bowlerStats = bowlerStats;
    }

    @Override
    public int getItemCount() {
        return bowlerStats.size();
    }

    @NonNull
    @Override
    public BowlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View bowlerView = inflater.inflate(R.layout.item_bowlers, parent, false);
        return new BowlerViewHolder(bowlerView);
    }

    @Override
    public void onBindViewHolder(@NonNull BowlerViewHolder holder, int position) {
        BeanBowlerStats bowler = bowlerStats.get(position);

        // Bind data to the bowler view
        holder.bowlerName.setText(bowler.getBowlerName());
        holder.bowlerOvers.setText(bowler.getOvers());
        holder.bowlerMaidenOvers.setText(bowler.getMaidenOvers());
        holder.bowlerRuns.setText(bowler.getRuns());
        holder.bowlerWickets.setText(bowler.getWickets());
        holder.bowlerEconomy.setText(bowler.getEconomy());
        // Bind other bowling statistics here
    }

    static class BowlerViewHolder extends RecyclerView.ViewHolder {
        TextView bowlerName, bowlerOvers, bowlerMaidenOvers, bowlerRuns, bowlerWickets, bowlerEconomy;
        // Other bowling TextViews

        BowlerViewHolder(View itemView) {
            super(itemView);
            bowlerName = itemView.findViewById(R.id.im_bowlers_name);
            bowlerOvers = itemView.findViewById(R.id.im_bowlers_overs);
            bowlerMaidenOvers = itemView.findViewById(R.id.im_bowlers_maidenovers);
            bowlerRuns = itemView.findViewById(R.id.im_bowlers_runs);
            bowlerWickets = itemView.findViewById(R.id.im_bowlers_wickets);
            bowlerEconomy = itemView.findViewById(R.id.im_bowlers_economy);
            // Initialize other bowling TextViews
        }
    }
}
