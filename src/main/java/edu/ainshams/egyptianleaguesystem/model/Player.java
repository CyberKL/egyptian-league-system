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
    public int getPlayerId() {
        return playerId;
    }

    public String searchPlayer(ArrayList<Player> playersList, ArrayList<Team> teamsList) {
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

            for (Team team : teamsList) {
                if (team.getName().equalsIgnoreCase(inputTeamName)) {
                    for (Player player : team.getPlayers()) {
                        if (player.getName().equalsIgnoreCase(playerName)) {
                            System.out.println("Player Name: " + player.getName() + ", Player ID: " + player.getPlayerId());
                        }
                    }
                }
            }
            return "Player not found in the specified team";
        } else {
            return "Invalid";
        }
    }

    protected void updatePlayerInfo(ArrayList<Player> playersList , ArrayList<Team> teams) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to update?");
        System.out.println("1. Number/n2. Team/n 3. Height/n4. Weight/n5. Preferred Foot/n6. Position/n" +
                "7. Nationality/n8. Data of birth/n9. Player ID/n10. Yellow cards/n11. Red cards  ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= 11) {
            switch (choice) {
                case 1: {
                    System.out.print("Enter new number: ");
                    try {
                        int newNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (newNumber > 0 && newNumber <= 99) {
                            this.number = newNumber;
                        } else {
                            System.out.println("Number should be between 1 and 99");
                            return;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Mismatched input type. Please enter a number.");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter new Team name: ");
                    String newTeamName = scanner.nextLine();

                    boolean teamExists = false;
                    for (Team existingTeam : teams) {
                        if (existingTeam.getName().equalsIgnoreCase(newTeamName)) {
                            existingTeam.deletePlayer(this);
                            this.team = existingTeam;
                            teamExists = true;
                            break;
                        }
                    }
                    if (!teamExists) {
                        System.out.println("Team does not exist in the system.");
                    }
                    break;
                }

                case 3: {
                    try {
                        System.out.print("Enter new Height: ");
                        int newHeight = scanner.nextInt();
                        scanner.nextLine();
                        this.height = newHeight;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid height, Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                }
                case 4: {
                    try {
                        System.out.print("Enter new Weight: ");
                        int newWeight = scanner.nextInt();
                        scanner.nextLine();
                        this.weight = newWeight;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid weight, Please enter a numerical value.");
                        scanner.nextLine();
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter new Preferred Foot (left/right): ");
                    String newPreferredFoot = scanner.nextLine();

                    if (newPreferredFoot.equalsIgnoreCase("left") || newPreferredFoot.equalsIgnoreCase("right")) {
                        this.preferredFoot = newPreferredFoot;
                    } else {
                        System.out.println("Invalid preferred foot  , Please enter 'left' or 'right'.");
                        return;
                    }
                    break;
                }

                case 6: {
                    System.out.print("Enter new Position (Forward, Midfielder, Defender, Goalkeeper): ");
                    String newPosition = scanner.nextLine();
                    String[] validPositions = {"Forward", "Midfielder", "Defender", "Goalkeeper"};
                    boolean isValidPosition = false;
                    for (String validPos : validPositions) {
                        if (validPos.equalsIgnoreCase(newPosition)) {
                            this.position = newPosition;
                            isValidPosition = true;
                            break;
                        }
                    }
                    if (!isValidPosition) {
                        System.out.println("Invalid position, Please enter a valid position.");
                        return;
                    }
                    break;
                }

                case 7: {
                    System.out.print("Enter new Nationality: ");
                    String newNationality = scanner.nextLine();
                    this.nationality = newNationality;
                    break;
                }
                case 8: {
                    System.out.print("Enter new Date of Birth (yyyy-mm-dd): ");
                    LocalDate dob = LocalDate.parse(scanner.nextLine());
                    LocalDate currentDate = LocalDate.now();
                    Period period = Period.between(dob, currentDate);
                    int age = period.getYears();

                    if (age >= 16) {
                        this.dateOfBirth = dob;
                        this.age = age;
                    } else {
                        System.out.println("The player must be at least 16 years old.");
                        return;
                    }
                    break;
                }
                case 9: {
                    System.out.println("Enter new Player ID:");
                    int newPlayerId = scanner.nextInt();
                    scanner.nextLine();
                    isPlayerIdDuplicate(newPlayerId, playersList);
                    this.playerId = newPlayerId;
                    break;
                }
                case 10: {
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
                }
                case 11: {
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
                }
            }
        } else {
            System.out.println("Please enter a number between 1 and 11");
        }

    }
    private static void isPlayerIdDuplicate(int playerId, ArrayList<Player> playerList) throws DuplicateException{
        for (Player element : playerList){
            if(element.getPlayerId()==playerId){
                throw new DuplicateException("This player id is already taken");
            }
        }
    }


    protected void enterPlayerInfo(ArrayList<Team> teams, ArrayList<Player> players) throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        Team playerTeam = null;
        boolean isValidAge = false;
        LocalDate playerDateOfBirth = null;

        System.out.println("Enter player name:");
        String playerName = scanner.nextLine();

        while (!isValidAge) {
            System.out.print("Enter Date of Birth: ");
            playerDateOfBirth = LocalDate.parse(scanner.nextLine());
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(playerDateOfBirth, currentDate);
            int age = period.getYears();

            if (age >= 16) {
                isValidAge = true;
            } else {
                System.out.println("The player must be at least 16 years old");
                return;
            }
        }

        System.out.print("Enter nationality: ");
        String playerNationality = scanner.nextLine();

        System.out.print("Enter player ID:");
        int playerID = scanner.nextInt();
        scanner.nextLine();
        isPlayerIdDuplicate(playerID, players);

        try {
            System.out.print("Enter player number: ");
            int playerNumber = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Please enter a numerical value");
        }


        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        for (Team t :teams) {
            if (t.getName().equalsIgnoreCase(teamName)) {
                playerTeam = t;
                break;
            }
        }

        try {
            System.out.println("Enter player height:");
            int playerHeight = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid integer for player height.");
            scanner.nextLine();
        }

        try {
            System.out.println("Enter player weight:");
            int playerWeight = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid integer for player weight.");
            scanner.nextLine();
        }

            System.out.println("Enter preferred foot (right/left):");
            String playerPreferredFoot = scanner.nextLine();
            if (!playerPreferredFoot.equalsIgnoreCase("right") && !playerPreferredFoot.equalsIgnoreCase("left")) {
                System.out.println("Please enter either 'right' or 'left'.");
                return;
            }

        System.out.println("Enter player position (forward/midfielder/defender/goalkeeper):");
        String playerPosition = scanner.nextLine();
        String[] validPositions = {"Forward", "Midfielder", "Defender", "Goalkeeper"};
        boolean isValidPosition = false;
        for (String validPos : validPositions) {
            if (validPos.equalsIgnoreCase(playerPosition)) {
                isValidPosition = true;
                break;
            }
        }
        if (!isValidPosition){
            System.out.println("Please enter a correct position");
        }


    }


}