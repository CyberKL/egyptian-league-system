package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public abstract class Player extends FootballCharacter{

    protected int playerId;
    protected int number;
    protected Team team;
    protected int height;
    protected int weight;
    protected String preferredFoot;
    protected String position;
    private static int numOfPlayers = 0;

    protected Player(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position, int yellowCards, int redCards){
        super(name, dateOfBirth, nationality, yellowCards, redCards);
        this.playerId = playerId;
        this.number = number;
        this.team = team;
        this.height = height;
        this.weight = weight;
        this.preferredFoot = preferredFoot;
        this.position = position;
        numOfPlayers ++;
    }

    @Override
    public String toString() {
        return "Player Id: " + playerId +
                "/nNumber: " + number +
                "/nTeam: " + team +
                "/nHeight: " + height +
                "/nWeight: " + weight +
                "/nPreferredFoot: " + preferredFoot +
                "/nPosition: " + position +
                "/nName: " + name +
                "/nNationality: " + nationality +
                "/nYellowCards: " + yellowCards +
                "/nRedCards: " + redCards +
                "/nAge: " + age;
    }

    public int getPlayerId() {
        return playerId;
    }
}
