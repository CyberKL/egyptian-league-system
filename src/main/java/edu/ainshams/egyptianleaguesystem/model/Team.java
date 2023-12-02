package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Team {

    private String name;
    private int teamId;
    private ArrayList<Player> players;
    private Player captain;
    private Manager manager;
    private  ArrayList<Match> matches;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int totalScore;
    private  static int numOfTeams = 0;

    public Team(String name, int teamId, Player captain, Manager manager) {
        this.name = name;
        this.teamId = teamId;
        this.captain = captain;
        this.manager = manager;
        this.matchesPlayed = 0;
        this.totalScore = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        numOfTeams++;
    }

    public String getName() {
        return name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
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

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public static int getNumOfTeams() {
        return numOfTeams;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "/nTeam Id: " + teamId +
                "/nCaptain: " + captain.getName() +
                "/nManager: " + manager.getName() +
                "/nMatches Played: " + matchesPlayed +
                "/nWins: " + wins +
                "/nDraws: " + draws +
                "/nLosses: " + losses +
                "/nTotal score: " + totalScore;
    }

    public void displayTeamPlayers() {
        for (Player player : this.players){
            System.out.println(player.getName());
        }
    }

    public void displayTeamMatches(){
        boolean heldMatches = false;
        boolean upcomingMatches = false;
        for (Match match : matches){
            if (match.getDate().isBefore(LocalDate.now())){
                heldMatches = true;
            }
            if (!match.getDate().isBefore(LocalDate.now())){
                upcomingMatches = true;
            }
        }
        if (heldMatches){
            System.out.println("Matches held: ");
            for (Match match : matches){
                if (match.getDate().isBefore(LocalDate.now())){
                    System.out.println(match.matchHeader());
                }
            }
        }
        if (upcomingMatches){
            System.out.println("Upcoming matches: ");
            for (Match match : matches){
                if (!match.getDate().isBefore(LocalDate.now())){
                    System.out.println(match.matchHeader());
                }
            }
        }
        if (!heldMatches && !upcomingMatches){
            System.out.println("This team has no matches");
        }
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void deletePlayer(Player player){
        this.players.remove(player);
    }

}
