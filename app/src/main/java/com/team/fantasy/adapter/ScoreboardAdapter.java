//package com.team.fantasy.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.team.fantasy.R;
//import com.team.fantasy.activity.Scoreboard;
//import java.util.List;
//
//public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ViewHolder> {
//
//    private List<Scoreboard.Item> itemList; // List of Item objects (can be Batsman or Bowler)
//
//    // Constructor for ScoreboardAdapter
//    public ScoreboardAdapter(List<Scoreboard.Item> itemList) {
//        this.itemList = itemList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//
//        // Inflate different item layouts based on the view type (batsman or bowler)
//        if (viewType == Scoreboard.Item.TYPE_BATSMAN) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_batsman, parent, false);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bowlers, parent, false);
//        }
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Scoreboard.Item item = itemList.get(position);
//
//        // Bind data to TextViews based on the item type
//        if (item.getType() == Scoreboard.Item.TYPE_BATSMAN) {
//            Scoreboard.BatsmanItem batsmanItem = (Scoreboard.BatsmanItem) item;
//            holder.batsmanName.setText(batsmanItem.getBatsmanName());
//            holder.runs.setText(batsmanItem.getRuns());
//            holder.ballsPlayed.setText(batsmanItem.getBallsPlayed());
//            holder.fours.setText(batsmanItem.getFours());
//            holder.sixes.setText(batsmanItem.getSixes());
//            holder.strikeRate.setText(batsmanItem.getStrikeRate());
//        } else {
//            Scoreboard.BowlerItem bowlerItem = (Scoreboard.BowlerItem) item;
//            holder.bowlerName.setText(bowlerItem.getBowlerName());
//            holder.overs.setText(bowlerItem.getOvers());
//            holder.maidenOvers.setText(bowlerItem.getMaidenOvers());
//            holder.runsGiven.setText(bowlerItem.getRunsGiven());
//            holder.wickets.setText(bowlerItem.getWickets());
//            holder.economy.setText(bowlerItem.getEconomy());
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        // Determine the view type based on the item's type
//        return itemList.get(position).getType();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        // Common Views for both Batsman and Bowler
//        public TextView batsmanName, bowlerName;
//
//        // Views for Batsman
//        public TextView runs, ballsPlayed, fours, sixes, strikeRate;
//
//        // Views for Bowler
//        public TextView overs, maidenOvers, runsGiven, wickets, economy;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            batsmanName = itemView.findViewById(R.id.im_batsman_name);
//            bowlerName = itemView.findViewById(R.id.im_bowlers_name);
//
//            runs = itemView.findViewById(R.id.im_batsman_runs);
//            ballsPlayed = itemView.findViewById(R.id.im_batsman_balls_played);
//            fours = itemView.findViewById(R.id.im_batsman_fours);
//            sixes = itemView.findViewById(R.id.im_batsman_sixes);
//            strikeRate = itemView.findViewById(R.id.im_batsman_StrikeRate);
//
//            overs = itemView.findViewById(R.id.im_bowlers_overs);
//            maidenOvers = itemView.findViewById(R.id.im_bowlers_maidenovers);
//            runsGiven = itemView.findViewById(R.id.im_bowlers_runs);
//            wickets = itemView.findViewById(R.id.im_bowlers_wickets);
//            economy = itemView.findViewById(R.id.im_bowlers_economy);
//        }
//    }
//}
