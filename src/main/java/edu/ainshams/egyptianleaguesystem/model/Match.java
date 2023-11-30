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

    public int getMatchId() {
        return matchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Referee getReferee() {
        return referee;
    }

    public Score getScore() {
        return score;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }
}
