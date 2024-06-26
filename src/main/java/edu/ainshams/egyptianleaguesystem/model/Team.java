package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class Team {

    private String name;
    private int teamId;

    private ArrayList<Player> players = new ArrayList<Player>();
    private Player captain;
    private Manager manager;
    private  ArrayList<Match> matches = new ArrayList<Match>();
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int totalScore;
    private double averageAge;
    //private static int numOfTeams = 0;

    public Team(@JsonProperty("name") String name, @JsonProperty("teamId") int teamId) {
        this.name = name;
        this.teamId = teamId;
        this.matchesPlayed = 0;
        this.totalScore = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.goalDifference = 0;
        this.averageAge = 0.0;
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
        return Logic.getTeams().size();
    }

    public int getTeamId() {
        return teamId;
    }

    public Manager getManager() {
        return manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer (Player player){
        this.players.remove(player);
    }

    public void deletePlayer(Player player){
        this.players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public Player getCaptain() {
        return captain;
    }

    public void setCaptain(Player captain) {
        this.captain = captain;
    }

    public double getAverageAge() {
        calcAverageAge();
        return averageAge;
    }

    @Override
    public String toString() {
        String captainName = (captain != null) ? captain.getName() : "N/A";
        String managerName = (manager != null) ? manager.getName() : "N/A";

        return "Name: " + name +
                "\nTeam Id: " + teamId +
                "\nCaptain: " + captainName +
                "\nManager: " + managerName +
                "\nMatches Played: " + matchesPlayed +
                "\nWins: " + wins +
                "\nDraws: " + draws +
                "\nLosses: " + losses +
                "\nGoals for: " + goalsFor +
                "\nGoals against: " + goalsAgainst +
                "\nGoals difference: " + goalDifference +
                "\nTotal score: " + totalScore;
    }

    protected static void enterTeamInfo(ArrayList<Team> teams) {

        Scanner scanner = new Scanner(System.in);
        boolean duplicateTeamName;
        String teamName;
        try {
            do {
                duplicateTeamName = false;
                System.out.print("Enter the team name: ");
                teamName = scanner.nextLine();
                for (Team team : teams) {
                    if (team.getName().equalsIgnoreCase(teamName)) {
                        System.out.println("There is already a team with this name");
                        duplicateTeamName = true;
                        break;
                    }
                }
            } while (duplicateTeamName);

            System.out.print("Enter the team Id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            isTeamIdDuplicate(id, teams);

            Team team = new Team(teamName, id);
            teams.add(team);
            System.out.println("Team created successful");

        }
        catch (InputMismatchException e){
            System.out.println("Id must be a number");
        }
        catch (Exception e){
            System.out.println("Invalid input");
        }

    }
    private static void isTeamIdDuplicate(int id, ArrayList<Team> teams) throws DuplicateException{
        for (Team element : teams){
            if(element.getTeamId()==id){
                throw new DuplicateException("This team id is already taken");
            }
        }
    }
    protected void displayTeamPlayers() {
        for (Player player : this.players){
            System.out.println(player.getName());
        }
    }

    protected void updateTeam(ArrayList<Team> teams, ArrayList<Player> players) throws DuplicateException{
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Id\n 3. Players\n4. Captain");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean outOfBounds = true;
        try{
            while (outOfBounds){
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 4){
                    System.out.println("Choose a number from 1-4 please");
                }
                else {
                    outOfBounds = false;
                }
            }

        }
        catch (InputMismatchException e){
            System.out.println("Choose a number from 1-4 please");
            scanner.nextLine();
            //return;
        }
        boolean duplicateTeamName;
        switch (choice){
            case 1:{
                String teamName;
                do {
                    duplicateTeamName = false;
                    System.out.print("Enter the team name: ");
                    teamName = scanner.nextLine();
                    for (Team team : teams) {
                        if (team.getName().equalsIgnoreCase(teamName)) {
                            System.out.println("There is already a team with this name");
                            duplicateTeamName = true;
                            break;
                        }
                    }
                } while (duplicateTeamName);
                this.name = teamName;
                break;
            }
            case 2: {
                System.out.print("Enter new Id: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                isTeamIdDuplicate(id, teams);
                this.teamId = id;
                break;
            }
            case 3:{
                boolean enterAgain;
                do {
                    boolean playerFound = false;
                    System.out.println("Enter the name and id of the player you want to add: ");
                    System.out.print("Name: ");
                    String playerName = scanner.nextLine();
                    System.out.print("Id: ");
                    int playerId = scanner.nextInt();
                    scanner.nextLine();
                    for (Player player : players) {
                        if (player.getName().equalsIgnoreCase(playerName) && player.getPlayerId() == playerId) {
                            this.players.add(player);
                            System.out.println("Player added successfully!");
                            playerFound = true;
                            break;
                        }
                    }
                    if (!playerFound) {
                        System.out.println("Player not found");
                    }
                    System.out.println("Do you want to enter another player (y-n): ");
                    String another = scanner.nextLine();
                    switch (another) {
                        case "y":
                            enterAgain = true;
                            break;
                        case "n":
                            enterAgain = false;
                            break;
                        default:
                            System.out.println("Invalid input");
                            return;
                    }
                }while (enterAgain);
                break;
            }
            case 4: {
                boolean captainFound = false;
                System.out.println("Enter the name and id of the new captain: ");
                System.out.print("Name: ");
                String captainName = scanner.nextLine();
                System.out.print("Id: ");
                int captainId = scanner.nextInt();
                scanner.nextLine();

                for (Player player : this.players){
                    if (player.getName().equalsIgnoreCase(captainName) && player.getPlayerId()==captainId){
                        this.captain = player;
                        System.out.println("Captain updated!");
                        captainFound = true;
                        break;
                    }
                }
                if (!captainFound){
                    System.out.println("Captain not found in this team players (Captain must be from the team)!");
                }
                break;
            }
        }
    }

    protected void displayTeamMatches(){
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

    private void calcAverageAge(){
        int numOfPlayers = players.size();
        int totalAge = 0;
        for (Player player : players){
            totalAge += player.age;
        }
        averageAge = (double) totalAge/numOfPlayers;
    }

    public void calcTotalScore() {
        this.totalScore = (3 * this.wins)+(this.draws);
    }

    public void calcGoalDiff(){
        this.goalDifference = this.goalsFor - this.goalsAgainst;
    }
}
