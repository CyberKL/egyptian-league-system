package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Score {

    private final int homeTeam;
    private final int awayTeam;

    public Score(@JsonProperty("homeTeam") int homeTeam,
                 @JsonProperty("awayTeam") int awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString(){
        return homeTeam+"-"+awayTeam;
    }

    public int getHomeTeam() {
        return homeTeam;
    }

    public int getAwayTeam() {
        return awayTeam;
    }
}
