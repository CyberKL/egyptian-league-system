package edu.ainshams.egyptianleaguesystem.model;

public class Score {

    private int homeTeam;
    private int awayTeam;

    public Score(int homeTeam, int awayTeam){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString(){
        return homeTeam+":"+awayTeam;
    }

}
