package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;

public class Referee extends FootballCharacter{

    private int matchesRefereed;
    private static int numOfReferees = 0;

    public Referee(String name, LocalDate dateOfBirth, String nationality, int yellowCards, int redCards){
        super(name, dateOfBirth, nationality);
        this.matchesRefereed = 0;
        numOfReferees++;
    }
<<<<<<< HEAD
=======

    public String displayRefereeInfo(){
        return "Name: "+name+"/nAge: "+age+"/nNationality: " +nationality+"/nMatches Refereed: " +matchesRefereed+ "/nYellow Cards: " +yellowCards+ "/nRed Cards: " +redCards;
    }

    public int getMatchesRefereed() {
        return matchesRefereed;
    }

    public void setMatchesRefereed(int matchesRefereed) {
        this.matchesRefereed = matchesRefereed;
    }
>>>>>>> 32b688f701b79799c98a49c950d6935bcb264a6f
}
