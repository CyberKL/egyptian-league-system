package edu.ainshams.egyptianleaguesystem.model;

import java.util.ArrayList;

public class Team {

    private String name;
    private int teamId;
    private ArrayList<Player> players;
    private Player captain;
    private Manager manager;
    private  ArrayList<Match> matches;
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

    public String getName() {
        return name;
    }

    public int getTeamId() {
        return teamId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCaptain() {
        return captain;
    }

    public Manager getManager() {
        return manager;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public static int getNumOfTeams() {
        return numOfTeams;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCaptain(Player captain) {
        this.captain = captain;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void removeMatch(Match match) {
        matches.remove(match);
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public static void setNumOfTeams(int numOfTeams) {
        Team.numOfTeams = numOfTeams;
    }
}
