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
    private String winner;

    private Match(int matchId, LocalDate date, Team homeTeam, Team awayTeam, Referee referee, Score score, Stadium stadium) {
        this.matchId = matchId;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referee = referee;
        this.score = score;
        this.stadium = stadium;
        this.winner = null;
    }

    public int getMatchId() {
        return matchId;
    }

    public LocalDate getDate() {
        return date;
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
        boolean diffTeams;

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
                        break;
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
                                break;
                            }
                        }
                    }
                } while (!diffTeams);

                System.out.print("Enter referee name: ");
                String refName = scanner.nextLine();
                for (Referee element : referees) {
                    if (element.getName().equalsIgnoreCase(home)) {
                        referee = element;
                        break;
                    }
                }

                boolean available = false;
                while(!available) {
                    System.out.print("Enter stadium name: ");
                    String stadiumName = scanner.nextLine();
                    for (Stadium element : stadiums) {
                        if (element.getName().equalsIgnoreCase(stadiumName)) {
                            for (Match i : element.getUpcomingMatches()) {
                                if (i.getDate().isEqual(date)) {
                                    System.out.println("Stadium not available on " + date);
                                } else {
                                    stadium = element;
                                    available = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                do {
                    System.out.print("Enter score (home-away): ");
                    matchScore = scanner.nextLine();
                } while (!isValidScore(matchScore));
                score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));

                // Creates new match object with the data the user entered
                Match match = new Match(matchId, date, homeTeam, awayTeam, referee, score, stadium);

                //Updating related objects;
                homeTeam.addMatch(match);
                homeTeam.setMatchesPlayed(homeTeam.getMatchesPlayed()+1);
                awayTeam.addMatch(match);
                awayTeam.setMatchesPlayed(awayTeam.getMatchesPlayed()+1);
                referee.setMatchesRefereed(referee.getMatchesRefereed()+1);
                stadium.addUpcomingMatch(match);
                result(match);

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
    private static void result(Match match){
        if(match.score.getHomeTeam()>match.score.getAwayTeam()){
            match.homeTeam.setWins(match.homeTeam.getWins()+1);
            match.awayTeam.setLosses(match.awayTeam.getLosses()+1);
            match.winner = match.homeTeam.getName();
        }
        else if (match.score.getHomeTeam()<match.score.getAwayTeam()) {
            match.awayTeam.setWins(match.awayTeam.getWins()+1);
            match.homeTeam.setLosses(match.homeTeam.getLosses()+1);
            match.winner = match.awayTeam.getName();
        }
        else {
            match.homeTeam.setDraws(match.homeTeam.getDraws()+1);
            match.awayTeam.setDraws(match.awayTeam.getDraws()+1);
            match.winner = "draw";
        }
    }

    public void updateMatch(ArrayList<Team> teams, ArrayList<Referee> referees, ArrayList<Stadium> stadiums){

        System.out.println("What do you want to update?");
        System.out.println("1. Date/n2. Home team/n 3. Away team/n4. Referee/n5. Stadium/n6.Score");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        boolean outOfBounds = true;
        try{
            while (outOfBounds){
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > 6){
                    System.out.println("Choose a number from 1-6 please");
                }
                else {
                    outOfBounds = false;
                }
            }

        }
        catch (InputMismatchException e){
            System.out.println("Choose a number from 1-6 please");
            scanner.nextLine();
            //return;
        }
        switch (choice) {
            case 1: {
                boolean isValidDate = false;
                while(!isValidDate) {
                    System.out.print("Enter new date: ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    if (date.isBefore(LocalDate.now())) {
                        System.out.println("Please enter an upcoming date.");
                    }
                    else {
                        this.date = date;
                        isValidDate = true;
                    }
                }
                break;
            }
            case 2: {
                System.out.print("Enter home team: ");
                String home = scanner.nextLine();
                for (Team i : teams) {
                    if (i.getName().equals(home)) {
                        this.homeTeam.removeMatch(this);
                        this.homeTeam.setMatchesPlayed(this.homeTeam.getMatchesPlayed()-1);
                        if (this.winner.equalsIgnoreCase(this.homeTeam.getName())){
                            this.homeTeam.setWins(this.homeTeam.getWins()-1);
                            this.homeTeam = i;
                            this.homeTeam.setWins(this.homeTeam.getWins()+1);
                        }
                        else if (this.winner.equals("draw")){
                            this.homeTeam.setDraws(this.homeTeam.getDraws()-1);
                            this.homeTeam = i;
                            this.homeTeam.setDraws(this.homeTeam.getDraws()+1);
                        }
                        else {
                            this.homeTeam.setLosses(this.homeTeam.getLosses()-1);
                            this.homeTeam = i;
                            this.homeTeam.setLosses(this.homeTeam.getLosses()+1);
                        }
                        this.homeTeam.addMatch(this);
                        this.homeTeam.setMatchesPlayed(this.homeTeam.getMatchesPlayed()+1);
                        return;
                    }
                    System.out.println("Team not found");
                }
                break;
            }
            case 3: {
                System.out.print("Enter away team: ");
                String away = scanner.nextLine();
                for (Team i : teams) {
                    if (i.getName().equals(away)) {
                        this.awayTeam.removeMatch(this);
                        this.awayTeam.setMatchesPlayed(this.awayTeam.getMatchesPlayed()-1);
                        if (this.winner.equalsIgnoreCase(this.awayTeam.getName())){
                            this.awayTeam.setWins(this.awayTeam.getWins()-1);
                            this.awayTeam = i;
                            this.awayTeam.setWins(this.awayTeam.getWins()+1);
                        }
                        else if (this.winner.equals("draw")){
                            this.awayTeam.setDraws(this.awayTeam.getDraws()-1);
                            this.awayTeam = i;
                            this.awayTeam.setDraws(this.awayTeam.getDraws()+1);
                        }
                        else {
                            this.awayTeam.setLosses(this.awayTeam.getLosses()-1);
                            this.awayTeam = i;
                            this.awayTeam.setLosses(this.awayTeam.getLosses()+1);
                        }
                        this.awayTeam.addMatch(this);
                        this.awayTeam.setMatchesPlayed(this.awayTeam.getMatchesPlayed()+1);
                        return;
                    }
                    System.out.println("Team not found");
                }
                break;
            }
            case 4: {
                System.out.println("Enter new referee: ");
                String refName = scanner.nextLine();
                for (Referee i : referees) {
                    if (i.getName().equals(refName)) {
                        this.referee.setMatchesRefereed(this.referee.getMatchesRefereed()-1);
                        this.referee = i;
                        this.referee.setMatchesRefereed(this.referee.getMatchesRefereed()+1);
                        return;
                    }
                    System.out.println("Referee not found");
                }
                break;
            }
            case 5: {
                boolean available = false;
                boolean found = false;
                while (!available) {
                    System.out.println("Enter new stadium: ");
                    String stadiumName = scanner.nextLine();
                    for (Stadium i : stadiums) {
                        if (i.getName().equals(stadiumName)) {
                            for (Match upcoming : i.getUpcomingMatches()) {
                                if (upcoming.getDate().isEqual(this.date)) {
                                    System.out.println("Stadium not available on " + this.date);
                                } else {
                                    this.stadium.removeUpcomingMatch(this);
                                    this.stadium = i;
                                    this.stadium.addUpcomingMatch(this);
                                    available = true;
                                    found = true;
                                }
                            }
                        }
                    }
                    if(!found) {
                        System.out.println("Stadium not found");
                    }
                }
                break;
            }
            case 6: {
                System.out.println("Enter new Score (home-away): ");
                String matchScore = scanner.nextLine();
                Score score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                this.score = score;
                result(this);
                break;
            }
        }
            System.out.println("Match updated successfully!");
    }

    public void deleteMatch(ArrayList<Match> matches){
        matches.remove(this);
        System.out.println("Match deleted successfully!");
    }
    public String matchHeader(){
        return homeTeam.getName()+" : "+awayTeam.getName();
    }

}
