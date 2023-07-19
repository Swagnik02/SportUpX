package com.team.fantasy.Bean;

import org.json.JSONArray;


public class BeanRank  {


    String id,winners_count,note;

    JSONArray ranking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWinners_count() {
        return winners_count;
    }

    public void setWinners_count(String winners_count) {
        this.winners_count = winners_count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public JSONArray getRanking() {
        return ranking;
    }

    public void setRanking(JSONArray ranking) {
        this.ranking = ranking;
    }

}
