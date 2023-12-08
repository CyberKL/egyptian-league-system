package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends FootballCharacter{

    private int managerId;
    private Team team;
    private int trophies;
    private boolean wasPlayer;
    private static int numOfManagers = 0;

    public Manager(String name, int managerId, LocalDate dateOfBirth, String nationality, Team team, int trophies, boolean wasPlayer) {
        super(name, dateOfBirth, nationality);
        this.team = team;
        this.managerId = managerId;
        this.trophies = trophies;
        this.wasPlayer = wasPlayer;
        numOfManagers++;
    }

    @Override
    public String toString() {
        return
                "/n Name: " + name +
                "/n Manager Id:"+managerId+
                "/n Team: " + team +
                "/n Trophies:" + trophies +
                "/n Was Player:" + wasPlayer +
                "/n Nationality: " + nationality +
                "/n YellowCards: " + yellowCards +
                "/n RedCards: " + redCards +
                "/n Date of birth:"+dateOfBirth+
                "/n Age: " + age;
    }


    public static void enterManagerInfo(ArrayList<Team>teams,ArrayList<Manager>managerList) throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        Team managerTeam=null;
        System.out.println("Enter manager name:");
        String managerName = scanner.nextLine();

        System.out.print("Enter manager ID:");
        int managerId = scanner.nextInt();
        scanner.nextLine();
        isManagerIdDuplicate(managerId, managerList);

        System.out.println("Enter manager team name:");
        String teamName = scanner.nextLine();
        for (Team t :teams) {
            if (t.getName().equalsIgnoreCase(teamName)) {
                managerTeam = t;
                break;
            }
        }

        boolean isValidAge = false;
        LocalDate managerDateOfBirth = null;
        while (!isValidAge) {
            System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
            managerDateOfBirth = LocalDate.parse(scanner.nextLine());
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(managerDateOfBirth, currentDate);
            int age = period.getYears();

            if (age >= 30) {
                isValidAge = true;
                break;
            } else {
                System.out.println("The manager must be at least 30 years old.");
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
        Manager manager = new Manager(managerName,managerId,managerDateOfBirth,managerNationality,managerTeam,trophies,wasPlayer);
        managerList.add(manager);
    }

    public int getManagerId() {
        return managerId;
    }
    private static void isManagerIdDuplicate(int managerId, ArrayList<Manager> managerList) throws DuplicateException {
        for (Manager manager : managerList) {
            if (manager.getManagerId() == managerId) {
                throw new DuplicateException("This manager ID is already taken");
            }
        }
    }


    protected void updatePlayerInfo() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Name\n2. Team\n3. Trophies\n4. Was Player\n5. Nationality\n6. Date Of Birth\n7. Age\n8. Yellow Cards\n9. Red Cards");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= 9) {
            switch (choice) {
                case 1: {
                    System.out.print("Enter new player name: ");
                    String newPlayerName = scanner.nextLine();
                    break;
                }
                case 2: {
                    System.out.print("Enter new team: ");
                    String newTeam = scanner.nextLine();
                    break;
                }
                case 3: {
                    System.out.print("Enter new number of trophies: ");
                    int newTrophies = scanner.nextInt();
                    scanner.nextLine();
                    break;
                }
                case 4: {
                    System.out.print("Was the manager player before (true/false): ");
                    boolean wasPlayer = scanner.nextBoolean();
                    scanner.nextLine();
                    break;
                }
                case 5: {
                    System.out.print("Enter new nationality: ");
                    String newNationality = scanner.nextLine();
                    break;
                }
                case 6: {
                    System.out.print("Enter new Date Of Birth (yyyy-MM-dd): ");
                    LocalDate newDOB = LocalDate.parse(scanner.nextLine());
                    break;
                }
                case 7: {
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();
                    scanner.nextLine();
                    break;
                }
                case 8: {
                    System.out.print("Enter new number of yellow cards: ");
                    int yellowCards = scanner.nextInt();
                    scanner.nextLine();
                    this.yellowCards += yellowCards;
                    break;
                }
                case 9: {
                    System.out.print("Enter new number of red cards: ");
                    int redCards = scanner.nextInt();
                    scanner.nextLine();
                    this.redCards += redCards;
                    break;
                }
                default: {
                    System.out.println("Please enter a valid choice");
                    break;
                }
            }
        } else {
            System.out.println("Please enter a valid choice");
        }

    }
}