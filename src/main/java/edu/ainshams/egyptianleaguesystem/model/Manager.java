package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public class Manager extends FootballCharacter{

    private Team team;
    private int trophies;
    private boolean wasPlayer;
    private static int numOfManagers;

    public Manager(String name, LocalDate dateOfBirth, String nationality, int yellowCards, int redCards, Team team, int trophies, boolean wasPlayer){
        super(name,dateOfBirth, nationality, yellowCards, redCards);
        this.team = team;
        this.trophies = trophies;
        this.wasPlayer = wasPlayer;
        numOfManagers++;
    }

}
