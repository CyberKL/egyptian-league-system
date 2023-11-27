package edu.ainshams.egyptianleaguesystem.model;

public class Stadium {

    private final String name;
    private final int capacity;
    private final String city;

    private int matchesPlayedOn;

    public  Stadium(String name, int capacity, String city){
        this.name = name;
        this.capacity = capacity;
        this.city = city;
        this.matchesPlayedOn = 0;
    }

}
