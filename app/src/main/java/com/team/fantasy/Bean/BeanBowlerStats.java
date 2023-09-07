package com.team.fantasy.Bean;

import java.io.Serializable;

public class BeanBowlerStats implements Serializable {
    private String bowlerName,overs,maidenOvers,runs,wickets,economy;

    public BeanBowlerStats(String bowlerName, String overs, String maidenOvers, String runs, String wickets, String economy) {
        this.bowlerName = bowlerName;
        this.overs = overs;
        this.maidenOvers = maidenOvers;
        this.runs = runs;
        this.wickets = wickets;
        this.economy = economy;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getMaidenOvers() {
        return maidenOvers;
    }

    public void setMaidenOvers(String maidenOvers) {
        this.maidenOvers = maidenOvers;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }
}