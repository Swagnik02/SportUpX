package com.team.fantasy.Bean;

import java.io.Serializable;

public class BeanBatterStats implements Serializable {
    private String batterName,  runs,  ballsPlayed, fours,sixes,strikeRate;

    public BeanBatterStats(String batterName, String runs, String ballsPlayed, String fours, String sixes, String strikeRate) {
        this.batterName = batterName;
        this.runs = runs;
        this.ballsPlayed = ballsPlayed;
        this.fours = fours;
        this.sixes = sixes;
        this.strikeRate = strikeRate;
    }

    public String getBatterName() {
        return batterName;
    }

    public void setBatterName(String batterName) {
        this.batterName = batterName;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(String ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }
}