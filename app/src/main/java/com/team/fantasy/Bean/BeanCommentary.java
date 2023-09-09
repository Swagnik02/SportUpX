package com.team.fantasy.Bean;



import java.io.Serializable;



public class BeanCommentary implements Serializable {

    private String id, match_id, contest_id, title, contest_name, contest_description;
    private String message, description;
    private String inning, over,batsman,bowler,runs,commentary;

    public BeanCommentary (String Inning, String Overs, String Batsman, String Bowler, String Runs, String Commentary){
        this.inning = Inning;
        this.over =Overs;
        this.batsman = Batsman;
        this.bowler = Bowler;
        this.runs = Runs;
        this.commentary = Commentary;
    }

    public String getInning() {
        return inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getBatsman() {
        return batsman;
    }

    public void setBatsman(String batsman) {
        this.batsman = batsman;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }
}

