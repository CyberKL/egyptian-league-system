package edu.ainshams.egyptianleaguesystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {

    private static ArrayList<Team> teams = new ArrayList<Team>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static ArrayList<Match> matches = new ArrayList<Match>();
    private static ArrayList<Manager> managers = new ArrayList<Manager>();
    private static ArrayList<Referee> referees = new ArrayList<Referee>();
    private static ArrayList<Stadium> stadiums = new ArrayList<Stadium>();

    public static ArrayList<Team> getTeams() {
        return teams;
    }
    public static void addTeam(Team team){
        teams.add(team);
    }
    public static void removeTeam(Team team){
        teams.remove(team);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }
    public static void addPlayer(Player player){
        players.add(player);
    }
    public static void removePlayer(Player player){
        players.remove(player);
    }

    public static ArrayList<Stadium> getStadiums() {
        return stadiums;
    }
    public static void addStadium(Stadium stadium){
        stadiums.add(stadium);
    }
    public static void removeStadium(Stadium stadium){
        stadiums.remove(stadium);
    }

    public static ArrayList<Match> getMatches() {
        return matches;
    }
    public static void addMatch(Match match){
        matches.add(match);
    }
    public static void removeMatch(Match match){
        matches.remove(match);
    }

    public static ArrayList<Referee> getReferees() {
        return referees;
    }
    public static void addReferee(Referee referee){
        referees.add(referee);
    }
    public static void removeReferee(Referee referee){
        referees.remove(referee);
    }

    public static ArrayList<Manager> getManagers() {
        return managers;
    }
    public static void addManager(Manager manager){
        managers.add(manager);
    }
    public static void removeManager(Manager manager){
        managers.remove(manager);
    }

    //Start of match related methods
    public static void enterMatchInfo(){
        Match.enterMatchInfo(teams, referees, stadiums, matches);
    }

    public static String displayMatchInfo(){
        System.out.print("Enter the id of the match you want to display: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Match element : matches){
            if (element.getMatchId()==id){
                return element.toString();
            }
        }
        return "Match not found, please provide a valid match Id";
    }

    public static void updateMatch(){
        System.out.print("Enter the id of the match you want to update: ");
        Scanner idScanner = new Scanner(System.in);
        int id = idScanner.nextInt();
        idScanner.nextLine();
        for (Match element : matches){
            if (element.getMatchId()==id){
                element.updateMatch(teams, referees, stadiums);
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match Id");
    }

    public static void deleteMatch(){
        System.out.print("Enter the id of the match you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Match element:matches) {
            if (element.getMatchId() == id) {
                matches.remove(element);
                System.out.println("Match deleted successfully!");
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match id");
    }
    //End of match related methods

    //Start of team related methods
    public static void enterTeamInfo(){
        Team.enterTeamInfo(teams,managers);
    }

    public static void updateTeam() throws DuplicateException{
        System.out.print("Enter the name of the team you want to update: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team element : teams){
            if (element.getName().equalsIgnoreCase(name)){
                element.updateTeam(teams, players);
                return;
            }
        }
        System.out.println("Match not found, please provide a valid match Id");
    }

    public static String displayTeamInfo(){
        System.out.println("Enter team name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team element : teams){
            if (element.getName().equalsIgnoreCase(name)){
                element.calcTotalScore();
                return element.toString();
            }
        }
        return "Team not found, please provide a valid team name";
    }

    public static void displayTeamMatches(){
        System.out.println("Enter the name of the team you want to see its matches: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(name)){
                team.displayTeamMatches();
            }
        }
    }

    public static void displayTeamPlayers(){
        System.out.println("Enter the name of the team you want to see its players: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(name)){
                team.displayTeamPlayers();
            }
        }
    }


    public static void addPlayerToTeam(){
        String teamName = null;
        String playerName = null;
        int id = 0;
        boolean teamFound = false;
        try {
            System.out.print("Enter the team name you would like to add player to: ");
            Scanner scanner = new Scanner(System.in);
            teamName = scanner.nextLine();
            System.out.println("Enter the player name and id you would like to add: ");
            System.out.print("Name: ");
            playerName = scanner.nextLine();
            System.out.print("Id: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input, please try again!");
        }
        catch (Exception e){
            System.out.println("Try again!");
        }
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(teamName)){
                teamFound = true;
                for (Player player : players){
                    if (player.getPlayerId()==id && player.getName().equalsIgnoreCase(playerName)){
                        team.addPlayer(player);
                        System.out.println("Player added successfully to "+team.getName());
                        return;
                    }
                }
                System.out.println("Player not found");
            }
        }
        if (!teamFound){
            System.out.println("Team not found");
        }
    }

    public static void deletePlayerFromTeam(){
        String teamName = null;
        String playerName = null;
        int id = 0;
        boolean teamFound = false;
        try {
            System.out.print("Enter the team name you would like to delete player from: ");
            Scanner scanner = new Scanner(System.in);
            teamName = scanner.nextLine();
            System.out.println("Enter the player name and id you would like to delete: ");
            System.out.print("Name: ");
            playerName = scanner.nextLine();
            System.out.print("Id: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("Invalid input, please try again!");
        }
        catch (Exception e){
            System.out.println("Try again!");
        }
        for (Team team : teams){
            if (team.getName().equalsIgnoreCase(teamName)){
                teamFound = true;
                for (Player player : players){
                    if (player.getPlayerId()==id && player.getName().equalsIgnoreCase(playerName)){
                        team.deletePlayer(player);
                        System.out.println("Player deleted successfully from "+team.getName());
                        return;
                    }
                }
                System.out.println("Player not found");
            }
        }
        if (!teamFound){
            System.out.println("Team not found");
        }
    }

    public static void displayTotalTeams (){
        for (Team team : teams){
            System.out.println(team.getName());
        }
    }

    public static int calcNumOfTeams(){
        return Team.getNumOfTeams();
    }

    public static void deleteTeam(){
        System.out.print("Enter the Id of the team you want to delete: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Team element:teams) {
            if (element.getTeamId() == id) {
                teams.remove(element);
                System.out.println("Team deleted successfully!");
                return;
            }
        }
        System.out.println("Team not found, please provide a valid team id");
    }
    //End of team related methods

    //Start of stadium related methods
    public static void enterStadiumInfo() throws DuplicateException{
        Stadium.enterStadiumInfo(stadiums);
    }

    public static String displayStadiumInfo(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium you want to display: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums){
            if (stadium.getName().equalsIgnoreCase(name)){
                return stadium.toString();
            }
        }
        return "Stadium not found, please enter a valid name";
    }

    public static String displayUpcomingMatches(){
        StringBuilder upcomingMatches = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium to see upcoming matches: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums) {
            if (stadium.getName().equalsIgnoreCase(name)) {
                if (stadium.getUpcomingMatches().isEmpty()){
                    return "No upcoming matches found";
                }
                else {
                    for (Match match : stadium.getUpcomingMatches()) {
                        upcomingMatches.append(match.matchHeader()).append("\n");
                    }
                    return upcomingMatches.toString();
                }
            }
        }
        return "Stadium not found, please enter a valid name";
    }

    public static void deleteStadium(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the Stadium you want to delete: ");
        String name = scanner.nextLine();
        for (Stadium stadium: stadiums) {
            if (stadium.getName().equalsIgnoreCase(name)) {
                stadiums.remove(stadium);
                System.out.println("Stadium deleted successfully!");

            }
        }
        System.out.println("Stadium not found, please enter a valid name");
    }
    //End of stadium related methods

    //Start of referee related methods
    public static void displayTotalReferees(){
        System.out.println("Total number of referees: " + Integer.toString(Referee.getNumOfReferees()));
    }

    public static void deleteReferee(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the ID of the referee you want to delete");
        int refereeId = scanner.nextInt();
        scanner.nextLine();
        for (Referee referee : referees){
            if (referee.getRefereeId()==refereeId){
                referees.remove(referee);
                System.out.println("This referee has been deleted successfully");
            }
        }
        System.out.println("Referee not found");
    }

    public static void enterRefereeInfo() throws DuplicateException {
        Referee.enterRefereeInfo(referees);
    }
    public static void updateRefereeInfo() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the referee you want to update: ");
        int refereeIdToUpdate = scanner.nextInt();
        scanner.nextLine();

        for (Referee referee : referees) {
            if (referee.getRefereeId() == refereeIdToUpdate) {
                referee.updateReferee();
                System.out.println("Referee information updated successfully!");
                return;
            }
        }
        System.out.println("Referee with ID " + refereeIdToUpdate + " not found.");
    }
    //End of Referee related methods


    //Start of Manager related methods
    public static void enterManagerInfo() throws DuplicateException {
        Manager.enterManagerInfo(teams, managers);
    }

    public static void updateManagerInfo() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the manager you want to update: ");
        int managerIdToUpdate = scanner.nextInt();
        scanner.nextLine();

        for (Manager manager : managers) {
            if (manager.getManagerId() == managerIdToUpdate) {
                manager.updateManagerInfo();
                System.out.println("Manager information updated successfully!");
                return;
            }
        }
        System.out.println("Manager with ID " + managerIdToUpdate + " not found.");
    }

    public static void deleteManager() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the manager you want to delete: ");
        int managerIdToDelete = scanner.nextInt();
        scanner.nextLine();

        for (Manager manager : managers) {
            if (manager.getManagerId() == managerIdToDelete) {
                managers.remove(manager);
                System.out.println("Manager deleted successfully!");
                return;
            }
        }
        System.out.println("Manager with ID " + managerIdToDelete + " not found.");
    }
    public static String displayManagerInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the manager you want to display: ");
        int managerIdToDisplay = scanner.nextInt();
        scanner.nextLine();

        for (Manager manager : managers) {
            if (manager.getManagerId() == managerIdToDisplay) {
                return manager.toString();
            }
        }
        return "Manager with ID " + managerIdToDisplay + " not found.";
    }
    //End of Manager related methods

    //Start of Player related methods

        public static void enterPlayerInfo() throws DuplicateException {
                Player.enterPlayerInfo(teams, players);
        }
        public static void searchPlayer() {

        Player.searchPlayer(players,teams);
        }

        public static void updatePlayer() throws DuplicateException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the player you want to update: ");
        int playerIdToUpdate = scanner.nextInt();
        scanner.nextLine();

        for (Player player : players) {
        if (player.getPlayerId() == playerIdToUpdate) {
            int choice=0;
            switch (player.position) {

                case "Forward":{

                    Forward forward=(Forward) player;
                    forward.updatePlayerInfo(players,teams,choice);
                    break;
                }

                case "Midfielder":{
                    Midfielder midfielder=(Midfielder) player;
                    midfielder.updatePlayerInfo(players,teams,choice);
                    break;
                }

                case "Defender":{
                    Defender defender=(Defender) player;
                    defender.updatePlayerInfo(players,teams,choice);
                    break;
                }

                case "Goalkeeper":{
                    Goalkeeper goalkeeper=(Goalkeeper) player;
                    goalkeeper.updatePlayerInfo(players,teams,choice);
                    break;
                }

            }

            }
            System.out.println("Player information updated successfully!");
                return;
            }
        System.out.println("Player with ID " + playerIdToUpdate + " not found.");
    }

        public static void deletePlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the player you want to delete: ");
        int playerIdToDelete = scanner.nextInt();
        scanner.nextLine();

        for (Player player : players) {
            if (player.getPlayerId() == playerIdToDelete) {
                players.remove(player);
                System.out.println("Player deleted successfully!");
                return;
            }
        }
        System.out.println("Player with ID " + playerIdToDelete + " not found.");
    }

    //Start of stats related methods
    public static void displayTopScorePlayers() {
        if (!Logic.getPlayers().isEmpty()) {
            List<Player> filteredPlayers = Logic.getPlayers().stream()
                    .filter(player -> !(player instanceof Goalkeeper))
                    .collect(Collectors.toList());

            if (!filteredPlayers.isEmpty()) {
                filteredPlayers.sort((player1, player2) -> {
                    Optional<Integer> goals1Opt = player1.getGoalsScored();
                    Optional<Integer> goals2Opt = player2.getGoalsScored();

                    if (goals1Opt.isPresent() && goals2Opt.isPresent()) {
                        return Integer.compare(goals2Opt.get(), goals1Opt.get());
                    } else if (goals1Opt.isPresent()) {
                        return -1;
                    } else if (goals2Opt.isPresent()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });

                int numberOfPlayersToShow = Math.min(3, filteredPlayers.size());

                System.out.println("Top Scorers:");
                for (int i = 0; i < numberOfPlayersToShow; i++) {
                    Player player = filteredPlayers.get(i);
                    Optional<Integer> goalsOptional = player.getGoalsScored();
                    String goals = goalsOptional.map(String::valueOf).orElse("N/A");

                    System.out.println((i + 1) + ". " + player.getName() + " - Goals: " + goals);
                }
            } else {
                System.out.println("There are no outfield players to show");
            }
        } else {
            System.out.println("There are no players to show");
        }
    }
    public static void displayTopAssistPlayers() {

        if (!Logic.getPlayers().isEmpty()) {
        List<Player> filteredPlayers = Logic.getPlayers().stream()
                .filter(player -> !(player instanceof Goalkeeper))
                .collect(Collectors.toList());

        if (!filteredPlayers.isEmpty()) {
            filteredPlayers.sort((player1, player2) -> {
                Optional<Integer> assists1Opt = player1.getAssists();
                Optional<Integer> assists2Opt = player2.getAssists();

                if (assists1Opt.isPresent() && assists2Opt.isPresent()) {
                    return Integer.compare(assists2Opt.get(), assists1Opt.get());
                } else if (assists1Opt.isPresent()) {
                    return -1;
                } else if (assists2Opt.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            });

            int numberOfPlayersToShow = Math.min(3, filteredPlayers.size());

            System.out.println("Top Assists:");
            for (int i = 0; i < numberOfPlayersToShow; i++) {
                Player player = filteredPlayers.get(i);
                Optional<Integer> assistsOptional = player.getAssists();
                String assists = assistsOptional.map(String::valueOf).orElse("N/A");

                System.out.println((i + 1) + ". " + player.getName() + " - Assists: " + assists);
            }
        } else {
            System.out.println("There are no outfield players to show");
        }
    } else {
        System.out.println("There are no players to show");
    }
    }
    public static void displayTopCleanSheetsPlayers() {
        List<Player> goalkeepers = Logic.getPlayers().stream()
                .filter(player -> player instanceof Goalkeeper)
                .collect(Collectors.toList());

        if (!goalkeepers.isEmpty()) {
            goalkeepers.sort((gk1, gk2) -> {
                int cleanSheets1 = ((Goalkeeper) gk1).getCleanSheets();
                int cleanSheets2 = ((Goalkeeper) gk2).getCleanSheets();

                return Integer.compare(cleanSheets2, cleanSheets1);
            });

            int numberOfGoalkeepersToShow = Math.min(3, goalkeepers.size());

            System.out.println("Top Goalkeeper Clean Sheets:");
            for (int i = 0; i < numberOfGoalkeepersToShow; i++) {
                Goalkeeper goalkeeper = (Goalkeeper) goalkeepers.get(i);
                int cleanSheets = goalkeeper.getCleanSheets();

                System.out.println((i + 1) + ". " + goalkeeper.getName() + " - Clean Sheets: " + cleanSheets);
            }
        } else {
            System.out.println("There are no goalkeepers to show");
        }
    }
    //End od Stats related methods

    public static void displayTeamsByAverageAge() {
        List<Team> teamsByAge = teams;

        if (!teamsByAge.isEmpty()) {
            teamsByAge.sort((team1, team2) -> Double.compare(team2.getAverageAge(), team1.getAverageAge()));

            System.out.println("Teams sorted by Average Age (Descending):");
            for (Team team : teamsByAge) {
                System.out.println("Team: " + team.getName() + " - Average Age: " + team.getAverageAge());
            }
        } else {
            System.out.println("There are no teams to show");
        }
    }
    //End od Stats related methods


        public static void startCLI() throws DuplicateException {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome to the Egyptian League System!");
                System.out.println("Select an option:");
                System.out.println("1. Teams");
                System.out.println("2. Matches");
                System.out.println("3. Players");
                System.out.println("4. Managers");
                System.out.println("5. Referees");
                System.out.println("6. Stadiums");
                System.out.println("7. Stats");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        handleTeams();
                        break;
                    case 2:
                        handleMatches();
                        break;
                    case 3:
                        handlePlayers();
                        break;
                    case 4:
                        handleManagers();
                        break;
                    case 5:
                        handleReferees();
                        break;
                    case 6:
                        handleStadiums();
                        break;
                    case 7:
                        handleStats();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }

    public static void handleTeams() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Team Management Menu:");
            System.out.println("1. Enter Team Information");
            System.out.println("2. Display Team Players");
            System.out.println("3. Update Team");
            System.out.println("4. Display Team Matches");
            System.out.println("5. Add Player to Team");
            System.out.println("6. Delete Player from Team");
            System.out.println("7. Display Total Teams");
            System.out.println("8. Calculate Number of Teams");
            System.out.println("9. Delete Team");
            System.out.println("10. Display Team Info");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterTeamInfo();
                    break;
                case 2:
                    displayTeamPlayers();
                    break;
                case 3:
                    updateTeam();
                    break;
                case 4:
                    displayTeamMatches();
                    break;
                case 5:
                    addPlayerToTeam();
                    break;
                case 6:
                    deletePlayerFromTeam();
                    break;
                case 7:
                    displayTotalTeams();
                    break;
                case 8:
                    int numOfTeams = calcNumOfTeams();
                    System.out.println("Number of Teams: " + numOfTeams);
                    break;
                case 9:
                    deleteTeam();
                    break;
                case 10:
                    String teamInfo =displayTeamInfo();
                    System.out.println(teamInfo);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    public static void handleMatches() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Match Management Menu:");
            System.out.println("1. Enter Match Information");
            System.out.println("2. Display Match Information");
            System.out.println("3. Update Match");
            System.out.println("4. Delete Match");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterMatchInfo();
                    break;
                case 2:
                    String matchInfo = displayMatchInfo();
                    System.out.println(matchInfo);
                    break;
                case 3:
                    updateMatch();
                    break;
                case 4:
                    deleteMatch();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }



    public static void handlePlayers() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Player Management Menu:");
            System.out.println("1. Enter Player Information");
            System.out.println("2. Search Player");
            System.out.println("3. Update Player");
            System.out.println("4. Delete Player");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterPlayerInfo();
                    break;
                case 2:
                    searchPlayer();
                    break;
                case 3:
                    updatePlayer();
                    break;
                case 4:
                    deletePlayer();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    public static void handleManagers() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Manager Management Menu:");
            System.out.println("1. Enter Manager Information");
            System.out.println("2. Update Manager Information");
            System.out.println("3. Delete Manager");
            System.out.println("4. Display Manager Information");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterManagerInfo();
                    break;
                case 2:
                    updateManagerInfo();
                    break;
                case 3:
                    deleteManager();
                    break;
                case 4:
                   System.out.println( displayManagerInfo() );
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void handleReferees() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Referee Management Menu:");
            System.out.println("1. Enter Referee Information");
            System.out.println("2. Update Referee Information");
            System.out.println("3. Delete Referee");
            System.out.println("4. Display Total Referees");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterRefereeInfo();
                    break;
                case 2:
                    updateRefereeInfo();
                    break;
                case 3:
                    deleteReferee();
                    break;
                case 4:
                    displayTotalReferees();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static void handleStadiums() throws DuplicateException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Stadium Management Menu:");
            System.out.println("1. Enter Stadium Information");
            System.out.println("2. Display Stadium Information");
            System.out.println("3. Display Upcoming Matches at Stadium");
            System.out.println("4. Delete Stadium");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    enterStadiumInfo();
                    break;
                case 2:
                    System.out.println(displayStadiumInfo());
                    break;
                case 3:
                    System.out.println(displayUpcomingMatches());
                    break;
                case 4:
                    deleteStadium();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    public static void handleStats() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Stats Menu:");
            System.out.println("1. Display Players with most goals ");
            System.out.println("2. Display Players with most Assists");
            System.out.println("3. Display Goalkeepers with most Clean Sheets  ");
            System.out.println("4. Display Teams by Average Age");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Returning to Main Menu...");
                    return;
                case 1:
                    displayTopScorePlayers();
                    break;
                case 2:
                    displayTopAssistPlayers();
                    break;
                case 3:
                    displayTopCleanSheetsPlayers();
                    break;
                case 4:
                    displayTeamsByAverageAge();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}


