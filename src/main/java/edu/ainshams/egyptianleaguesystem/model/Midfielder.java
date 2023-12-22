package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Midfielder extends Player{
    private int goalsScored;
    private int assists;
    private int interceptions;
    private int keyPasses;

    public Midfielder(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot){
        super(name, dateOfBirth, nationality, playerId, number, team, height, weight, preferredFoot);
        this.position = "Midfielder";
        this.goalsScored = 0;
        this.assists= 0;
        this.interceptions = 0;
        this.keyPasses = 0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Goals Scored: " + goalsScored +
                "\nAssists: " + assists +
                "\nInterceptions: " + interceptions +
                "\nKey Passes: " + keyPasses;
    }

    @Override
    public Optional<Integer> getGoalsScored() {
        return Optional.of(goalsScored);
    }

    public Optional<Integer> getAssists() {
        return Optional.of(assists);
    }

    public int getInterceptions() {
        return interceptions;
    }

    public int getKeyPasses() {
        return keyPasses;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }

    public void setKeyPasses(int keyPasses) {
        this.keyPasses = keyPasses;
    }

    public void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> teams, int choice) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Number\n3. Team\n 4. Height\n5. Weight\n6. Preferred Foot\n" +
                "7. Nationality\n8. Data of birth\n9. Player ID\n10. Yellow cards\n11. Red cards\n12. Goals scored\n13. Assists\n14. Interceptions\n15. Key passes");
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalid input, please enter a number");
        }

        if (choice<=11&&choice>=1){
            super.updatePlayerInfo(playersList, teams, choice);
        } else if (choice>11 && choice<16) {
            switch(choice){
                case 12:{
                    int addedGoals = 0;
                    try {
                        System.out.println("Enter num of goals to be added");
                        addedGoals = scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");
                    }
                    this.goalsScored += addedGoals;
                    break;

                }
                case 13:{
                    int addedAssists =0;
                    try {
                        System.out.println("Enter num of assists to be added");
                         addedAssists = scanner.nextInt();
                        scanner.nextLine();
                    }catch(InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");

                    }
                    this.assists += addedAssists;
                    break;
                }
                case 14:{
                    int addedInterceptions=0;
                    try{


                    System.out.println("Enter num of added interceptions");
                     addedInterceptions= scanner.nextInt();
                    scanner.nextLine();}
                    catch(InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");

                    }
                    this.interceptions+= addedInterceptions;
                    break;
                }
                case 15:{
                    int addedkeyPasses =0;
                    try {
                        System.out.println("Enter num of added key passes ");
                         addedkeyPasses = scanner.nextInt();
                        scanner.nextLine();
                    }catch(InputMismatchException e){
                        System.out.println("Invalid input, please enter a number");


                    }
                    this.keyPasses+= addedkeyPasses;
                    break;

                }
            }
        }
    }
}
