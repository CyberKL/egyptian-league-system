package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Referee extends FootballCharacter{

    private int matchesRefereed;
    private int refereeId;
    private static int numOfReferees = 0;

    public Referee(String name, LocalDate dateOfBirth, String nationality, int refereeId){
        super(name, dateOfBirth, nationality);
        this.refereeId = refereeId;
        this.matchesRefereed = 0;
        numOfReferees++;
    }

    public int getMatchesRefereed() {
        return matchesRefereed;
    }
    public void setMatchesRefereed(int matchesRefereed) {
        this.matchesRefereed = matchesRefereed;
    }

    public static int getNumOfReferees() {
        return numOfReferees;
    }

    public int getRefereeId() {
        return refereeId;
    }

    @Override
    public String toString() {
        return  "Name: " + name +
                "\nID:" + refereeId +
                "\nMatches Refereed: " + matchesRefereed +
                "\nNationality: " + nationality +
                "\nYellow Cards: " + yellowCards +
                "\nRed Cards: " + redCards +
                "\nAge: " + age;
    }

    public static void enterRefereeInfo(ArrayList<Referee> referees){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Referee Name: ");
        String refereeName = scanner.nextLine();
        System.out.println("Enter ID Number: ");
        int refereeId = scanner.nextInt();
        scanner.nextLine();
        for (Referee referee : referees){
            if (referee.refereeId==refereeId){
                System.out.println("There is another referee with this ID!");
                return;
            }
        }
        System.out.println("Enter Date Of Birth: ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        Period period = Period.between(dateOfBirth, LocalDate.now());
        if (period.getYears()<25){
            System.out.println("The is Below 25 years ! ");
            return;
        }
        System.out.println("Enter The Nationality: ");
        String refereeNationality = scanner.nextLine();

        Referee referee = new  Referee(refereeName , dateOfBirth , refereeNationality,refereeId );
        Logic.addReferee(referee);

    }

    public void updateReferee (){
        System.out.println("what do you want ? ");
        System.out.println("1.Name\n2.Date Of Birth\n3.Nationality\n4.Matches Referee\n5.Yellow Cards\n6.Red Cards");
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException ime){
            System.out.println("Invalid enter data, please enter a number from 1-6");
        }
        if (choice < 7 && choice > 0){
            switch (choice) {
                case 1 : {
                    System.out.println(" What is the new name : ");
                    String name = scanner.nextLine();
                    this.name = name ;
                    System.out.println(" Successful ! ");
                    break;
                }
                case 2 : {
                    System.out.println(" What is the new date of birth : ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());
                    Period period = Period.between(date, LocalDate.now());
                    if (period.getYears()<25){
                        System.out.println("The is Below 25 years ! ");
                        return;}
                    this.dateOfBirth = date ;
                    System.out.println("successful ! ");
                    break;
                }
                case 3 : {
                    System.out.println("What is the new nationality : ");
                    String nationality = scanner.nextLine();
                    this.nationality = nationality;
                    System.out.println("successful !");
                    break;
                }
                case 4 : {
                    System.out.println(" What is the new amount matches refereed :");
                    int matchesRefereed = 0;
                    try {
                        matchesRefereed = scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException ime){
                        System.out.println("Invalid enetr data, please enter a number!");
                    }
                    this.matchesRefereed = matchesRefereed;
                    System.out.println("successful ! ");
                    break;
                }
                case 5 : {
                    System.out.println("What is the new amount yellow cards : ");
                    int yelloweCard = 0 ;
                    try {
                        yelloweCard = scanner.nextInt();
                        scanner.nextLine();
                    }
                    catch (InputMismatchException ime){
                        System.out.println("Invalid enetr data, please enter a number!");
                    }
                    this.yellowCards = yellowCards;
                    System.out.println("successful !");
                    break;
                }
                case 6 : {
                    System.out.println("What is the new amount red cards : ");
                    int redCards = 0 ;
                    try {
                        redCards = scanner.nextInt();
                        scanner.nextLine();
                    }
                    catch(InputMismatchException ime) {
                        System.out.println("Invalid enetr data, please enter a number!");
                    }
                    this.redCards = redCards;
                    System.out.println("successful !");
                    break;
                }
            }
        }
    }

}
