package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Defender extends Player{

    private int goalsScored;

    private int assists;

    private int tacklesWon;

    private int cleanSheets;


    public Defender(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot) {
        super(name, dateOfBirth, nationality, playerId, number, team, height, weight, preferredFoot);
        this.position = "Defender";
        this.goalsScored = 0;
        this.assists = 0;
        this.tacklesWon = 0;
        this.cleanSheets = 0;
    }


    @Override
    public String toString() {
        return super.toString() +
                "Goals Scored: " + goalsScored +
                "\nAssists: " + assists +
                "\nTackles Won: " + tacklesWon +
                "\nClean Sheets: " + cleanSheets;
    }

    public Optional<Integer> getGoalsScored(){
        return Optional.of(goalsScored);
    }
    public Optional<Integer> getAssists(){
        return Optional.of(assists);
    }

    public int getTacklesWon() {
        return tacklesWon;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setTacklesWon(int tacklesWon) {
        this.tacklesWon = tacklesWon;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    @Override
    protected void updatePlayerInfo(ArrayList<Player> playersList, ArrayList<Team> teams, int choice) throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Number\n3. Team\n 4. Height\n5. Weight\n6. Preferred Foot\n" +
                "7. Nationality\n8. Data of birth\n9. Player ID\n10. Yellow cards\n11. Red cards\n12. Goals Scored\n13. Tackles Won\n14. Assists\n15. Clean Sheets");
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid occurrence, Please reenter a valid input");
        }
        if (choice >= 1 && choice <= 11){
            super.updatePlayerInfo(playersList, teams, choice);
        } else if (choice <= 15) {
            switch (choice) {
                case 12: {
                    System.out.println("Enter amount of Goals to add");
                    int newGoals = 0;
                    try {
                        newGoals = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException z) {
                        System.out.println("Please Enter valid Input");
                    }
                    this.goalsScored += newGoals;
                    break;
                }
                case 13: {
                    System.out.println("Enter amount of Tackles to add");
                    int newTacklesWon = 0;
                    try {
                        newTacklesWon = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException f) {
                        System.out.println("Please Enter valid Input");
                    }
                    this.tacklesWon += newTacklesWon;
                    break;
                }
                case 14: {
                    System.out.println("Enter amount of Assists to add");
                    int newAssists = 0;
                    try {
                        newAssists = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException z) {
                        System.out.println("Please Enter valid Input");
                    }
                    this.assists += newAssists;
                    break;
                }
                case 15: {
                    System.out.println("Enter amount of Clean Sheets to add");
                    int newCleanSheets = 0;
                    try {
                        newCleanSheets = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException z) {
                        System.out.println("Please Enter valid Input");
                    }
                    this.cleanSheets += newCleanSheets;
                    break;
                }
            }
        }
    }
}