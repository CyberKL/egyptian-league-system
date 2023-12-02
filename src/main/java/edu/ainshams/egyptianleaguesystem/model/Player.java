package edu.ainshams.egyptianleaguesystem.model;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Player extends FootballCharacter{

    protected  int playerId;
    protected int number;
    protected Team team;
    protected int height;
    protected int weight;
    protected String preferredFoot;
    protected String position;
    private static int numOfPlayers = 0;

    protected Player(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position){
        super(name, dateOfBirth, nationality);
        this.playerId = playerId;
        this.number = number;
        this.team = team;
        this.height = height;
        this.weight = weight;
        this.preferredFoot = preferredFoot;
        this.position = position;
        numOfPlayers ++;
    }

    @Override
    public String toString() {
        return "Player Id: " + playerId +
                "/nNumber: " + number +
                "/nTeam: " + team +
                "/nHeight: " + height +
                "/nWeight: " + weight +
                "/nPreferredFoot: " + preferredFoot +
                "/nPosition: " + position +
                "/nName: " + name +
                "/nNationality: " + nationality +
                "/nYellowCards: " + yellowCards +
                "/nRedCards: " + redCards +
                "/nAge: " + age;
    }

    public static int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Team getTeam() {
        return team;
    }
    public String getName() {
        return this.name;
    }
    public String searchPlayer(ArrayList<Player> playersList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player name:");
        String playerName = scanner.nextLine();

        System.out.println("Do you want to search by team name? (yes/no)");
        String searchByTeam = scanner.nextLine();

        if (searchByTeam.equalsIgnoreCase("no")) {

            for (Player player : playersList) {
                if (player.getName().equalsIgnoreCase(playerName)) {
                    System.out.println( "Player Name: " + player.getName() + ", Player ID: " + player.getPlayerId());
                }
            }
            return "Player not found.";
        } else if (searchByTeam.equalsIgnoreCase("yes")) {
            System.out.println("Enter team name:");
            String inputTeamName = scanner.nextLine();

            for (Player player : playersList) {
                if (player.getName().equalsIgnoreCase(playerName) && player.getTeam().getTeamName().equalsIgnoreCase(inputTeamName)) {
                    System.out.println( "Player Name: " + player.getName() + ", Player ID: " + player.getPlayerId());
                }
            }
            return "Player not found in the specified team";
        } else {
            return "Invalid";
        }
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected void setWeight(int weight) {
        this.weight = weight;
    }
    protected void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }
    protected void setPosition(String position) {
        this.position = position;
    }
    private void setPlayerId(int newPlayerId) {
        this.playerId = newPlayerId;
    }

    public void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> Teams, int number, Team team, int height, int weight, String preferredFoot
            , String position, String nationality, LocalDate dateOfBirth, int playerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Number/n2. Team/n 3. Height/n4. Weight/n5. PreferredFoot/n6. Position/n" +
                "7. Nationality/n8. Data of birth/n9. Player ID/n10. Add yellow cards/n11. Add Red cards  ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= 11) {
            switch (choice) {
                case 1:
                    System.out.println("Enter new number:");
                    try {
                        int newNumber = Integer.parseInt(scanner.nextLine());
                        if (newNumber > 0 && newNumber <= 99) {
                            for (Player player : playersList) {
                                player.setNumber(newNumber);
                            }
                        } else {
                            System.out.println("Number should be between 1 and 99");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Mismatched input type. Please enter a valid number.");
                    }
                    break;
                case 2:
                    // remove from old team
                    System.out.println("Enter new Team name:");
                    String newTeamName = scanner.nextLine();

                    boolean teamExists = false;
                    for (Team existingTeam : Teams) {
                        if (existingTeam.getName().equalsIgnoreCase(newTeamName)) {
                            teamExists = true;
                            break;
                        }
                    }

                    if (teamExists) {
                        for (Player player : playersList) {
                            if (player.getTeam() != null) {
                                player.getTeam().setTeamName(newTeamName);
                            }
                        }
                    } else {
                        System.out.println("Team name does not exist in the system.");
                    }
                    break;

                case 3:
                    try {
                        System.out.println("Enter new Height:");
                        int newHeight = scanner.nextInt();
                        scanner.nextLine();
                        for (Player player : playersList) {
                            player.setHeight(newHeight);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid height, Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                case 4:
                    try {
                        System.out.println("Enter new Weight:");
                        int newWeight = scanner.nextInt();
                        scanner.nextLine();
                        for (Player player : playersList) {
                            player.setWeight(newWeight);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid weight, Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                case 5:
                    System.out.println("Enter new PreferredFoot:");
                    System.out.println("Enter new PreferredFoot (left/right):");
                    String newPreferredFoot = scanner.nextLine();

                    if (newPreferredFoot.equals("left") || newPreferredFoot.equals("right")) {
                        for (Player player : playersList) {
                            player.setPreferredFoot(newPreferredFoot);
                        }
                    } else {
                        System.out.println("Invalid preferred foot  , Please enter 'left' or 'right'.");
                    }
                    break;

                case 6:
                    System.out.println("Enter new Position (Forward, Midfielder, Defender, Goalkeeper):");
                    String newPosition = scanner.nextLine();
                    String[] validPositions = {"Forward", "Midfielder", "Defender", "Goalkeeper"};
                    boolean isValidPosition = false;
                    for (String validPos : validPositions) {
                        if (validPos.equalsIgnoreCase(newPosition)) {
                            isValidPosition = true;
                            break;
                        }
                    }
                    if (isValidPosition) {
                        for (Player player : playersList) {
                            player.setPosition(newPosition);
                        }
                    } else {
                        System.out.println("Invalid position, Please enter a valid position.");
                    }
                    break;

                case 7:
                    System.out.println("Enter new Nationality:");
                    String newNationality = scanner.nextLine();
                    for (Player player : playersList) {
                        player.setNationality(newNationality);
                    }
                    break;
                case 8:
                    System.out.println("Enter new Date of Birth :");
                    String dob = scanner.nextLine();
                    LocalDate newDateOfBirth = LocalDate.parse(dob);
                    LocalDate currentDate = LocalDate.now();
                    Period period = Period.between(dateOfBirth, currentDate);
                    int age = period.getYears();

                    if (age >= 16) {
                        for (Player player : playersList) {
                            player.setDateOfBirth(newDateOfBirth);
                            player.calculateAge();
                        }
                    } else {
                        System.out.println("The player must be at least 16 years old.");
                    }
                    break;
                case 9:
                    System.out.println("Enter new Player ID:");
                    int newPlayerId = scanner.nextInt();
                    scanner.nextLine();
                    for (Player player : playersList) {
                        player.setPlayerId(newPlayerId);
                    }
                    break;
                case 10:
                    try {
                        System.out.println("Enter number of Yellow Cards you want to add:");
                        int numYellowCards = scanner.nextInt();
                        scanner.nextLine();
                        this.yellowCards += numYellowCards;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for yellow cards. Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                case 11:
                    try {
                        System.out.println("Enter number of Red Cards you want to add:");
                        int numRedCards = scanner.nextInt();
                        scanner.nextLine();
                        this.redCards += numRedCards;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input for red cards. Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } else {
            System.out.println("Please enter a number between 1 and 11");
        }

    }

    protected void enterPlayerInfo(ArrayList<Team> teams , String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position, int yellowCards, int redCards) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter player name:");
        String playerName = scanner.nextLine();

        boolean isValidAge = false;
        LocalDate playerDateOfBirth = null;

        while (!isValidAge) {
            System.out.println("Enter Date of Birth :");
            String dob = scanner.nextLine();

            playerDateOfBirth = LocalDate.parse(dob);
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(playerDateOfBirth, currentDate);
            int age = period.getYears();

            if (age >= 16) {
                isValidAge = true;
                System.out.println("Age verified");
            } else {
                System.out.println("The player must be at least 16 years old");
            }
        }

        System.out.println("Enter nationality:");
        String playerNationality = scanner.nextLine();

        System.out.println("Enter player ID:");
        int playerID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter player number:");
        int playerNumber = scanner.nextInt();
        scanner.nextLine();


        System.out.println("Enter team name:");
        String teamName = scanner.nextLine();
        Team playerTeam = null;
        for (Team t :teams) {
            if (t.getName().equalsIgnoreCase(teamName)) {
                playerTeam = t;
                break;

            }
        }

        while (true) {
            try {
                System.out.println("Enter player height:");
                int playerHeight = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for player height.");
                scanner.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("Enter player weight:");
                int playerWeight = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for player weight.");
                scanner.nextLine();
            }
        }


        String playerPreferredFoot;
        while (true) {
            System.out.println("Enter preferred foot (right/left):");
            playerPreferredFoot = scanner.nextLine();

            if (playerPreferredFoot.equals("right") || playerPreferredFoot.equals("left")) {
                break;
            } else {
                System.out.println("Please enter either 'right' or 'left'.");
            }
        }

        String playerPosition;

        while (true) {
            System.out.println("Enter player position (forward/midfielder/defender/goalkeeper):");
            playerPosition = scanner.nextLine();

            if (playerPosition.equalsIgnoreCase("forward") || playerPosition.equalsIgnoreCase("midfielder")
                    || playerPosition.equalsIgnoreCase("defender") || playerPosition.equalsIgnoreCase("goalkeeper")) {
                break;
            } else {
                System.out.println("Please enter a valid player position.");
            }
        }


    }

    public int getPlayerId() {
        return playerId;

    }
}