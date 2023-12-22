package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Logic {

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<Match> matches = new ArrayList<Match>();
    private static ArrayList<Manager> managers = new ArrayList<Manager>();
    private static ArrayList<Referee> referees = new ArrayList<Referee>();
    private static ArrayList<Stadium> stadiums = new ArrayList<Stadium>();

    public static ArrayList<Team> getTeams() {
        return teams;
    }
    public static void addTeam(Team team){
        teams.add(team);
    }
    public static void removeTeam(Team team){
        teams.remove(team);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
    public static void addPlayer(Player player){
        players.add(player);
    }
    public static void removePlayer(Player player){
        players.remove(player);
    }

    public static ArrayList<Stadium> getStadiums() {
        return stadiums;
    }
    public static void addStadium(Stadium stadium){
        stadiums.add(stadium);
    }
    public static void removeStadium(Stadium stadium){
        stadiums.remove(stadium);
    }

    public static ArrayList<Match> getMatches() {
        return matches;
    }
    public static void addMatch(Match match){
        matches.add(match);
    }
    public static void removeMatch(Match match){
        matches.remove(match);
    }

    public static ArrayList<Referee> getReferees() {
        return referees;
    }
    public static void addReferee(Referee referee){
        referees.add(referee);
    }
    public static void removeReferee(Referee referee){
        referees.remove(referee);
    }

    public static ArrayList<Manager> getManagers() {
        return managers;
    }
    public static void addManager(Manager manager){
        managers.add(manager);
    }
    public static void removeManager(Manager manager){
        managers.remove(manager);
    }

    //Start of match related methods
    public static void enterMatchInfo(){
        Match.enterMatchInfo(teams, referees, stadiums, matches);
    }

    public static String displayMatchInfo(){
        System.out.print("Enter the id of the match you want to display: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Match element : matches){
            if (element.getMatchId()==id){
                return element.toString();
            }
        }
        return "Match not found, please provide a valid match Id";
    }

    public static void updateMatch(){
        System.out.print("Enter the id of the match you want to update: ");
        Scanner idScanner = new Scanner(System.in);
        int id = idScanner.nextInt();
        idScanner.nextLine();
        for (Match element : matches){
            if (element.getMatchId()==id){
                element.updateMatch(teams, referees, stadiums);
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match Id");
    }

    public static void deleteMatch(){
        System.out.print("Enter the id of the match you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Match element:matches) {
            if (element.getMatchId() == id) {
                matches.remove(element);
                System.out.println("Match deleted successfully!");
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match id");
    }
    //End of match related methods

    //Start of team related methods
    public static void enterTeamInfo(){
        Team.enterTeamInfo(teams,managers);
    }

    public static void updateTeam() throws DuplicateException{
        System.out.print("Enter the name of the team you want to update: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team element : teams){
            if (element.getName().equalsIgnoreCase(name)){
                element.updateTeam(teams, players);
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match Id");
    }

    public static String displayTeamInfo(){
        System.out.println("Enter team name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team element : teams){
            if (element.getName().equalsIgnoreCase(name)){
                element.calcTotalScore();
                return element.toString();
            }
        }
        return "Team not found, please provide a valid team name";
    }

    public static void displayTeamMatches(){
        System.out.println("Enter the name of the team you want to see its matches: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(name)){
                team.displayTeamMatches();
            }
        }
    }

    public static void displayTeamPlayers(){
        System.out.println("Enter the name of the team you want to see its players: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(name)){
                team.displayTeamPlayers();
            }
        }
    }

    public static void addPlayerToTeam(){
        String teamName = null;
        String playerName = null;
        int id = 0;
        boolean teamFound = false;
        try {
            System.out.print("Enter the team name you would like to add player to: ");
            Scanner scanner = new Scanner(System.in);
            teamName = scanner.nextLine();
            System.out.println("Enter the player name and id you would like to add: ");
            System.out.print("Name: ");
            playerName = scanner.nextLine();
            System.out.print("Id: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input, please try again!");
        }
        catch (Exception e){
            System.out.println("Try again!");
        }
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(teamName)){
                teamFound = true;
                for (Player player : players){
                    if (player.getPlayerId()==id && player.getName().equalsIgnoreCase(playerName)){
                        team.addPlayer(player);
                        System.out.println("Player added successfully to "+team.getName());
                        return;
                    }
                }
                System.out.println("Player not found");
            }
        }
        if (!teamFound){
            System.out.println("Team not found");
        }
    }

    public static void deletePlayerFromTeam(){
        String teamName = null;
        String playerName = null;
        int id = 0;
        boolean teamFound = false;
        try {
            System.out.print("Enter the team name you would like to delete player from: ");
            Scanner scanner = new Scanner(System.in);
            teamName = scanner.nextLine();
            System.out.println("Enter the player name and id you would like to delete: ");
            System.out.print("Name: ");
            playerName = scanner.nextLine();
            System.out.print("Id: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input, please try again!");
        }
        catch (Exception e){
            System.out.println("Try again!");
        }
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(teamName)){
                teamFound = true;
                for (Player player : players){
                    if (player.getPlayerId()==id && player.getName().equalsIgnoreCase(playerName)){
                        team.deletePlayer(player);
                        System.out.println("Player deleted successfully from "+team.getName());
                        return;
                    }
                }
                System.out.println("Player not found");
            }
        }
        if (!teamFound){
            System.out.println("Team not found");
        }
    }

    public static void displayTotalTeams (){
        for (Team team : teams){
            System.out.println(team.getName());
        }
    }

    public static int calcNumOfTeams(){
        return Team.getNumOfTeams();
    }

    public static void deleteTeam(){
        System.out.print("Enter the Id of the team you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Team element:teams) {
            if (element.getTeamId() == id) {
                teams.remove(element);
                System.out.println("Team deleted successfully!");
                return;
            }
        }
        System.out.println("Team not found, please provide a valid team id");
    }
    //End of team related methods

    //Start of stadium related methods
    public static void enterStadiumInfo() throws DuplicateException{
        Stadium.enterStadiumInfo(stadiums);
    }

    public static String displayStadiumInfo(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium you want to display: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums){
            if (stadium.getName().equalsIgnoreCase(name)){
                return stadium.toString();
            }
        }
        return "Stadium not found, please enter a valid name";
    }

    public static String displayUpcomingMatches(){
        StringBuilder upcomingMatches = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium to see upcoming matches: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums) {
            if (stadium.getName().equalsIgnoreCase(name)) {
                if (stadium.getUpcomingMatches().isEmpty()){
                    return "No upcoming matches found";
                }
                else {
                    for (Match match : stadium.getUpcomingMatches()) {
                        upcomingMatches.append(match.matchHeader()).append("\n");
                    }
                    return upcomingMatches.toString();
                }
            }
        }
        return "Stadium not found, please enter a valid name";
    }

    public static String deleteStadium(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium you want to delete: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums) {
            if (stadium.getName().equalsIgnoreCase(name)) {
                stadiums.remove(stadium);
                return "Stadium deleted successfully!";
            }
        }
        return "Stadium not found, please enter a valid name";
    }
    //End of stadium related methods

    //Start of referee related methods
    public static void displayTotalReferees(){
        System.out.println("Total number of referees: " + Integer.toString(Referee.getNumOfReferees()));
    }

    public static String deleteReferee(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the ID of the referee you want to delete");
        int refereeId = scanner.nextInt();
        scanner.nextLine();
        for (Referee referee : referees){
            if (referee.getRefereeId()==refereeId){
                referees.remove(referee);
                return "This referee has been deleted successfully";
            }
        }
        return "Referee not found";
    }
}
