package edu.ainshams.egyptianleaguesystem.ui;

import edu.ainshams.egyptianleaguesystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamController {

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    @FXML
    private Button backBtn;
    @FXML
    private TextField teamName;
    @FXML
    private TextField teamId;
    @FXML
    private AnchorPane root;
    @FXML
    private GridPane infoGrid;
    @FXML
    private ToggleGroup choice;
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label currentInfoLabel;
    @FXML
    private Label currentInfo;
    @FXML
    private Label newInfoLabel;
    @FXML
    private TextField newInfoField;
    @FXML
    private VBox editBox;
    @FXML
    private HBox playerChoiceBox;
    @FXML
    private ToggleGroup playerChoice;
    private Team currentTeam;

    public void defaultButton(MouseEvent event){
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: transparent;");
    }

    public void blueBack(){
        backBtn.setStyle("-fx-background-color: #2377b8;");
    }

    public void switchTeamMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("teamsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene startMenu = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(startMenu);
        stage.show();
    }

    public void createTeam(){
        try {
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Team created successfully!");
            boolean duplicateName = false;
            boolean duplicateId = false;
            String name = teamName.getText();
            int id = Integer.parseInt(teamId.getText());
            for (Team team : Logic.getTeams()) {
                if (team.getName().equalsIgnoreCase(name)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There is already a team with this name!");
                    alert.show();
                    duplicateName = true;
                }
                if (team.getTeamId() == id) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("There is already a team with this id!");
                    alert.show();
                    duplicateId = true;
                }
            }
            if (!duplicateName && !duplicateId) {
                Team team = new Team(name, id);
                Logic.addTeam(team);
                success.show();
            }
            System.out.println("Available teams:");
            Logic.displayTotalTeams();
        }catch (NumberFormatException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter numbers only in id field");
            alert.show();
        }
    }


    public void teamInfo(Team team){
        Label teamName = new Label(team.getName());
        Label teamId = new Label(Integer.toString(team.getTeamId()));
        Label teamManager;
        if (team.getManager() != null) {
            teamManager = new Label(team.getManager().getName());
        }
        else {
            teamManager = new Label("N/A");
        }
        Label teamCaptain;
        if (team.getCaptain() != null){
            teamCaptain = new Label(team.getCaptain().getName());
        }
        else {
            teamCaptain = new Label("N/A");
        }
        Label matchesPlayed = new Label(Integer.toString(team.getMatchesPlayed()));
        Label teamWins = new Label(Integer.toString(team.getWins()));
        Label teamDraws = new Label(Integer.toString(team.getDraws()));
        Label teamLosses = new Label(Integer.toString(team.getLosses()));
        Label teamGoalsFor = new Label(Integer.toString(team.getGoalsFor()));
        Label teamGoalsAgainst = new Label(Integer.toString(team.getGoalsAgainst()));
        Label teamGoalDifference = new Label(Integer.toString(team.getGoalDifference()));
        Label teamTotalScore = new Label(Integer.toString(team.getTotalScore()));
        VBox infoList = new VBox(teamName, teamId, teamManager, teamCaptain, matchesPlayed, teamWins, teamDraws, teamLosses, teamGoalsFor, teamGoalsAgainst, teamGoalDifference, teamTotalScore);
        infoGrid.add(infoList, 1, 0);
        infoGrid.setVisible(true);
    }
    public void teamMatches(Team team){
        FlowPane matchesPane = new FlowPane();
        for (Match match : team.getMatches()){
            Label label = new Label(match.matchHeader());
            label.setFont(new Font(20));
            label.setTextFill(Color.WHITE);
            matchesPane.getChildren().add(label);
        }
        root.getChildren().add(matchesPane);
    }
    public void teamPlayers(Team team){
        FlowPane playersPane = new FlowPane();
        playersPane.setOrientation(Orientation.VERTICAL);
        for (Player player : team.getPlayers()){
            Label label = new Label(player.getNumber()+"  "+player.getName());
            playersPane.getChildren().add(label);
        }
    }


    private String getSelectedButtonText() {
        Toggle selectedToggle = choice.getSelectedToggle();

        if (selectedToggle != null) {
            return ((ToggleButton) selectedToggle).getText();
        } else {
            return "No toggle selected";
        }
    }
    public void showNodes(Team currentTeam){
        String editing = getSelectedButtonText();
        if (currentTeam != null){
            playerChoiceBox.setVisible(false);
            if (editing.equalsIgnoreCase("name")){
                currentInfoLabel.setText("Current team name:");
                currentInfo.setText(currentTeam.getName());
                newInfoLabel.setText("New team name:");
            }
            else if (editing.equalsIgnoreCase("id")) {
                currentInfoLabel.setText("Current team ID:");
                currentInfo.setText(Integer.toString(currentTeam.getTeamId()));
                newInfoLabel.setText("New team ID:");
            }
            else if (editing.equalsIgnoreCase("players")){
                currentInfoLabel.setText("Add or Remove player from team");
                currentInfo.setText("");
                newInfoField.setText("Enter Player ID:");
                playerChoiceBox.setVisible(true);
            }
            else if (editing.equalsIgnoreCase("captain")){
                currentInfoLabel.setText("Current team captain:");
                if (currentTeam.getCaptain() != null) {
                    currentInfo.setText(currentTeam.getCaptain().getName());
                }
                else {
                    currentInfo.setText("N/A");
                }
                newInfoLabel.setText("New team captain:");
            }
            else {
                currentInfoLabel.setText("Current team manager:");
                if (currentTeam.getManager() != null) {
                    currentInfo.setText(currentTeam.getManager().getName());
                }
                else {
                    currentInfo.setText("N/A");
                }
                newInfoLabel.setText("New team manager:");
            }
            editBox.setVisible(true);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error occurred!");
            alert.show();
        }
    }
    public void initialize(Team team) {
        teamNameLabel.setText(team.getName());
        choice.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showNodes(team);
                currentTeam = team;
            }
        });
    }
    public void editTeamInfo(){
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Team updated successfully!");
        Team team = currentTeam;
        String editing = getSelectedButtonText();
        if (currentTeam != null && editing != null){
            try {
                if (editing.equalsIgnoreCase("name")) {
                    boolean isNameTaken = false;
                    String teamName = newInfoField.getText();
                    for (Team element : Logic.getTeams()) {
                        if (element.getName().equalsIgnoreCase(teamName)) {
                            Alert nameTaken = new Alert(Alert.AlertType.WARNING, "This name is already taken");
                            nameTaken.show();
                            isNameTaken = true;
                        }
                    }
                    if (!isNameTaken) {
                        team.setName(teamName);
                        success.show();
                        teamNameLabel.setText(team.getName());
                    }
                } else if (editing.equalsIgnoreCase("id")) {
                    boolean isIdTaken = false;
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Team element : Logic.getTeams()) {
                        if (element.getTeamId() == id) {
                            Alert idTaken = new Alert(Alert.AlertType.WARNING, "This id is already taken");
                            idTaken.show();
                            isIdTaken = true;
                        }
                    }
                    if (!isIdTaken) {
                        team.setTeamId(id);
                        success.show();
                    }
                } else if (editing.equalsIgnoreCase("players")) {
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Player player : Logic.getPlayers()) {
                        if (player.getPlayerId() == id) {
                            Toggle selectedToggle = playerChoice.getSelectedToggle();
                            String operation = ((ToggleButton) selectedToggle).getText();
                            switch (operation) {
                                case "Add": {
                                    if (!team.getPlayers().contains(player)) {
                                        team.addPlayer(player);
                                        success.show();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.WARNING, "This player is already a part of this team!");
                                        alert.show();
                                    }
                                }
                                case "Remove": {
                                    if (team.getPlayers().contains(player)) {
                                        team.removePlayer(player);
                                        success.show();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.WARNING, "The player entered is not a part of this team!");
                                        alert.show();
                                    }
                                }
                            }
                        }
                    }
                } else if (editing.equalsIgnoreCase("captain")) {
                    boolean captainFound = false;
                    boolean playerFound = false;
                    int id = Integer.parseInt(newInfoField.getText());
                    for (Player player : team.getPlayers()) {
                        if (player.getPlayerId() == id && team.getCaptain().getPlayerId() != id) {
                            team.setCaptain(player);
                            success.show();
                            captainFound = true;
                            break;
                        }
                    }
                    if (!captainFound) {
                        if (team.getCaptain().getPlayerId() == id) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Captain entered is already the captain of this team");
                            alert.show();
                        }
                        for (Player player : Logic.getPlayers()) {
                            if (player.getPlayerId() == id) {
                                playerFound = true;
                                break;
                            }
                        }
                        if (!playerFound) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Player not found!");
                            alert.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Captain entered is not a current player in this team");
                            alert.show();
                        }
                    }
                } else if (editing.equalsIgnoreCase("manager")) {
                    int id = Integer.parseInt(newInfoField.getText());
                    if (team.getManager().getManagerId() == id) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "This is already the team manager");
                        alert.show();
                    } else {
                        boolean managerFound = false;
                        for (Manager manager : Logic.getManagers()) {
                            if (manager.getManagerId() == id) {
                                managerFound = true;
                                if (manager.getTeam() != null) {
                                    manager.getTeam().setManager(null);
                                }
                                team.setManager(manager);
                                manager.setTeam(team);
                                success.show();
                                break;
                            }
                        }
                        if (!managerFound) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Manager not found!");
                            alert.show();
                        }
                    }
                }
                newInfoField.setText("");
                choice.selectToggle(null);
                editBox.setVisible(false);
            }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter number only");
                alert.show();
            }
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR, "An error occurred please try again");
            error.show();
        }
    }

}
