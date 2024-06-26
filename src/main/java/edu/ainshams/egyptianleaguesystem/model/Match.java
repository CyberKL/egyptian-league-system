package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class Match {

    private final int matchId;
    private LocalDate date;
    private  Team homeTeam;
    private Team awayTeam;
    private Referee referee;
    private Score score;
    private Stadium stadium;
    private String winner;

    public Match(@JsonProperty("matchId") int matchId,
                 @JsonProperty("date") LocalDate date,
                 @JsonProperty("homeTeam") Team homeTeam,
                 @JsonProperty("awayTeam") Team awayTeam,
                 @JsonProperty("referee") Referee referee,
                 @JsonProperty("stadium") Stadium stadium) {
        this.matchId = matchId;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.referee = referee;
        this.stadium = stadium;
        this.winner = null;
    }

    public int getMatchId() {
        return matchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Score getScore() {
        return score;
    }

    public String getWinner() {
        return winner;
    }


    @Override
    public String toString() {
        String homeTeamName = (homeTeam != null) ? homeTeam.getName() : "N/A";
        String awayTeamName = (awayTeam != null) ? awayTeam.getName() : "N/A";
        String info = "Match Id: " + matchId +
                "\nDate: " + date +
                "\nHome team: " + homeTeamName +
                "\nAway team: " + awayTeamName +
                "\nReferee: " + referee.getName() +
                "\nStadium: " + stadium.getName();
        if (this.date.isBefore(LocalDate.now())){
            info = info.concat("\nScore: " + score.toString());
        }
        return info;
    }

    protected static void enterMatchInfo (ArrayList<Team> teams, ArrayList<Referee> referees, ArrayList<Stadium> stadiums, ArrayList<Match> matches){
        Team homeTeam = null;
        Team awayTeam = null;
        Referee referee = null;
        Stadium stadium = null;
        String matchScore;
        Score score = null;

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
                    if (element.getName().equalsIgnoreCase(refName)) {
                        referee = element;
                        break;
                    }
                }

                boolean notAvailable = false;
                do {
                    System.out.print("Enter stadium name: ");
                    String stadiumName = scanner.nextLine();
                    for (Stadium element : stadiums) {
                        if (element.getName().equalsIgnoreCase(stadiumName)) {
                            for (Match i : element.getUpcomingMatches()) {
                                if (i.getDate().isEqual(date)) {
                                    System.out.println("Stadium not available on " + date);
                                    notAvailable = true;
                                    break;
                                }
                            }
                            if (!notAvailable){
                                stadium = element;
                                break;
                            }
                        }
                    }
                }while (notAvailable);

                if (date.isBefore(LocalDate.now())) {
                    do {
                        System.out.print("Enter score (home-away): ");
                        matchScore = scanner.nextLine();
                    } while (!isValidScore(matchScore));
                    score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                }

                // Creates new match object with the data the user entered
                Match match = new Match(matchId, date, homeTeam, awayTeam, referee, stadium);

                //Updating related objects;
                homeTeam.addMatch(match);
                awayTeam.addMatch(match);
                referee.setMatchesRefereed(referee.getMatchesRefereed()+1);
                if(date.isBefore(LocalDate.now())){
                    homeTeam.setMatchesPlayed(homeTeam.getMatchesPlayed()+1);
                    awayTeam.setMatchesPlayed(awayTeam.getMatchesPlayed()+1);
                    stadium.setMatchesPlayedOn(stadium.getMatchesPlayedOn()+1);
                }else{
                    stadium.addUpcomingMatch(match);
                }
                if (score != null) {
                    match.score = score;
                    result(match);
                }

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

    public static void result(Match match){
        if(match.score.getHomeTeam()>match.score.getAwayTeam()){
            match.homeTeam.setWins(match.homeTeam.getWins()+1);
            match.homeTeam.setTotalScore(match.homeTeam.getTotalScore()+3);
            match.awayTeam.setLosses(match.awayTeam.getLosses()+1);
            match.winner = match.homeTeam.getName();
        }
        else if (match.score.getHomeTeam()<match.score.getAwayTeam()) {
            match.awayTeam.setWins(match.awayTeam.getWins()+1);
            match.awayTeam.setTotalScore(match.awayTeam.getTotalScore()+3);
            match.homeTeam.setLosses(match.homeTeam.getLosses()+1);
            match.winner = match.awayTeam.getName();
        }
        else {
            match.homeTeam.setDraws(match.homeTeam.getDraws()+1);
            match.homeTeam.setTotalScore(match.homeTeam.getTotalScore()+1);
            match.awayTeam.setDraws(match.awayTeam.getDraws()+1);
            match.awayTeam.setTotalScore(match.awayTeam.getTotalScore()+1);
            match.winner = "draw";
        }
        match.homeTeam.setGoalsFor(match.homeTeam.getGoalsFor()+match.score.getHomeTeam());
        match.homeTeam.setGoalsAgainst(match.homeTeam.getGoalsAgainst()+match.score.getAwayTeam());
        match.homeTeam.calcGoalDiff();
        match.awayTeam.setGoalsFor(match.awayTeam.getGoalsFor()+match.score.getAwayTeam());
        match.awayTeam.setGoalsAgainst(match.awayTeam.getGoalsAgainst()+match.score.getHomeTeam());
        match.awayTeam.calcGoalDiff();
    }

    protected void updateMatch(ArrayList<Team> teams, ArrayList<Referee> referees, ArrayList<Stadium> stadiums){

        System.out.println("What do you want to update?");
        System.out.println("1. Date\n2. Home team\n 3. Away team\n4. Referee\n5. Stadium\n6. Score");
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
                    try {
                        System.out.print("Enter new date: ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        if (date.isBefore(LocalDate.now())) {
                            System.out.println("Please enter an upcoming date.");
                        } else {
                            if (this.date.isBefore(LocalDate.now())) {
                                this.stadium.setMatchesPlayedOn(stadium.getMatchesPlayedOn() - 1);
                                this.stadium.addUpcomingMatch(this);
                                this.homeTeam.setGoalsFor(this.homeTeam.getGoalsFor() - this.score.getHomeTeam());
                                this.homeTeam.setGoalsAgainst(this.homeTeam.getGoalsAgainst() - this.score.getAwayTeam());
                                this.homeTeam.calcGoalDiff();
                                this.awayTeam.setGoalsFor(this.awayTeam.getGoalsAgainst() - this.score.getAwayTeam());
                                this.awayTeam.setGoalsAgainst(this.awayTeam.getGoalsAgainst() - this.score.getHomeTeam());
                                this.awayTeam.calcGoalDiff();
                                this.homeTeam.setMatchesPlayed(this.homeTeam.getMatchesPlayed() - 1);
                                this.awayTeam.setMatchesPlayed(this.awayTeam.getMatchesPlayed() - 1);
                                this.score = null;
                            }
                            this.date = date;
                            isValidDate = true;
                        }
                    }catch (InputMismatchException ime){
                        System.out.println("Please enter a valid date");
                    }
                }
                break;
            }
            case 2: {
                System.out.print("Enter home team: ");
                String home = scanner.nextLine();
                if (this.date.isBefore(LocalDate.now())) {
                    for (Team i : teams) {
                        if (i.getName().equalsIgnoreCase(home)) {
                            if (i != homeTeam && i != awayTeam) {
                                this.homeTeam.setMatchesPlayed(this.homeTeam.getMatchesPlayed() - 1);
                                this.homeTeam.setGoalsFor(this.homeTeam.getGoalsFor()-this.score.getHomeTeam());
                                this.homeTeam.setGoalsAgainst(this.homeTeam.getGoalsAgainst()-this.score.getAwayTeam());
                                this.homeTeam.calcGoalDiff();
                                this.homeTeam.removeMatch(this);
                                if (this.winner.equalsIgnoreCase(this.homeTeam.getName())) {
                                    this.homeTeam.setWins(this.homeTeam.getWins() - 1);
                                    this.homeTeam.setTotalScore(this.homeTeam.getTotalScore()-3);
                                } else if (this.winner.equals("draw")) {
                                    this.homeTeam.setDraws(this.homeTeam.getDraws() - 1);
                                    this.homeTeam.setTotalScore(this.homeTeam.getTotalScore()-1);
                                } else {
                                    this.homeTeam.setLosses(this.homeTeam.getLosses() - 1);
                                }
                                this.homeTeam = i;
                                result(this);
                                this.homeTeam.addMatch(this);
                                this.homeTeam.setMatchesPlayed(this.homeTeam.getMatchesPlayed() + 1);
                            }
                        }
                    }
                    //System.out.println("This team is already a side in the match");
                    System.out.println("Team not found");
                }
                else {
                    for (Team j : teams){
                        if (j.getName().equalsIgnoreCase(home)){
                            if (j != awayTeam && j != homeTeam){
                                this.homeTeam.removeMatch(this);
                                this.homeTeam = j;
                                this.homeTeam.addMatch(this);
                            }
                            else {
                                System.out.println("This team is already a side in the match");
                                return;
                            }
                            break;
                        }
                    }
                    System.out.println("Team not found");
                    return;
                }
                break;
            }
            case 3: {
                System.out.print("Enter away team: ");
                String away = scanner.nextLine();
                if (this.date.isBefore(LocalDate.now())) {
                    for (Team i : teams) {
                        if (i.getName().equalsIgnoreCase(away)) {
                            if (i != homeTeam && i != awayTeam) {
                                this.awayTeam.setMatchesPlayed(this.awayTeam.getMatchesPlayed() - 1);
                                this.awayTeam.setGoalsFor(this.awayTeam.getGoalsFor()-this.score.getAwayTeam());
                                this.awayTeam.setGoalsAgainst(this.awayTeam.getGoalsAgainst()-this.score.getHomeTeam());
                                this.awayTeam.calcGoalDiff();
                                this.awayTeam.removeMatch(this);
                                if (this.winner.equalsIgnoreCase(this.awayTeam.getName())) {
                                    this.awayTeam.setWins(this.awayTeam.getWins() - 1);
                                    this.awayTeam.setTotalScore(this.awayTeam.getTotalScore()-3);
                                } else if (this.winner.equals("draw")) {
                                    this.awayTeam.setDraws(this.awayTeam.getDraws() - 1);
                                    this.awayTeam.setTotalScore(this.awayTeam.getTotalScore()-1);
                                } else {
                                    this.awayTeam.setLosses(this.awayTeam.getLosses() - 1);
                                }
                                this.awayTeam = i;
                                result(this);
                                this.awayTeam.addMatch(this);
                                this.awayTeam.setMatchesPlayed(this.awayTeam.getMatchesPlayed() + 1);
                            } else {
                                System.out.println("This team is already a side in the match");
                            }
                            return;
                        }
                    }
                    System.out.println("Team not found");
                }
                else {
                    for (Team j : teams){
                        if (j.getName().equalsIgnoreCase(away)){
                            if (j != awayTeam && j != homeTeam){
                                this.awayTeam.removeMatch(this);
                                this.awayTeam = j;
                                this.awayTeam.addMatch(this);
                            }
                            else {
                                System.out.println("This team is already a side in the match");
                                return;
                            }
                            break;
                        }
                    }
                    System.out.println("Team not found");
                    return;
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
                                    if (this.date.isBefore(LocalDate.now())){
                                        this.stadium.setMatchesPlayedOn(this.stadium.getMatchesPlayedOn()-1);
                                        this.stadium= i;
                                        this.stadium.setMatchesPlayedOn(this.stadium.getMatchesPlayedOn()+1);
                                    }
                                    else {
                                        this.stadium.removeUpcomingMatch(this);
                                        this.stadium = i;
                                        this.stadium.addUpcomingMatch(this);
                                    }
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
                if (this.date.isBefore(LocalDate.now())) {
                    String matchScore;
                    do {
                        System.out.println("Enter new Score (home-away): ");
                        matchScore = scanner.nextLine();
                    }while (!isValidScore(matchScore));
                    Score score = new Score(Integer.parseInt(matchScore.substring(0, 1)), Integer.parseInt(matchScore.substring(2)));
                    this.homeTeam.setGoalsFor(this.homeTeam.getGoalsFor() - this.score.getHomeTeam());
                    this.homeTeam.setGoalsAgainst(this.homeTeam.getGoalsAgainst() - this.score.getAwayTeam());
                    this.awayTeam.setGoalsFor(this.awayTeam.getGoalsAgainst() - this.score.getAwayTeam());
                    this.awayTeam.setGoalsAgainst(this.awayTeam.getGoalsAgainst() - this.score.getHomeTeam());
                    if (this.getWinner().equals(this.getHomeTeam().getName())){
                        this.getHomeTeam().setWins(this.getHomeTeam().getWins()-1);
                        this.getHomeTeam().setTotalScore(this.getHomeTeam().getTotalScore()-3);
                        this.getAwayTeam().setLosses(this.getAwayTeam().getLosses()-1);
                    }
                    else if (this.getWinner().equals(this.getAwayTeam().getName())) {
                        this.getAwayTeam().setWins(this.getAwayTeam().getWins()-1);
                        this.getAwayTeam().setTotalScore(this.getAwayTeam().getTotalScore()-3);
                        this.getHomeTeam().setLosses(this.getHomeTeam().getLosses()-1);
                    }
                    else {
                        this.getHomeTeam().setDraws(this.getHomeTeam().getDraws()-1);
                        this.getHomeTeam().setTotalScore(this.getHomeTeam().getTotalScore()-1);
                        this.getAwayTeam().setDraws(this.getAwayTeam().getDraws()-1);
                        this.getAwayTeam().setTotalScore(this.getAwayTeam().getTotalScore()-1);
                    }
                    this.score = score;
                    result(this);
                }
                else {
                    System.out.println("Match not played yet!");
                    return;
                }
                break;
            }
        }
            System.out.println("Update process finished");
    }

    public String matchHeader(){
        String header;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        if (this.date.isBefore(LocalDate.now())){
            header = homeTeam.getName()+"   "+this.score.getHomeTeam()+" : "+this.score.getAwayTeam()+"   "+awayTeam.getName();
        }
        else {
            String formattedDate = date.format(formatter);
            header = homeTeam.getName()+"  "+formattedDate+"  "+awayTeam.getName();
        }
        return header;
    }

}
