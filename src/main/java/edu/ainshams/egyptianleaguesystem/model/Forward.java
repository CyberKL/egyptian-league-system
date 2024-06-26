package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

@JsonTypeName("Forward")
public class Forward extends Player {
    private int goalsScored;
    private double expectedGoals;
    private int shotsOnTarget;
    private double conversionRate;
    private int assists;


    public Forward(@JsonProperty("name") String name,
                   @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                   @JsonProperty("nationality") String nationality,
                   @JsonProperty("playerId") int playerId,
                   @JsonProperty("number") int number,
                   @JsonProperty("team") Team team,
                   @JsonProperty("height") int height,
                   @JsonProperty("weight") int weight,
                   @JsonProperty("preferredFoot") String preferredFoot) {

        super(name, dateOfBirth, nationality, playerId, number, team, height, weight, preferredFoot);
        this.position = "Forward";
        this.goalsScored = 0;
        this.expectedGoals = 0.0;
        this.shotsOnTarget = 0;
        this.conversionRate = 0.0;
        this.assists = 0;
    }


    @Override
    public String toString() {
        return super.toString()+
                "Goals Scored: " + goalsScored +
                "\nExpected Goals: " + expectedGoals +
                "\nShots On Target: " + shotsOnTarget +
                "\nConversion Rate: " + conversionRate +
                "\nAssists: " + assists;
    }

    @Override
    public Optional<Integer> getGoalsScored() {
        return Optional.of(goalsScored);
    }

    public int getShotsOnTarget() {
        return shotsOnTarget;
    }

    public Optional<Integer> getAssists() {
        return Optional.of(assists);
    }

    public double getExpectedGoals() {
        return expectedGoals;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setShotsOnTarget(int shotsOnTarget) {
        this.shotsOnTarget = shotsOnTarget;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Override
    protected void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> teams, int choice) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Number\n3. Team\n 4. Height\n5. Weight\n6. Preferred Foot\n" +
                "7. Nationality\n8. Data of birth\n9. Player ID\n10. Yellow cards\n11. Red cards\n12. Goals Scored\n13. Shots on Target\n14. Assists");
        try{
        choice = scanner.nextInt();
        scanner.nextLine();}catch (InputMismatchException e){
            System.out.println("Invalid occurrence, Please reenter a valid input");
        }
        if(choice >=1 && choice<=11){
            super.updatePlayerInfo(playersList, teams, choice);
        } else if (choice <= 14) {
            switch (choice){
                case 12: {
                    System.out.println("Enter amount of Goals to add");
                    int newGoals = 0;
                    try { newGoals = scanner.nextInt();
                    scanner.nextLine();}catch (InputMismatchException z){
                        System.out.println("Please Enter valid Input");
                    }
                    this.goalsScored += newGoals;
                    calcConversionRate();
                    calcExpectedGoals();
                    break;
                }
                case 13: {
                    System.out.println("Enter amount of Shots to add");
                    int newShotsOnTarget = 0;
                    try{ newShotsOnTarget = scanner.nextInt();
                    scanner.nextLine();}catch (InputMismatchException f){
                        System.out.println("Please Enter valid Input");
                    }
                    this.shotsOnTarget += newShotsOnTarget;
                    calcConversionRate();
                    calcExpectedGoals();
                    break;
                }
                case 14: {
                    System.out.println("Enter amount of Assists to add");
                    int newAssists = 0;
                    try{ newAssists = scanner.nextInt();
                    scanner.nextLine();}catch (InputMismatchException z){
                        System.out.println("Please Enter valid Input");
                    }
                    this.assists += newAssists;
                    break;
                }
            }
        }

    }

    private void calcConversionRate(){
        this.conversionRate = (double) goalsScored /shotsOnTarget;


    }

    private void calcExpectedGoals(){
        double expectedGoalsFromShots = shotsOnTarget * expectedGoals;
        double expectedGoalsFromConversion = goalsScored / conversionRate;
        this.expectedGoals = expectedGoalsFromShots + expectedGoalsFromConversion;
    }

    public void updateStats () {
        calcConversionRate();
        calcExpectedGoals();
    }
}
