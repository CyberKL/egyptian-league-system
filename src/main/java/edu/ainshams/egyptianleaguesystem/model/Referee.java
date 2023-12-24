package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class Referee extends FootballCharacter{

    private int matchesRefereed;
    private final int refereeId;
    private static int numOfReferees = Logic.getReferees().size();

    public Referee(@JsonProperty("name") String name,
                   @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                   @JsonProperty("nationality") String nationality,
                   @JsonProperty("refereeId") int refereeId){
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
                "\nAge: " + age +
                "\nNationality: " + nationality +
                "\nMatches Refereed: " + matchesRefereed +
                "\nYellow Cards: " + yellowCards +
                "\nRed Cards: " + redCards;
    }

    protected static void enterRefereeInfo(ArrayList<Referee> referees){
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
        LocalDate dateOfBirth = null;
        try {
            System.out.println("Enter Date Of Birth: ");
            dateOfBirth = LocalDate.parse(scanner.nextLine());
            Period period = Period.between(dateOfBirth, LocalDate.now());
            if (period.getYears() < 25) {
                System.out.println("The is Below 25 years ! ");
                return;
            }
        }catch (InputMismatchException ime){
            System.out.println("Please enter a valid date");
        }
        System.out.println("Enter The Nationality: ");
        String refereeNationality = scanner.nextLine();

        Referee referee = new  Referee(refereeName , dateOfBirth , refereeNationality,refereeId );
        Logic.addReferee(referee);

    }

    protected void updateReferee (){
        System.out.println("what do you want ? ");
        System.out.println("1.Name\n2.Date Of Birth\n3.Nationality\n4.Matches Refereed\n5.Yellow Cards\n6.Red Cards");
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
                    this.name = scanner.nextLine();
                    System.out.println(" Successful ! ");
                    break;
                }
                case 2 : {
                    LocalDate date = null;
                    try {
                        System.out.println(" What is the new date of birth : ");
                        date = LocalDate.parse(scanner.nextLine());
                        Period period = Period.between(date, LocalDate.now());
                        if (period.getYears() < 25) {
                            System.out.println("The is Below 25 years ! ");
                            return;
                        }
                    }catch (InputMismatchException ime){
                        System.out.println("Please enter a valid date");
                    }
                    this.dateOfBirth = date ;
                    System.out.println("successful ! ");
                    break;
                }
                case 3 : {
                    System.out.println("What is the new nationality : ");
                    this.nationality = scanner.nextLine();
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
                        System.out.println("Invalid enter data, please enter a number!");
                    }
                    this.matchesRefereed = matchesRefereed;
                    System.out.println("successful ! ");
                    break;
                }
                case 5 : {
                    System.out.println("What is the new amount yellow cards : ");
                    int yellowCard = 0 ;
                    try {
                        yellowCard = scanner.nextInt();
                        scanner.nextLine();
                    }
                    catch (InputMismatchException ime){
                        System.out.println("Invalid enter data, please enter a number!");
                    }
                    this.yellowCards = yellowCard;
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
