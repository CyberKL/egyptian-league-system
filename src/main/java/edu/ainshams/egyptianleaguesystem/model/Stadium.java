package edu.ainshams.egyptianleaguesystem.model;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void addUpcomingMatch(Match upcomingMatch) {
        upcomingMatches.add(upcomingMatch);
    }
    public void removeUpcomingMatch(Match upcomingMatch){
        upcomingMatches.remove(upcomingMatch);
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

    @Override
    public String toString() {
        return "Name: " + name +
                "\nCapacity: " + capacity +
                "\nCity: " + city  +
                "\nNumber of matches played on: " + matchesPlayedOn;
    }

    public static void enterStadiumInfo(ArrayList<Stadium> stadiums){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stadium name: ");
        String name = scanner.nextLine();
        for (Stadium stadium:stadiums){
            if (stadium.getName().equalsIgnoreCase(name)){
                System.out.println("Stadium already exist!");
                return;
            }
        }

        System.out.print("Enter stadium capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter stadium city: ");
        String city = scanner.nextLine();

        Stadium stadium = new Stadium(name, capacity, city);
        stadiums.add(stadium);
    }

}
