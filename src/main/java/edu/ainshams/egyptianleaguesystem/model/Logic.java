package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Logic {

    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Match> matches = new ArrayList<Match>();
    private ArrayList<Manager> managers = new ArrayList<Manager>();
    private ArrayList<Referee> referees = new ArrayList<Referee>();
    private ArrayList<Stadium> stadiums = new ArrayList<Stadium>();


    public void enterMatchInfo (){
        Team homeTeam = null;
        Team awayTeam = null;
        Referee referee = null;
        Stadium stadium = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter match ID:");
        int matchId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Date (yyyy-mm-dd):");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("Enter home team name:");
        String home = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(home)){
                homeTeam = team;
            }
        }

        System.out.println("Enter away team name:");
        String away = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(home)){
                awayTeam = team;
            }
        }

        System.out.println("Enter referee name: ");
        String refName = scanner.nextLine();
        for (Referee element : referees){
            if (element.getName().equalsIgnoreCase(home)){
                referee = element;
            }
        }

        System.out.println("Enter stadium name: ");
        String stadiumName = scanner.nextLine();
        for (Stadium element : stadiums){
            if (element.getName().equalsIgnoreCase(stadiumName)){
                stadium = element;
            }
        }

        System.out.println("Enter score (home-away): ");
        String matchScore = scanner.nextLine();
        Score score = new Score(Integer.parseInt(matchScore.substring(0,1)),Integer.parseInt(matchScore.substring(2)));

        Match match = new Match(matchId, date, homeTeam, awayTeam, referee, score, stadium);

        matches.add(match);

    }

}
