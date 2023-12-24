package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Scanner;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class Stadium {

    private final String name;
    private final int id;
    private final int capacity;
    private final String city;
    private ArrayList<Match> upcomingMatches = new ArrayList<Match>();
    private int matchesPlayedOn;

    public Stadium(@JsonProperty("name") String name,
                   @JsonProperty("id") int id,
                   @JsonProperty("capacity") int capacity,
                   @JsonProperty("city") String city){
        this.name = name;
        this.id = id;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nCapacity: " + capacity +
                "\nCity: " + city  +
                "\nNumber of matches played on: " + matchesPlayedOn;
    }

    protected static void enterStadiumInfo(ArrayList<Stadium> stadiums) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stadium name: ");
        String name = scanner.nextLine();
        for (Stadium stadium:stadiums){
            if (stadium.getName().equalsIgnoreCase(name)){
                System.out.println("Stadium already exist!");
                return;
            }
        }
        System.out.print("Enter stadium ID");
        int id = scanner.nextInt();
        scanner.nextLine();
        isStadiumIdDuplicate(id, stadiums);

        System.out.print("Enter stadium capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter stadium city: ");
        String city = scanner.nextLine();

        Stadium stadium = new Stadium(name, id, capacity, city);
        stadiums.add(stadium);
    }
    private static void isStadiumIdDuplicate(int id, ArrayList<Stadium> stadiums) throws DuplicateException{
        for (Stadium element : stadiums){
            if(element.getId()==id){
                throw new DuplicateException("This team id is already taken");
            }
        }
    }
}
