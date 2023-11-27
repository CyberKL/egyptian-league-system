package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public class Match {

    private int matchId;
    private LocalDate date;
    private  Team team;
    private Referee referee;
    private Score score;
    private Stadium stadium;

    public Match(int matchId, LocalDate date, Team team, Referee referee, Score score, Stadium stadium) {
        this.matchId = matchId;
        this.date = date;
        this.team = team;
        this.referee = referee;
        this.score = score;
        this.stadium = stadium;
    }
}
