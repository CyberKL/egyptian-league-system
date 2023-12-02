package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Team {

    private String name;
    private int teamId;
    private ArrayList<Player> players;
    private Player captain;
    private Manager manager;
    private  ArrayList<Match> matches;
    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int totalScore;
    private  static int numOfTeams = 0;

    public Team(String name, int teamId, Manager manager) {
        this.name = name;
        this.teamId = teamId;
        this.manager = manager;
        this.matchesPlayed = 0;
        this.totalScore = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        numOfTeams++;
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
        return numOfTeams;
    }

    public int getTeamId() {
        return teamId;
    }

    public Manager getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "/nTeam Id: " + teamId +
                "/nCaptain: " + captain.getName() +
                "/nManager: " + manager.getName() +
                "/nMatches Played: " + matchesPlayed +
                "/nWins: " + wins +
                "/nDraws: " + draws +
                "/nLosses: " + losses +
                "/nTotal score: " + totalScore;
    }

    public static void enterMatchInfo(ArrayList<Team> teams, ArrayList<Manager> managers) {

        Manager manager = null;
        Scanner scanner = new Scanner(System.in);
        boolean duplicateTeamName;
        boolean managerFound = false;
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

            System.out.print("Enter the name of the manager: ");
            String managerName = scanner.nextLine();
            for (Manager element : managers) {
                if (element.getName().equalsIgnoreCase(managerName)) {
                    for (Team team : teams) {
                        if (team.getManager().getName().equalsIgnoreCase(element.getName())) {
                            System.out.println("This manager is already assigned to another team");
                            return;
                        }
                    }
                    manager = element;
                    managerFound = true;
                    break;
                }
            }
            if (!managerFound) {
                System.out.println("Manager not found!");
                return;
            }

            Team team = new Team(teamName, id, manager);
            teams.add(team);
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
    public void displayTeamPlayers() {
        for (Player player : this.players){
            System.out.println(player.getName());
        }
    }

    public void updateTeam(ArrayList<Team> teams, ArrayList<Player> players) throws DuplicateException{
        System.out.println("What do you want to update?");
        System.out.println("1. Name/n2. Id/n 3. Players/n4. Captain");
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
                String teamName = null;
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

    public void displayTeamMatches(){
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

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void deletePlayer(Player player){
        this.players.remove(player);
    }

}
