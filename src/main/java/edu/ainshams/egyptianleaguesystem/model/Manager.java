package edu.ainshams.egyptianleaguesystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class Manager extends FootballCharacter{

    private final int managerId;
    private Team team;
    private int trophies;
    private boolean wasPlayer;
    private static int numOfManagers = 0;


    public Manager( @JsonProperty("name") String name,
                    @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                    @JsonProperty("nationality") String nationality,
                    @JsonProperty("managerId") int managerId,
                    @JsonProperty("trophies") int trophies,
                    @JsonProperty("wasPlayer") boolean wasPlayer,
                    @JsonProperty("team") Team team){
        super(name,dateOfBirth, nationality);
        this.team = team;
        this.managerId = managerId;
        this.trophies = trophies;
        this.wasPlayer = wasPlayer;
        numOfManagers++;
    }

    public int getManagerId() {
        return managerId;
    }

    public Team getTeam() {
        return team;
    }

    public int getTrophies() {
        return trophies;
    }

    public boolean isWasPlayer() {
        return wasPlayer;
    }

    public static int getNumOfManagers() {
        return numOfManagers;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public void setWasPlayer(boolean wasPlayer) {
        this.wasPlayer = wasPlayer;
    }

    @Override
    public String toString() {
        String teamName = (team != null) ? team.getName() : "N/A";
        return
                "\nName: " + name +
                "\nManager Id:"+managerId+
                "\nAge: " + age +
                "\nNationality: " + nationality +
                "\nTeam: " + teamName +
                "\nTrophies:" + trophies +
                "\nWas Player:" + wasPlayer +
                "\nYellowCards: " + yellowCards +
                "\nRedCards: " + redCards;
    }


    protected static void enterManagerInfo(ArrayList<Team>teams,ArrayList<Manager>managerList) throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        Team managerTeam = null;
        System.out.println("Enter manager name:");
        String managerName = scanner.nextLine();

        System.out.print("Enter manager ID:");
        int managerId = scanner.nextInt();
        scanner.nextLine();
        isManagerIdDuplicate(managerId, managerList);

        boolean isValidAge = false;
        LocalDate managerDateOfBirth = null;
        while (!isValidAge) {
            try {
                System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
                managerDateOfBirth = LocalDate.parse(scanner.nextLine());

                LocalDate currentDate = LocalDate.now();
                Period period = Period.between(managerDateOfBirth, currentDate);
                int age = period.getYears();
                if (age >= 30) {
                    isValidAge = true;
                } else {
                    System.out.println("The manager must be at least 30 years old.");
                }
            }catch (DateTimeParseException dtpe){
                System.out.println("Please enter a valid date");
            }
        }

        System.out.println("Enter manager nationality:");
        String managerNationality = scanner.nextLine();

        System.out.println("Enter number of trophies won:");
        int trophies = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Was the manager a player before (true/false):");
        String input = scanner.nextLine();
        boolean wasPlayer;
        if (input.equalsIgnoreCase("true")) {
             wasPlayer = true;
        } else if (input.equalsIgnoreCase("false")) {
             wasPlayer = false;
        } else {
            System.out.println("Please enter 'true' or 'false'.");
            return;
        }

        boolean teamFound = false;
        System.out.println("Enter manager team");
        String teamName = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(teamName)){
                teamFound = true;
                managerTeam = team;
                break;
            }
        }
        if (!teamFound){
            System.out.println("Team not found!");
            return;
        }

        Manager manager = new Manager(managerName, managerDateOfBirth, managerNationality, managerId, trophies, wasPlayer, managerTeam);
        managerList.add(manager);
        managerTeam.setManager(manager);
        System.out.println("Manger created successful");
    }

    private static void isManagerIdDuplicate(int managerId, ArrayList<Manager> managerList) throws DuplicateException {
        for (Manager manager : managerList) {
            if (manager.getManagerId() == managerId) {
                throw new DuplicateException("This manager ID is already taken");
            }
        }
    }

    protected void updateManagerInfo(ArrayList<Team> teams) throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Team\n3. Trophies\n4. Was Player\n5. Nationality\n6. Date Of Birth\n7. Yellow Cards\n8. Red Cards");

        int choice = 0;
        try {
            choice  = scanner.nextInt();
            scanner.nextLine();
        }catch (InputMismatchException ime){
            System.out.println("Please enter a number");
        }

        switch (choice) {
            case 1: {
                System.out.print("Enter new player name: ");
                this.name = scanner.nextLine();
                break;
            }
            case 2: {
                System.out.print("Enter new team name: ");
                String newTeamName = scanner.nextLine();
                boolean teamExists = false;
                for (Team team : teams) {
                    if (team.getName().equalsIgnoreCase(newTeamName)) {
                        teamExists = true;
                        if (team.getManager()!= null){
                            team.getManager().setTeam(null);
                        }
                        team.setManager(this);
                        this.setTeam(team);
                        break;
                    }
                }
                if (!teamExists) {
                    System.out.println("Team not found.");
                }
                break;
            }
            case 3: {
                System.out.print("Enter new number of trophies: ");
                int newTrophies = scanner.nextInt();
                scanner.nextLine();
                this.trophies=newTrophies;
                break;
            }
            case 4: {
                System.out.print("Was the manager player before (true/false): ");
                boolean wasPlayerBefore = false;
                try {
                    wasPlayerBefore = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter 'true' or 'false'.");
                    scanner.nextLine();
                }
                this.wasPlayer=wasPlayerBefore;
                break;
            }

            case 5: {
                System.out.print("Enter new nationality: ");
                this.nationality= scanner.nextLine();
                break;
            }
            case 6: {
                LocalDate newDOB = null;
                try {
                    boolean isValidAge = false;
                    while (!isValidAge) {
                        System.out.print("Enter new Date Of Birth (yyyy-MM-dd): ");
                        newDOB = LocalDate.parse(scanner.nextLine());
                        LocalDate currentDate = LocalDate.now();
                        Period period = Period.between(newDOB, currentDate);
                        int age = period.getYears();
                        if (age >= 30) {
                            isValidAge = true;
                        } else {
                            System.out.println("The manager must be at least 30 years old.");
                        }
                    }
                }catch (DateTimeParseException dtpe){
                    System.out.println("Please enter a valid date");
                }
                this.dateOfBirth=newDOB;
                break;
            }

            case 7: {
                System.out.print("Enter new number of yellow cards: ");
                int yellowCards = 0;
                try{
                    yellowCards = scanner.nextInt();
                    scanner.nextLine();
                }catch (InputMismatchException ime){
                    System.out.println("Invalid input please enter numbers only");
                }
                this.yellowCards = yellowCards;
                break;
            }
            case 8: {
                System.out.print("Enter new number of red cards: ");
                int redCards = 0;
                try{
                    redCards = scanner.nextInt();
                    scanner.nextLine();
                }catch (InputMismatchException ime){
                    System.out.println("Invalid input please enter numbers only");
                }
                this.redCards = redCards;
                break;
            }
            default: {
                System.out.println("Please enter a valid choice");
                break;
            }
        }
    }
}