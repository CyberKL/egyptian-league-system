package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Goalkeeper extends Player{
    private int cleanSheets;
    private int saves;

    public Goalkeeper(String name, LocalDate dateOfBirth, String nationality,int playerId, int number, Team team, int height, int weight, String preferredFoot){
        super(name, dateOfBirth, nationality,playerId,number,team,height,weight,preferredFoot);
        this.position = "Goalkeeper";
        this.cleanSheets = 0;
        this.saves=0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Clean Sheets: " + cleanSheets +
                "\nSaves: " + saves;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public int getSaves() {
        return saves;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    @Override
    public Optional<Integer> getGoalsScored() {
        return Optional.empty();
    }

    public Optional<Integer> getAssists() {
        return Optional.empty();
    }

    @Override
    protected void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> teams, int choice) throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Number\n3. Team\n 4. Height\n5. Weight\n6. Preferred Foot\n" +
                "7. Nationality\n8. Data of birth\n9. Player ID\n10. Yellow cards\n11. Red cards\n12. Clean sheets\n13. Saves");

        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch(InputMismatchException e){
            System.out.println("Invalid input, please enter a number");
        }
        if (choice>=1&&choice<=11){
            super.updatePlayerInfo(playersList, teams, choice);
        }
        else if(choice==12||choice==13){
            switch(choice){
                case 12: {
                    int noCleanSheets = 0;
                    try {
                        System.out.println("enter number of clean Sheets ");
                        noCleanSheets = scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");
                    }
                    this.cleanSheets+=noCleanSheets;
                    break;
                }
                case 13: {
                    int noSaves=0;
                    try {
                        System.out.println("enter number of saves");
                        noSaves = scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");
                    }
                    this.saves+=noSaves;
                    break;
                }
            }
        }
        else {
            System.out.println("Please enter a number between 1 and 13!");
        }

    }
}
