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


    public static void enterMatchInfo (){
        Team homeTeam = null;
        Team awayTeam = null;
        Referee referee = null;
        Stadium stadium = null;
        String matchScore;
        Score score;

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        boolean diffTeams = true;

        while (!isValid) {
            try {

                System.out.println("Enter match ID:");
                int matchId = scanner.nextInt();
                scanner.nextLine();
                //Check if id is taken
                isMatchIdDuplicate(matchId);

                System.out.println("Enter Date (yyyy-mm-dd):");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                System.out.println("Enter home team name:");
                String home = scanner.nextLine();
                for (Team team : teams) {
                    if (team.getName().equalsIgnoreCase(home)) {
                        homeTeam = team;
                    }
                }

                // Home and away team must be different
                do {
                    System.out.println("Enter away team name:");
                    String away = scanner.nextLine();
                    if (away.equals(home)) {
                        System.out.println("Home and away team must be different");
                        diffTeams = false;
                    } else {
                        diffTeams = true;
                        for (Team team : teams) {
                            if (team.getName().equalsIgnoreCase(home)) {
                                awayTeam = team;
                            }
                        }
                    }
                } while (!diffTeams);

                System.out.println("Enter referee name: ");
                String refName = scanner.nextLine();
                for (Referee element : referees) {
                    if (element.getName().equalsIgnoreCase(home)) {
                        referee = element;
                    }
                }

                System.out.println("Enter stadium name: ");
                String stadiumName = scanner.nextLine();
                for (Stadium element : stadiums) {
                    if (element.getName().equalsIgnoreCase(stadiumName)) {
                        stadium = element;
                    }
                }

                do {
                    System.out.println("Enter score (home-away): ");
                    matchScore = scanner.nextLine();
                } while (!isValidScore(matchScore));
                score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));

                // Creates new match object with the data the user entered
                Match match = new Match(matchId, date, homeTeam, awayTeam, referee, score, stadium);

                // Add the new match object to the matches ArrayList
                matches.add(match);
                isValid = true;
            }
            catch (DateTimeParseException e){
                System.out.println("Invalid date please enter a valid date");
                scanner.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("Id must be a number please enter a valid id");
                scanner.nextLine();
            }
            catch (Exception e){
                System.out.println("Invalid input please try again");
                scanner.nextLine();
            }
        }

    }
    private static void isMatchIdDuplicate(int matchId) throws DuplicateException{
        for (Match element : matches){
            if(element.getMatchId()==matchId){
                throw new DuplicateException("This match id is already taken");
            }
        }
    }
    private static boolean isValidScore(String matchScore) {
        return matchScore.matches("\\d+-\\d+");
    }

}
