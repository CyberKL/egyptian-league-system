package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public class Match {

    private int matchId;
    private LocalDate date;
    private  Team homeTeam;
    private Team awayTeam;
    private Referee referee;
    private Score score;
    private Stadium stadium;

    public Match(int matchId, LocalDate date, Team homeTeam, Team awayTeam, Referee referee, Score score, Stadium stadium) {
        this.matchId = matchId;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referee = referee;
        this.score = score;
        this.stadium = stadium;
    }
}
