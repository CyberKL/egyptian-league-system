package edu.ainshams.egyptianleaguesystem.model;

import java.util.ArrayList;

public class Stadium {

    private final String name;
    private final int capacity;
    private final String city;
    private ArrayList<Match> upcomingMatches;
    private int matchesPlayedOn;

    public  Stadium(String name, int capacity, String city){
        this.name = name;
        this.capacity = capacity;
        this.city = city;
        this.matchesPlayedOn = 0;
    }

    public void addUpcomingMatch(Match upComingMatch) {
        upcomingMatches.add(upComingMatch);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<Match> getUpcomingMatches() {
        return upcomingMatches;
    }

    public int getMatchesPlayedOn() {
        return matchesPlayedOn;
    }

    public void setMatchesPlayedOn(int matchesPlayedOn) {
        this.matchesPlayedOn = matchesPlayedOn;
    }
}
