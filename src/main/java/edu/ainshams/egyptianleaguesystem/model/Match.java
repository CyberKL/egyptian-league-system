package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Match {

    private int matchId;
    private LocalDate date;
    private  Team homeTeam;
    private Team awayTeam;
    private Referee referee;
    private Score score;
    private Stadium stadium;

    public Match(int matchId, LocalDate date, Team homeTeam, Team awayTeam, Referee referee, Score score, Stadium stadium) {
        this.matchId = matchId;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referee = referee;
        this.score = score;
        this.stadium = stadium;
    }

    public int getMatchId() {
        return matchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Referee getReferee() {
        return referee;
    }

    public Score getScore() {
        return score;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    @Override
    public String toString() {
        return "Match Id: " + matchId +
                "/nDate: " + date +
                "/nHome team: " + homeTeam.getName() +
                "/nAway team: " + awayTeam.getName() +
                "/nReferee: " + referee.getName() +
                "/nScore: " + score.toString() +
                "/nStadium: " + stadium.getName();
    }

    public static void enterMatchInfo (ArrayList<Team> teams, ArrayList<Referee> referees, ArrayList<Stadium> stadiums, ArrayList<Match> matches){
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

                System.out.print("Enter match ID: ");
                int matchId = scanner.nextInt();
                scanner.nextLine();
                //Check if id is taken
                isMatchIdDuplicate(matchId, matches);

                System.out.print("Enter Date (yyyy-mm-dd): ");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                System.out.print("Enter home team name: ");
                String home = scanner.nextLine();
                for (Team team : teams) {
                    if (team.getName().equalsIgnoreCase(home)) {
                        homeTeam = team;
                    }
                }

                // Home and away team must be different
                do {
                    System.out.print("Enter away team name: ");
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

                System.out.print("Enter referee name: ");
                String refName = scanner.nextLine();
                for (Referee element : referees) {
                    if (element.getName().equalsIgnoreCase(home)) {
                        referee = element;
                    }
                }

                System.out.print("Enter stadium name: ");
                String stadiumName = scanner.nextLine();
                for (Stadium element : stadiums) {
                    if (element.getName().equalsIgnoreCase(stadiumName)) {
                        stadium = element;
                    }
                }

                do {
                    System.out.print("Enter score (home-away): ");
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
    private static void isMatchIdDuplicate(int matchId, ArrayList<Match> matches) throws DuplicateException{
        for (Match element : matches){
            if(element.getMatchId()==matchId){
                throw new DuplicateException("This match id is already taken");
            }
        }
    }
    private static boolean isValidScore(String matchScore) {
        return matchScore.matches("\\d+-\\d+");
    }

    public void updateMatch(ArrayList<Team> teams, ArrayList<Referee> referees, ArrayList<Stadium> stadiums){

        System.out.println("What do you want to update?");
        System.out.println("1. Date/n2. Home team/n 3. Away team/n4. Referee/n5. Stadium/n6.Score");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        try{
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Choose a number from 1-6 please");
            scanner.nextLine();
        }
        switch (choice) {
            case 1: {
                System.out.print("Enter new date: ");
                LocalDate date = LocalDate.parse(scanner.nextLine());
                this.setDate(date);
                break;
            }
            case 2: {
                System.out.print("Enter home team: ");
                String home = scanner.nextLine();
                for (Team i : teams) {
                    if (i.getName().equals(home)) {
                        this.setHomeTeam(i);
                    } else {
                        System.out.println("Team not found");
                    }
                }
                break;
            }
            case 3: {
                System.out.print("Enter away team: ");
                String away = scanner.nextLine();
                for (Team i : teams) {
                    if (i.getName().equals(away)) {
                        this.setAwayTeam(i);
                    } else {
                        System.out.println("Team not found");
                    }
                }
                break;
            }
            case 4: {
                System.out.println("Enter new referee: ");
                String refName = scanner.nextLine();
                for (Referee i : referees) {
                    if (i.getName().equals(refName)) {
                        this.setReferee(i);
                    } else {
                        System.out.println("Referee not found");
                    }
                }
                break;
            }
            case 5: {
                System.out.println("Enter new stadium: ");
                String stadiumName = scanner.nextLine();
                for (Stadium i : stadiums) {
                    if (i.getName().equals(stadiumName)) {
                        this.setStadium(i);
                    } else {
                        System.out.println("Stadium not found");
                    }
                }
                break;
            }
            case 6: {
                System.out.println("Enter new Score (home-away): ");
                String matchScore = scanner.nextLine();
                Score score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                this.setScore(score);
                break;
            }
        }
            System.out.println("Match updated successfully!");
    }

    public void deleteMatch(ArrayList<Match> matches){
        matches.remove(this);
        System.out.println("Match deleted successfully!");
    }

}
