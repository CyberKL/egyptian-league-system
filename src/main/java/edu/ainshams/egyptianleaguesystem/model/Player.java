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
    protected String displayPlayerInfo(){

        return "Name: "+name+"/nNationality"+nationality+"/nAge: "+age+"/nPlayerId:"+playerId+"/nNumber:"+number+"/nTeam:"+team+"/nHeight:"+height+"/nWeight:"+weight+"/nPreferredFoot:"+preferredFoot+"/nPosition:"+position+"/nYellowCards:"+yellowCards+"/nRedCards:"+redCards;
    }

}
