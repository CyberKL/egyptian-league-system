package edu.ainshams.egyptianleaguesystem.model;

public class Team {

    private String name;
    private int teamId;
    private Player[] players;
    private Player captain;
    private Manager manager;
    private  Match[] matches;
    private int matchesPlayed;
    private int totalScore;
    private  static int numOfTeams = 0;

    public Team(String name, int teamId, Player captain, Manager manager) {
        this.name = name;
        this.teamId = teamId;
        this.captain = captain;
        this.manager = manager;
        this.matchesPlayed = 0;
        this.totalScore = 0;
        numOfTeams++;
    }
}
