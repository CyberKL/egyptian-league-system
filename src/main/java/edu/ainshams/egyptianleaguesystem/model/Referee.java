package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public class Referee extends FootballCharacter{

    private int matchesRefereed;
    private static int numOfReferees = 0;

    public Referee(String name, LocalDate dateOfBirth, String nationality, int yellowCards, int redCards){
        super(name, dateOfBirth, nationality, yellowCards, redCards);
        this.matchesRefereed = 0;
        numOfReferees++;
    }

    public String displayRefereeInfo(){
        return "Name: "+name+"/nAge: "+age+"/nNationality: " +nationality+"/nMatches Refereed: " +matchesRefereed+ "/nYellow Cards: " +yellowCards+ "/nRed Cards: " +redCards;
    }

    public int getMatchesRefereed() {
        return matchesRefereed;
    }

    public void setMatchesRefereed(int matchesRefereed) {
        this.matchesRefereed = matchesRefereed;
    }
}
