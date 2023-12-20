package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Forward extends Player {
    private int goalsScored;
    private double expectedGoals;
    private int shotsOnTarget;
    private double conversionRate;
    private int assists;


    public Forward(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position){
        super(name, dateOfBirth, nationality, playerId, number, team, height,weight,preferredFoot,position);
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
    public void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> teams, int choice) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Number\n2. Team\n 3. Height\n4. Weight\n5. Preferred Foot\n6. Position\n" +
                "7. Nationality\n8. Data of birth\n9. Player ID\n10. Yellow cards\n11. Red cards\n12. Goals Scored\n13. Shots on Target\n14. Assists");
        try{
        choice = scanner.nextInt();
        scanner.nextLine();}catch (InputMismatchException e){
            System.out.println("Invalid occurunce, Please reenter a valid input");
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








}
