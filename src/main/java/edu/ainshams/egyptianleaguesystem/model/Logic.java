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
        Scanner idScanner = new Scanner(System.in);
        int id = idScanner.nextInt();
        idScanner.nextLine();
        for (Match element:matches) {
            if (element.getMatchId() == id) {
                element.deleteMatch(matches);
            }
        }
        System.out.println("Match not found, please provide a valid match id");
    }
    //End of match related methods

    //Start of team related methods
    public static String displayTeamInfo(){
        System.out.println("Enter team name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team element : teams){
            if (element.getName().equalsIgnoreCase(name)){
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
                element.deleteTeam(teams);
            }
        }
        System.out.println("Team not found, please provide a valid team id");
    }

}
