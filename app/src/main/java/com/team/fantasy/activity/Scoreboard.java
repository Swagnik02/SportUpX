package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.MATCH_SCOREBOARD;
import static com.team.fantasy.APICallingPackage.Constants.MATCH_SCOREBOARD_TYPE;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    private BowlerAdapter team1bowlerAdapter,team2bowlerAdapter;

    String match_id="",team1name="",team2name="",team1Fullname,team2Fullname;
    int team1score=0,team1wickts=0;
    TextView team1Name,team1total_score,team1wickets,team1OVERS;

    int team2score=0,team2wickts=0;
    float team1overs=0,team2overs=0;
    TextView team2Name,team2total_score,team2wickets,team2OVERS;

    int currentTeamNo=1;
    CardView team1Container,team2Container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        ImageView closeButton = findViewById(R.id.im_CloseIcon);
        closeButton.setOnClickListener(v -> finish());

        ImageView changeTeam = findViewById(R.id.im_change);



        //INTENTS
        match_id = getIntent().getStringExtra("Match_ID");
        team1Fullname = getIntent().getStringExtra("Team1_Name");
        team2Fullname = getIntent().getStringExtra("Team2_Name");

        team1name =team1Fullname.substring(0,3).toUpperCase();
        System.out.println(team1name);
        team2name =team2Fullname.substring(0,3).toUpperCase();
        System.out.println(team2name);

        team1Container = findViewById(R.id.Team1_Maincontainer);
        team2Container = findViewById(R.id.Team2_Maincontainer);

        if (currentTeamNo==1){
            team2Container.setVisibility(View.GONE);
        }
        changeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentTeamNo==1) {
                    team1Container.setVisibility(View.GONE);
                    team2Container.setVisibility(View.VISIBLE);
                    currentTeamNo = 2;
//                    ShowToast(context, "TEAM "+ team2name);


                }
                else {
                    team2Container.setVisibility(View.GONE);
                    team1Container.setVisibility(View.VISIBLE);
                    currentTeamNo = 1;
//                    ShowToast(context, "TEAM "+ team1name);

                }
            }
        });


        //Team 1
        team1battingRecyclerView = findViewById(R.id.recyclerViewTeam1Batsmen);
        team1bowlingRecyclerView = findViewById(R.id.recyclerViewTeam1Bowlers);

        team1Name = findViewById(R.id.im_team1Name);
        team1total_score = findViewById(R.id.im_team1_total_score);
        team1OVERS = findViewById(R.id.im_team1_overs);
        team1wickets = findViewById(R.id.im_team1_wickets);

        team1battingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team1bowlingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //TEAM 2
        team2battingRecyclerView = findViewById(R.id.recyclerViewTeam2Batsmen);
        team2bowlingRecyclerView = findViewById(R.id.recyclerViewTeam2Bowlers);

        team2Name = findViewById(R.id.im_team2Name);
        team2total_score = findViewById(R.id.im_team2_total_score);
        team2OVERS = findViewById(R.id.im_team2_overs);
        team2wickets = findViewById(R.id.im_team2_wickets);

        team2battingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        team2bowlingRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        callMyMatchRecord(true);



        team1Name.setText(team1Fullname);
        team2Name.setText(team2Fullname);
    }

    private void initViews(){

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
                        if (team1name.equals(teamName)){
                            team1batsmenData.add(batsman);
                            team1score = team1score + Integer.valueOf(runs);
                        }
                        else {
                            team2batsmenData.add(batsman);
                            team2score = team2score + Integer.valueOf(runs);
                        }
                    } else if ("Bowler".equals(itemType)) {
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
                            team1overs = team1overs+Float.valueOf(overs);
                            team1wickts = team1wickts + Integer.valueOf(wickets);
                        }
                        else {
                            team2bowlersData.add(bowler);
                            team2overs = team2overs+Float.valueOf(overs);
                            team2wickts = team2wickts + Integer.valueOf(wickets);
                        }
                    }
                }

                // Initialize and set RecyclerView adapters
                team1batsmanAdapter = new BatsmanAdapter(this, team1batsmenData);
                team1bowlerAdapter = new BowlerAdapter(this, team1bowlersData);

                team1battingRecyclerView.setAdapter(team1batsmanAdapter);
                team1bowlingRecyclerView.setAdapter(team1bowlerAdapter);

                team2batsmanAdapter = new BatsmanAdapter(this, team2batsmenData);
                team2bowlerAdapter = new BowlerAdapter(this, team2bowlersData);

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

