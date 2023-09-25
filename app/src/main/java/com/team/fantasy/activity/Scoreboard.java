package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Config.MATCH_SCOREBOARD;
import static com.team.fantasy.APICallingPackage.Constants.MATCH_SCOREBOARD_TYPE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.Bean.BeanBatterStats;
import com.team.fantasy.Bean.BeanBowlerStats;
import com.team.fantasy.R;
import com.team.fantasy.adapter.BatsmanAdapter;
import com.team.fantasy.adapter.BowlerAdapter;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;


public class Scoreboard extends AppCompatActivity implements ResponseManager {
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;
    Context context;
    Scoreboard activity;
    private RecyclerView team1battingRecyclerView, team1bowlingRecyclerView;
    private RecyclerView team2battingRecyclerView, team2bowlingRecyclerView;
    private BatsmanAdapter team1batsmanAdapter, team2batsmanAdapter;
    private BowlerAdapter team1bowlerAdapter, team2bowlerAdapter;

    String match_id = "", team1name = "", team2name = "", team1Fullname, team2Fullname;
    int team1score = 0, team1wickts = 0;
    TextView team1Name, team1total_score, team1wickets, team1OVERS;

    int team2score = 0, team2wickts = 0;
    float team1overs = 0, team2overs = 0;
    TextView team2Name, team2total_score, team2wickets, team2OVERS, team1EXTRAS, team1EXTRAS_DESC, team2EXTRAS, team2EXTRAS_DESC;

    int currentTeamNo = 1;
    LinearLayout team1Container, team2Container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        //INTENTS
        match_id = getIntent().getStringExtra("Match_ID");
        team1Fullname = getIntent().getStringExtra("Team1_Name");
        team2Fullname = getIntent().getStringExtra("Team2_Name");

        ImageView closeButton = findViewById(R.id.im_CloseIcon);
        closeButton.setOnClickListener(v -> finish());

        ImageView commentary = findViewById(R.id.im_commentary);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CommentaryActivity.class);
                i.putExtra("Match_ID",match_id);
                startActivity(i);
            }
        });



        team1name = team1Fullname.substring(0, 3).toUpperCase();
        team2name = team2Fullname.substring(0, 3).toUpperCase();

        team1Container = findViewById(R.id.im_Team1Layout);
        team2Container = findViewById(R.id.im_Team2Layout);


        //Team 1
        team1battingRecyclerView = findViewById(R.id.recyclerViewTeam1Batsmen);
        team1bowlingRecyclerView = findViewById(R.id.recyclerViewTeam1Bowlers);

        team1Name = findViewById(R.id.im_team1Name);
        team1total_score = findViewById(R.id.im_team1_total_score);
        team1OVERS = findViewById(R.id.im_team1_overs);
        team1wickets = findViewById(R.id.im_team1_wickets);
        team1EXTRAS = findViewById(R.id.im_team1_total_extras);
        team1EXTRAS_DESC = findViewById(R.id.im_team1_extra_desc);

        team1battingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team1bowlingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TEAM 2
        team2battingRecyclerView = findViewById(R.id.recyclerViewTeam2Batsmen);
        team2bowlingRecyclerView = findViewById(R.id.recyclerViewTeam2Bowlers);

        team2Name = findViewById(R.id.im_team2Name);
        team2total_score = findViewById(R.id.im_team2_total_score);
        team2OVERS = findViewById(R.id.im_team2_overs);
        team2wickets = findViewById(R.id.im_team2_wickets);
        team2EXTRAS = findViewById(R.id.im_team2_total_extras);
        team2EXTRAS_DESC = findViewById(R.id.im_team2_extra_desc);

        team2battingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team2bowlingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TEAM VIEW CHANGE
        if (currentTeamNo == 1) {
            team1Name.setEnabled(false); //team1 button disabled
            team2Name.setEnabled(true);

//            team1Name.setTextColor(getResources().getColor(R.color.deactivate_text_color));
            team2Container.setVisibility(View.GONE);
            team1Container.setVisibility(View.VISIBLE);

            team2Name.setBackgroundColor(getResources().getColor(R.color.white));
            team2Name.setTextColor(getResources().getColor(R.color.colorPrimary));

            team1Name.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            team1Name.setTextColor(getResources().getColor(R.color.white));

        }
        team2Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1Container.setVisibility(View.GONE);
                team2Container.setVisibility(View.VISIBLE);
                team1Name.setBackgroundColor(getResources().getColor(R.color.white));
                team1Name.setTextColor(getResources().getColor(R.color.colorPrimary));

                team2Name.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                team2Name.setTextColor(getResources().getColor(R.color.white));
                team1Name.setEnabled(true);
                team2Name.setEnabled(false);
                currentTeamNo = 2;
            }
        });

        team1Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2Container.setVisibility(View.GONE);
                team1Container.setVisibility(View.VISIBLE);

                team2Name.setBackgroundColor(getResources().getColor(R.color.white));
                team2Name.setTextColor(getResources().getColor(R.color.colorPrimary));

                team1Name.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                team1Name.setTextColor(getResources().getColor(R.color.white));
                team1Name.setEnabled(false);
                team2Name.setEnabled(true);
                currentTeamNo = 1;
            }
        });

        callMyMatchRecord(true);

        team1Name.setText(team1Fullname);
        team2Name.setText(team2Fullname);
    }

    private void callMyMatchRecord(boolean isShowLoader) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("match_id", match_id);
            apiRequestManager.callAPI(MATCH_SCOREBOARD, jsonObject, this, activity, MATCH_SCOREBOARD_TYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        try {

            JSONArray data = result.getJSONArray("data");

            // Separate batsmen and bowlers data
            List<BeanBatterStats> team1batsmenData = new ArrayList<>();
            List<BeanBowlerStats> team1bowlersData = new ArrayList<>();

            List<BeanBatterStats> team2batsmenData = new ArrayList<>();
            List<BeanBowlerStats> team2bowlersData = new ArrayList<>();

            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);
                String itemType = item.getString("type");
                String teamName = item.getString("team_name");

                if ("Batsman".equals(itemType)) {

                    String batterName = item.getString("name");
                    String runs = item.getString("score");
                    String ballsPlayed = item.getString("balls");
                    String fours = item.getString("four");
                    String sixes = item.getString("six");
                    String strikeRate = item.getString("strike_rate");

                    // Create a new batsman bean object and add it to the list
                    BeanBatterStats batsman = new BeanBatterStats(batterName, runs, ballsPlayed, fours, sixes, strikeRate);
                    if (team1name.equals(teamName)) {
                        team1batsmenData.add(batsman);
                        team1score = team1score + Integer.valueOf(runs);
                    } else {
                        team2batsmenData.add(batsman);
                        team2score = team2score + Integer.valueOf(runs);
                    }
                } else if ("Bowler".equals(itemType)) {
//                        String teamName = item.getString("team_name");
                    String bowlerName = item.getString("name");
                    String overs = item.getString("overs");
                    String maidenOvers = item.getString("maiden");
                    String runs = item.getString("runs");
                    String wickets = item.getString("wicket");
                    String economy = item.getString("eco");

                    // Create a new bowler bean object and add it to the list
                    BeanBowlerStats bowler = new BeanBowlerStats(bowlerName, overs, maidenOvers, runs, wickets, economy);

                    if (team1name.equals(teamName)) {
                        team1bowlersData.add(bowler);
                        team1overs = team1overs + Float.valueOf(overs);
                        team1wickts = team1wickts + Integer.valueOf(wickets);
                    } else {
                        team2bowlersData.add(bowler);
                        team2overs = team2overs + Float.valueOf(overs);
                        team2wickts = team2wickts + Integer.valueOf(wickets);
                    }
                } else if ("Extras".equals(itemType)) {
//                        String teamName = item.getString("team_name");
                    int wides = Integer.valueOf(item.getString("Wides"));
                    int noBall = Integer.valueOf(item.getString("NB"));
                    int legBy = Integer.valueOf(item.getString("LB"));
                    int by = Integer.valueOf(item.getString("B"));

                    int total_extras = wides + noBall + legBy + by;
                    String total_extras_string = total_extras + "";
                    if (team1name.equals(teamName)) {
                        team1EXTRAS.setText(total_extras_string);
                        team1score = team1score + total_extras;
                        team1EXTRAS_DESC.setText("(wd " + wides + ",lb " + legBy + ",nb " + noBall + ",B " + by + ")");
                    } else {
                        team2EXTRAS.setText(total_extras_string);
                        team2score = team2score + total_extras;
                        team2EXTRAS_DESC.setText("(wd " + wides + ",lb " + legBy + ",nb " + noBall + ",B " + by + ")");
                    }

                }
            }

            // Initialize and set RecyclerView adapters
            team1batsmanAdapter = new BatsmanAdapter(this, team1batsmenData);
            team1bowlerAdapter = new BowlerAdapter(this, team2bowlersData);

            team1battingRecyclerView.setAdapter(team1batsmanAdapter);
            team1bowlingRecyclerView.setAdapter(team1bowlerAdapter);

            team2batsmanAdapter = new BatsmanAdapter(this, team2batsmenData);
            team2bowlerAdapter = new BowlerAdapter(this, team1bowlersData);

            team2battingRecyclerView.setAdapter(team2batsmanAdapter);
            team2bowlingRecyclerView.setAdapter(team2bowlerAdapter);

            team1total_score.setText(String.valueOf(team1score));
            team1OVERS.setText(String.valueOf(team2overs));
            team1wickets.setText(String.valueOf(team2wickts));

            team2total_score.setText(String.valueOf(team2score));
            team2OVERS.setText(String.valueOf(team1overs));
            team2wickets.setText(String.valueOf(team1wickts));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Context mContext, String type, String message) {
        // Handle errors here
    }
}

